package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.Quote;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dal.dao.CustomerDao;
import be.ipl.pae.dal.dao.DevelopmentTypeDao;
import be.ipl.pae.dal.dao.PhotoDao;
import be.ipl.pae.dal.dao.QuoteDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.time.LocalDate;
import java.util.List;

class QuoteUccImpl implements QuoteUcc {

  @Injected
  private QuoteDao quoteDao;

  @Injected
  private CustomerDao customerDao;

  @Injected
  private PhotoDao photoDao;

  @Injected
  private DevelopmentTypeDao developmentTypeDao;

  @Injected
  private DalServiceTransaction dalService;

  @Override
  public QuoteDto insert(QuoteDto quoteDto) throws BizException {
    try {
      dalService.startTransaction();
      if (quoteDao.checkQuoteIdInDb(quoteDto.getIdQuote())) {
        throw new BizException("Id de devis déjà utilisé");
      }
      quoteDto = quoteDao.insertQuote(quoteDto);
      for (DevelopmentTypeDto developmentType : quoteDto.getDevelopmentTypes()) {
        quoteDao.linkToType(quoteDto.getIdQuote(), developmentType.getIdType());
      }
      for (PhotoDto photoDto : quoteDto.getListPhotoBefore()) {
        photoDao.insert(photoDto);
      }
      return quoteDto;
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public QuoteDto getQuote(String idQuote) throws BizException {
    try {
      dalService.startTransaction();
      return getQuoteBis(idQuote);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
  }

  private QuoteDto getQuoteBis(String idQuote) throws BizException, DalException {
    QuoteDto quoteDto;
    quoteDto = quoteDao.getQuote(idQuote);
    if (quoteDto == null) {
      throw new BizException("Devis non existant");
    }

    quoteDto.setCustomer(customerDao.getCustomer(quoteDto.getIdCustomer()));
    quoteDto.setListPhotoBefore(photoDao.getPhotos(quoteDto.getIdQuote(), true));
    quoteDto.setListPhotoAfter(photoDao.getPhotos(quoteDto.getIdQuote(), false));

    quoteDto.setDevelopmentType(developmentTypeDao.getDevelopmentTypeList(quoteDto.getIdQuote()));

    return quoteDto;
  }

  @Override
  public void setStartDateQuoteInDb(QuoteDto quote) throws BizException {
    try {
      dalService.startTransaction();

      if (quote.getState() == null) {
        LocalDate startDate = quote.getStartDate();
        quote = quoteDao.getQuote(quote.getIdQuote());
        quote.setStartDate(startDate);
      }
      if (quote.getState().getId() >= 3) {
        throw new BizException("Impossible de modifier la date");
      }

      quoteDao.setStartDate(quote);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
  }

  public List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto) {
    return getQuotesFiltered(quotesFilterDto, -1);
  }

  @Override
  public List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto, int idCustomer) {
    List<QuoteDto> quoteDtoList;
    try {
      dalService.startTransaction();
      quoteDtoList = quoteDao.getQuotesFiltered(quotesFilterDto, idCustomer);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
    return quoteDtoList;
  }

  @Override
  public QuoteDto useStateManager(QuoteDto quote) throws BizException {

    if (!((Quote) quote).checkState(getStateQuote(quote))) {
      throw new BizException("L'état du devis a déjà été modifié");
    }

    QuoteDto quoteToReturn = null;

    switch (quote.getState()) {
      case QUOTE_ENTERED:
        setStartDateQuoteInDb(quote);
        quoteToReturn = setState(quote.getIdQuote(), QuoteState.PLACED_ORDERED);
        break;
      case PLACED_ORDERED:
        // if != null it means that the user want to change the date
        if (quote.getStartDate() != null) {
          setStartDateQuoteInDb(quote);
          quoteToReturn = getQuote(quote.getIdQuote());
        } else {
          quoteToReturn = setState(quote.getIdQuote(), QuoteState.CONFIRMED_DATE);
        }
        break;
      case CONFIRMED_DATE:
        int workDuartion = getWorkDuration(quote.getIdQuote());
        if (workDuartion > 15) {
          quoteToReturn = setState(quote.getIdQuote(), QuoteState.PARTIAL_INVOICE);
        } else {
          quoteToReturn = setState(quote.getIdQuote(), QuoteState.TOTAL_INVOICE);
        }
        break;
      case PARTIAL_INVOICE:
        quoteToReturn = setState(quote.getIdQuote(), QuoteState.TOTAL_INVOICE);
        break;
      case TOTAL_INVOICE:
        quoteToReturn = setState(quote.getIdQuote(), QuoteState.VISIBLE);
        break;
      case VISIBLE:
        break;
      case CANCELLED:
        quoteToReturn = setState(quote.getIdQuote(), QuoteState.CANCELLED);
        break;
      default:
        break;
    }
    return quoteToReturn;
  }

  private QuoteState getStateQuote(QuoteDto quote) throws BizException {
    QuoteState quoteState;
    try {
      dalService.startTransaction();
      if (quote.getIdQuote() == null) {
        throw new BizException("Aucune id pour le devis n'a été envoyé");
      }
      quoteState = quoteDao.getStateQuote(quote.getIdQuote());
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
    return quoteState;
  }

  private int getWorkDuration(String idQuote) {
    int workDuration;
    try {
      dalService.startTransaction();
      workDuration = quoteDao.getWorkDuration(idQuote);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
    return workDuration;
  }

  /**
   * Set the state of the in the db.
   *
   * @param idQuote id of the quote
   * @param state state of the quote
   * @return a new QuoteDto object
   * @throws BizException if idQuote or date isn't send by the user
   */
  private QuoteDto setState(String idQuote, QuoteState state) throws BizException {
    QuoteDto quoteDto;
    try {
      dalService.startTransaction();
      quoteDao.setStateQuote(state, idQuote);
      quoteDto = getQuoteBis(idQuote);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
    return quoteDto;
  }

  @Override
  public void setFavoritePhoto(String quoteId, int photoId) throws BizException {
    try {
      dalService.startTransaction();
      if (quoteDao.getQuote(quoteId) == null) {
        throw new BizException("Devis non existant");
      }
      PhotoDto photoDto = photoDao.getPhotoById(photoId);
      if (photoDto == null) {
        throw new BizException("Photo inexistante");
      }
      if (!photoDto.getIdQuote().equals(quoteId)) {
        throw new BizException("Photo non liée au devis");
      }

      quoteDao.setFavoritePhoto(quoteId, photoId);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
  }
}
