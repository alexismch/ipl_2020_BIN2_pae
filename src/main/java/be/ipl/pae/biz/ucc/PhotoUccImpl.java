package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.dal.dao.PhotoDao;
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

  @Override
  public List<PhotoVisibleDto> getVisiblePhotos() throws FatalException, BizException {
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
  public List<PhotoVisibleDto> getVisiblePhotos(int typeId) throws BizException, FatalException {
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
  public void insert(List<PhotoDto> photos) throws FatalException, BizException {
    try {
      dalService.startTransaction();
      try {
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
}
