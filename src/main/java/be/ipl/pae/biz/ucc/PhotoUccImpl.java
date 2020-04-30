package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dal.dao.PhotoDao;
import be.ipl.pae.dal.dao.QuoteDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class PhotoUccImpl implements PhotoUcc {

  @Injected
  private DalServiceTransaction dalService;

  @Injected
  private PhotoDao photoDao;

  @Injected
  private QuoteDao quoteDao;

  @Override
  public List<PhotoVisibleDto> getVisiblePhotos() throws BizException {
    try {
      dalService.startTransaction();
      try {
        return photoDao.getVisiblePhotos();
      } catch (FatalException ex) {
        throw new BizException(ex);
      }
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
    } finally {
      dalService.commitTransaction();
    }
    return null;
  }

  @Override
  public List<PhotoVisibleDto> getVisiblePhotos(int typeId) throws BizException {
    try {
      dalService.startTransaction();
      try {
        return photoDao.getVisiblePhotos(typeId);
      } catch (FatalException ex) {
        throw new BizException(ex);
      }
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
    } finally {
      dalService.commitTransaction();
    }
    return null;
  }

  @Override
  public void insert(List<PhotoDto> photos) throws BizException {
    try {
      dalService.startTransaction();
      try {
        QuoteState quoteState = quoteDao.getQuote(photos.get(0).getIdQuote()).getState();
        if (quoteState.getId() != 6 && quoteState.getId() != 7) {
          throw new BizException("Devis non éligible.");
        }

        for (PhotoDto photo : photos) {
          photoDao.insert(photo);
        }
      } catch (FatalException ex) {
        throw new BizException(ex);
      }
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
    } finally {
      dalService.commitTransaction();
    }
  }


  @Override
  public PhotoDto getPhotoById(int id) throws BizException {
    try {
      dalService.startTransaction();
      try {
        return photoDao.getPhotoById(id);
      } catch (FatalException ex) {
        throw new BizException(ex);
      }
    } catch (FatalException ex) {
      dalService.rollbackTransaction();
    } finally {
      dalService.commitTransaction();
    }
    return null;
  }
}
