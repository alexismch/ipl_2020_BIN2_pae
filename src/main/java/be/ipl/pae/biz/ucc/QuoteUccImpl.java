package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dal.dao.CustomerDao;
import be.ipl.pae.dal.dao.DevelopmentTypeDao;
import be.ipl.pae.dal.dao.PhotoDao;
import be.ipl.pae.dal.dao.QuoteDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class QuoteUccImpl implements QuoteUcc {

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
      try {
        dalService.startTransaction();
        if (quoteDao.checkQuoteIdInDb(quoteDto.getIdQuote())) {
          throw new BizException("Id de devis déjà utilisé!");
        }
        quoteDto = quoteDao.insertQuote(quoteDto);
        for (DevelopmentTypeDto developmentType : quoteDto.getDevelopmentTypes()) {
          quoteDao.linkToType(quoteDto.getIdQuote(), developmentType.getIdType());
        }
        for (PhotoDto photoDto : quoteDto.getListPhotoBefore()) {
          photoDao.insert(photoDto);
        }
        return quoteDto;
      } catch (FatalException ex) {
        dalService.rollbackTransaction();
        throw new BizException(ex);
      } finally {
        dalService.commitTransaction();
      }
    } catch (FatalException ex) {
      throw new BizException(ex);
    }

  }

  @Override
  public List<QuoteDto> getQuotes() throws BizException {
    try {
      try {
        dalService.startTransaction();
        return quoteDao.getAllQuote();
      } catch (FatalException ex) {
        dalService.rollbackTransaction();
        throw new BizException(ex);
      } finally {
        dalService.commitTransaction();
      }
    } catch (FatalException ex) {
      throw new BizException(ex);
    }
  }

  @Override
  public QuoteDto getQuote(String idQuote) throws FatalException, BizException {

    try {
      dalService.startTransaction();
      return getQuoteBis(idQuote);
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
    } finally {
      dalService.commitTransaction();
    }
    return null;
  }

  private QuoteDto getQuoteBis(String idQuote) throws FatalException, BizException {
    QuoteDto quoteDto;
    quoteDto = quoteDao.getQuote(idQuote);
    if (quoteDto.getIdQuote() == null) {
      throw new BizException("Devis non existant!");
    }

    quoteDto.setCustomer(customerDao.getCustomer(quoteDto.getIdCustomer()));
    quoteDto.setListPhotoBefore(photoDao.getPhotos(quoteDto.getIdQuote(), true));
    quoteDto.setListPhotoAfter(photoDao.getPhotos(quoteDto.getIdQuote(), false));

    quoteDto.setDevelopmentType(developmentTypeDao.getDevelopmentTypeList(quoteDto.getIdQuote()));

    return quoteDto;
  }

  @Override
  public List<QuoteDto> getCustomerQuotes(int customerId) throws BizException {
    try {
      try {
        dalService.startTransaction();
        return quoteDao.getCustomerQuotes(customerId);
      } catch (FatalException ex) {
        dalService.rollbackTransaction();
        throw new BizException(ex);
      } finally {
        dalService.commitTransaction();
      }
    } catch (FatalException ex) {
      throw new BizException(ex);
    }
  }


  @Override
  public void setStartDateQuoteInDb(QuoteDto quote) throws FatalException, BizException {
    try {
      dalService.startTransaction();
      quoteDao.setStartDate(quote);
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex.getMessage());
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto) throws FatalException {
    try {
      dalService.startTransaction();
      return quoteDao.getQuotesFiltered(quotesFilterDto);
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex.getMessage());
    } finally {
      dalService.commitTransaction();
    }
  }


  @Override
  public QuoteDto makeQuoteVisible(String idQuote) throws BizException, FatalException {
    try {
      dalService.startTransaction();
      // changeQuoteState(idQuote, QuoteState.VISIBLE);

    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex.getMessage());
    } finally {
      dalService.commitTransaction();
    }

    return null; // TODO
  }

  @Override
  public QuoteDto useStateManager(QuoteDto quote) throws BizException, FatalException {
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
          quoteToReturn = setState(quote.getIdQuote(), QuoteState.PLACED_ORDERED);
        } else {
          quoteToReturn = setState(quote.getIdQuote(), QuoteState.CONFIRMED_DATE);
        }
        break;
      case CONFIRMED_DATE:
        quoteToReturn = setState(quote.getIdQuote(), QuoteState.TOTAL_INVOICE);
        break;
      case PARTIAL_INVOICE:
        // TODO
        break;
      case TOTAL_INVOICE:

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

  @Override
  public QuoteDto setState(String idQuote, QuoteState state) throws BizException, FatalException {
    try {
      dalService.startTransaction();
      quoteDao.setStateQuote(state, idQuote);
      return getQuoteBis(idQuote);
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex.getMessage());
    } finally {
      dalService.commitTransaction();
    }
  }

  /*
   * public void changeQuoteState(String idQuote, QuoteState newQuoteState) throws BizException,
   * FatalException {
   * 
   * QuoteState quoteState = quoteDao.getQuoteState(idQuote);
   * 
   * if (quoteState == null) { throw new BizException("Il n'y a aucun devis avec l'id " + idQuote);
   * }
   * 
   * if (quoteState == newQuoteState) { return; }
   * 
   * switch (newQuoteState) {
   * 
   * case QUOTE_ENTERED: break; case PLACED_ORDERED: break; case CONFIRMED_DATE: break; case
   * PARTIAL_INVOICE: break; case TOTAL_INVOICE: if (CONFIRMED_DATE.equals(quoteState) ||
   * PARTIAL_INVOICE.equals(quoteState)) { quoteDao.changeQuoteState(idQuote, newQuoteState);
   * return; } break; case VISIBLE: if (TOTAL_INVOICE.equals(quoteState)) {
   * quoteDao.changeQuoteState(idQuote, newQuoteState); return; } break; case CANCELLED: if
   * (QUOTE_ENTERED.equals(quoteState) || PLACED_ORDERED.equals(quoteState) ||
   * CONFIRMED_DATE.equals(quoteState)) { quoteDao.changeQuoteState(idQuote, newQuoteState); return;
   * } break; }
   * 
   * }
   */
}
