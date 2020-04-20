package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;

import java.util.ArrayList;
import java.util.List;

public class MockDevelopmentTypeDao implements DevelopmentTypeDao {

  @Injected
  private DtoFactory dtoFactory;

  @Override
  public List<DevelopmentTypeDto> getdevelopmentTypes() {
    List<DevelopmentTypeDto> list = new ArrayList<>();

    DevelopmentTypeDto developmentType = dtoFactory.getDevelopmentType();
    developmentType.setIdType(1);
    developmentType.setTitle("Aménagement de jardin de ville");
    list.add(developmentType);

    developmentType = dtoFactory.getDevelopmentType();
    developmentType.setIdType(2);
    developmentType.setTitle("Aménagement de jardin");
    list.add(developmentType);

    return list;
  }

  @Override
  public DevelopmentTypeDto getDevelopmentType(int typeId) {
    if (typeId == 1) {
      DevelopmentTypeDto developmentType = dtoFactory.getDevelopmentType();
      developmentType.setIdType(typeId);
      developmentType.setTitle("Aménagement de jardin de ville");
    } else if (typeId == 2) {
      DevelopmentTypeDto developmentType = dtoFactory.getDevelopmentType();
      developmentType.setIdType(typeId);
      developmentType.setTitle("Aménagement de jardin");
    }
    return null;
  }

  @Override
  public DevelopmentTypeDto insert(DevelopmentTypeDto developmentType) {
    developmentType.setIdType(3);
    return developmentType;
  }

  @Override
  public List<DevelopmentTypeDto> getDevelopmentTypeList(String quoteId) {
    DevelopmentTypeDto developmentTypeDto = dtoFactory.getDevelopmentType();
    developmentTypeDto.setIdType(1);
    List<DevelopmentTypeDto> listToReturn = new ArrayList<>();
    listToReturn.add(developmentTypeDto);
    return listToReturn;
  }

  @Override
  public boolean exists(String title) {
    return "Aménagement de jardin de ville".equals(title)
        || "Aménagement de jardin".equals(title);
  }
}
