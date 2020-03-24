package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
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
  public QuoteDto getQuote(String idQuote) throws FatalException {
    QuoteDto quoteDto;
    try {
      dalService.startTransaction();
      quoteDto = quoteDao.getQuote(idQuote);
      quoteDto.setCustomer(customerDao.getCustomer(quoteDto.getIdCustomer()));
      quoteDto.setListPhotoBefore(photoDao.getPhotos(quoteDto.getIdQuote(), true));
      quoteDto.setListPhotoAfter(photoDao.getPhotos(quoteDto.getIdQuote(), false));
      for (PhotoDto photo : quoteDto.getListPhotoBefore()) {
        DevelopmentTypeDto developmentTypeDto =
            developmentTypeDao.getDevelopmentType(photo.getIdType());
        quoteDto.addDevelopmentTypesSet(developmentTypeDto);
      }

      for (PhotoDto photo : quoteDto.getListPhotoAfter()) {
        DevelopmentTypeDto developmentTypeDto =
            developmentTypeDao.getDevelopmentType(photo.getIdType());
        quoteDto.addDevelopmentTypesSet(developmentTypeDto);
      }
      return quoteDto;
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
    } finally {
      dalService.commitTransaction();
    }
    return null;
  }
}
