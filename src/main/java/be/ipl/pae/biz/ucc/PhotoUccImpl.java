package be.ipl.pae.biz.ucc;

import static be.ipl.pae.ihm.servlets.utils.Util.isInside;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dal.dao.DevelopmentTypeDao;
import be.ipl.pae.dal.dao.PhotoDao;
import be.ipl.pae.dal.dao.QuoteDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

class PhotoUccImpl implements PhotoUcc {

  @Injected
  private DalServiceTransaction dalService;

  @Injected
  private PhotoDao photoDao;

  @Injected
  private QuoteDao quoteDao;

  @Injected
  private DevelopmentTypeDao developmentTypeDao;

  @Override
  public List<PhotoVisibleDto> getVisiblePhotos() {

    List<PhotoVisibleDto> list;
    try {
      dalService.startTransaction();
      list = photoDao.getVisiblePhotos();
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }

    return list;
  }

  @Override
  public List<PhotoVisibleDto> getVisiblePhotos(int typeId) {

    List<PhotoVisibleDto> list;
    try {
      dalService.startTransaction();
      list = photoDao.getVisiblePhotos(typeId);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }

    return list;
  }

  @Override
  public void insert(List<PhotoDto> photos) throws BizException {
    try {
      dalService.startTransaction();
      QuoteState quoteState = quoteDao.getQuote(photos.get(0).getIdQuote()).getState();

      Object[] types = developmentTypeDao.getDevelopmentTypeList(photos.get(0).getIdQuote())
          .stream()
          .map(DevelopmentTypeDto::getIdType).toArray();

      for (PhotoDto photo : photos) {
        if (!isInside(types, photo.getIdType())) {
          throw new IllegalArgumentException();
        }
      }

      if (quoteState.getId() != 6 && quoteState.getId() != 7) {
        throw new BizException("Devis non éligible.");
      }

      for (PhotoDto photo : photos) {
        photoDao.insert(photo);
      }
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }

  }


  @Override
  public PhotoDto getPhotoById(int id) {
    PhotoDto photo;
    try {
      dalService.startTransaction();
      photo = photoDao.getPhotoById(id);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }


    return photo;
  }
}
