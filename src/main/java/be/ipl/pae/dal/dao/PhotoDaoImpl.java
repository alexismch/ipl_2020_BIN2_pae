package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

  @Override
  public void insert(PhotoDto photoDto) throws FatalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "INSERT INTO mystherbe.photos "
            + "(title, base64, id_quote, is_visible, id_type, before_work) "
            + "VALUES (?, ?, ?, ?, ?, ?)");

    try {
      ps.setString(1, photoDto.getTitle());
      ps.setString(2, photoDto.getBase64());
      ps.setString(3, photoDto.getIdQuote());
      ps.setBoolean(4, photoDto.isVisible());
      ps.setInt(5, photoDto.getIdType());
      ps.setBoolean(6, photoDto.isBeforeWork());
      ps.execute();
    } catch (SQLException e) {
      throw new FatalException("error with the db");
    }
  }
}
