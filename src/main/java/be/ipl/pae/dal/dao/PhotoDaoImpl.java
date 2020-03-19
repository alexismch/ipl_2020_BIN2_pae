package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;

import java.sql.PreparedStatement;

public class PhotoDaoImpl implements PhotoDao {

  @Injected
  DalService dalService;
  @Injected
  DtoFactory photoDtoFactory;
  
  public PhotoDto getPhotoPerDevelopmentType() {
    //autre uce case, pour plus tard
    PhotoDto photoDto= null;
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM  mystherbe.users WHERE  ");
    
    return null;
    
  }
  
}
