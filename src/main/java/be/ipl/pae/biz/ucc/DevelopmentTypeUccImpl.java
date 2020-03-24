package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.dal.dao.DevelopmentTypeDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class DevelopmentTypeUccImpl implements DevelopmentTypeUcc {

  @Injected
  private DevelopmentTypeDao developmentTypeDao;

  @Injected
  private DalServiceTransaction dalService;

  public List<DevelopmentTypeDto> getDevelopmentTypes() throws BizException {
    try {
      List<DevelopmentTypeDto> listToReturn = null;
      try {
        dalService.startTransaction();
        listToReturn = developmentTypeDao.getAllDevelopmentType();
      } catch (Exception ex) {
        dalService.rollbackTransaction();
      } finally {
        dalService.commitTransaction();
      }

      return listToReturn;

    } catch (FatalException ex) {
      throw new BizException(ex);
    }
  }

  @Override
  public DevelopmentTypeDto getDevelopmentType(int typeId) throws BizException, FatalException {
    try {
      dalService.startTransaction();
      try {
        DevelopmentTypeDto developmentType = developmentTypeDao.getDevelopmentType(typeId);
        if (developmentType == null) {
          throw new BizException("Type d'améngament inexistant.");
        }
        return developmentType;
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
