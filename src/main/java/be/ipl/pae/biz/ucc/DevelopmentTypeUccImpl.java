package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.dal.dao.DevelopmentTypeDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

class DevelopmentTypeUccImpl implements DevelopmentTypeUcc {

  @Injected
  private DevelopmentTypeDao developmentTypeDao;

  @Injected
  private DalServiceTransaction dalService;

  @Override
  public List<DevelopmentTypeDto> getDevelopmentTypes() {

    List<DevelopmentTypeDto> listToReturn;
    try {
      dalService.startTransaction();
      listToReturn = developmentTypeDao.getdevelopmentTypes();
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
    return listToReturn;


  }

  @Override
  public DevelopmentTypeDto getDevelopmentType(int typeId) throws BizException {

    try {
      dalService.startTransaction();
      DevelopmentTypeDto developmentType = developmentTypeDao.getDevelopmentType(typeId);
      if (developmentType == null) {
        throw new BizException("Type d'améngament inexistant.");
      }
      return developmentType;
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public DevelopmentTypeDto insert(DevelopmentTypeDto developmentType) throws BizException {
    DevelopmentTypeDto type;
    try {
      dalService.startTransaction();

      if (developmentTypeDao.exists(developmentType.getTitle())) {
        throw new BizException("Type d'améngament déjà existant.");
      }

      type = developmentTypeDao.insert(developmentType);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }

    return type;
  }
}
