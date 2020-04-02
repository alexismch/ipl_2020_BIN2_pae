package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.util.ArrayList;
import java.util.List;

public class MockDevelopmentTypeDao implements DevelopmentTypeDao {

  @Injected
  private DtoFactory dtoFactory;

  @Override
  public List<DevelopmentTypeDto> getdevelopmentTypes() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DevelopmentTypeDto getDevelopmentType(int typeId) throws FatalException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public DevelopmentTypeDto insert(DevelopmentTypeDto developmentType) throws FatalException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<DevelopmentTypeDto> getDevelopmentTypeList(String quoteId) throws FatalException {
    DevelopmentTypeDto developmentTypeDto = dtoFactory.getDevelopmentType();
    developmentTypeDto.setIdType(1);
    List<DevelopmentTypeDto> listToReturn = new ArrayList<DevelopmentTypeDto>();
    listToReturn.add(developmentTypeDto);
    return listToReturn;
  }

}
