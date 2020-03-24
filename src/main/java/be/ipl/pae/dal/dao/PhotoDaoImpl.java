package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;

public class PhotoDaoImpl implements PhotoDao {

  @Injected
  DalService dalService;
  @Injected
  DtoFactory photoDtoFactory;

  @Override
  public PhotoDto getPhotoPerDevelopmentType() {
    //autre uce case, pour plus tard
    //PhotoDto photoDto;
    //PreparedStatement ps;
    //ps = dalService.getPreparedStatement("Select * FROM  mystherbe.users WHERE  ");
    //TODO: méthode + javadoc
    return null;

  }

}
