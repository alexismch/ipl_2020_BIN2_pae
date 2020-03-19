package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.dal.dao.DevelopmentTypeDao;
import be.ipl.pae.dependencies.Injected;

import java.util.List;

public class DevelopmentTypeUccImpl implements DevelopmentTypeUcc {
  
  @Injected
  private DevelopmentTypeDao developmentTypeDao;

  public List<DevelopmentTypeDto> getDevelopmentTypes(){
    
    return developmentTypeDao.getAllDevelopmentType();
  }
}
