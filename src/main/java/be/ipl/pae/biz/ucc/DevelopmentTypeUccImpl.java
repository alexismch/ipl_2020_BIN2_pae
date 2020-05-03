package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.dal.dao.DevelopmentTypeDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class DevelopmentTypeUccImpl implements DevelopmentTypeUcc {

  @Injected
  private DevelopmentTypeDao developmentTypeDao;

  @Injected
  private DalServiceTransaction dalService;

  @Override
  public List<DevelopmentTypeDto> getDevelopmentTypes() {

    List<DevelopmentTypeDto> listToReturn = null;
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
  public List<DevelopmentTypeDto> getDevelopmentTypes(String quoteId) {

    List<DevelopmentTypeDto> listToReturn = null;
    try {
      dalService.startTransaction();
      listToReturn = developmentTypeDao.getDevelopmentTypeList(quoteId);
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
    DevelopmentTypeDto developmentType = null;

    try {
      dalService.startTransaction();
      developmentType = developmentTypeDao.getDevelopmentType(typeId);
      if (developmentType == null) {
        throw new BizException("Type d'améngament inexistant.");
      }
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }

    return developmentType;
  }

  @Override
  public DevelopmentTypeDto insert(DevelopmentTypeDto developmentType) throws BizException {
    DevelopmentTypeDto type = null;
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
