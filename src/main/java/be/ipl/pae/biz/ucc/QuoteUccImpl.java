package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
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
    QuoteDto quoteDto;
    try {
      dalService.startTransaction();
      quoteDto = quoteDao.getQuote(idQuote);
      if (quoteDto.getIdQuote() == null) {
        throw new BizException("Devis non existant!");
      }

      quoteDto.setCustomer(customerDao.getCustomer(quoteDto.getIdCustomer()));
      quoteDto.setListPhotoBefore(photoDao.getPhotos(quoteDto.getIdQuote(), true));
      quoteDto.setListPhotoAfter(photoDao.getPhotos(quoteDto.getIdQuote(), false));

      quoteDto.setDevelopmentType(developmentTypeDao.getDevelopmentTypeList(quoteDto.getIdQuote()));

      return quoteDto;
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
    } finally {
      dalService.commitTransaction();
    }
    return null;
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
  public QuoteDto confirmQuote(String quoteID) throws FatalException, BizException {
    try {
      dalService.startTransaction();
      System.out.println("confirmed date = " + QuoteState.CONFIRMED_DATE);
      quoteDao.setStateQuote(QuoteState.PLACED_ORDERED, quoteID);
      return getQuote(quoteID);
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
      System.out.println("confirmed date = " + QuoteState.CONFIRMED_DATE);
      quoteDao.setStartDate(quote);
      quoteDao.setStateQuote(QuoteState.CONFIRMED_DATE, quote.getIdQuote());
      return getQuote(quote.getIdQuote());
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex.getMessage());
    } finally {
      dalService.commitTransaction();
    }
  }

}
