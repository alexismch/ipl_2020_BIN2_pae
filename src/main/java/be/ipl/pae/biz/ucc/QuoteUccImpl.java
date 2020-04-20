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
  public QuoteDto confirmQuote(String quoteId) throws FatalException, BizException {
    try {
      dalService.startTransaction();
      quoteDao.setStateQuote(QuoteState.PLACED_ORDERED, quoteId);
      return getQuoteBis(quoteId);
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex.getMessage());
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public QuoteDto setStartDateQuoteInDb(QuoteDto quote) throws FatalException, BizException {
    try {
      dalService.startTransaction();
      quoteDao.setStartDate(quote);
      return getQuoteBis(quote.getIdQuote());
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex.getMessage());
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public QuoteDto confirmStartDate(String quoteId) throws FatalException, BizException {
    try {
      dalService.startTransaction();
      quoteDao.setStateQuote(QuoteState.CONFIRMED_DATE, quoteId);
      return getQuoteBis(quoteId);
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex.getMessage());
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public QuoteDto cancelQuote(String quoteId) throws BizException, FatalException {
    try {
      dalService.startTransaction();
      quoteDao.setStateQuote(QuoteState.CANCELLED, quoteId);
      return getQuoteBis(quoteId);
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

}
