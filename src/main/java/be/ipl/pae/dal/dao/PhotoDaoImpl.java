package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDaoImpl implements PhotoDao {

  @Injected
  DalService dalService;

  @Injected
  DtoFactory photoDtoFactory;

  @Override
  public PhotoDto getPhotoPerDevelopmentType() {
    // autre uce case, pour plus tard
    // PhotoDto photoDto;
    // PreparedStatement ps;
    // ps = dalService.getPreparedStatement("Select * FROM mystherbe.users WHERE ");
    // TODO: méthode + javadoc
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
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException("error with the db");
    }
  }

  @Override
  public List<PhotoDto> getPhotos(String idQuote, Boolean isBefore) throws FatalException {
    PreparedStatement ps;
    ps = dalService.getPreparedStatement(
        "Select * FROM mystherbe.photos WHERE id_quote =? AND before_work =?");

    try {
      ps.setString(1, idQuote);
      ps.setBoolean(2, isBefore);
      return getPhotosViaPs(ps);
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException("error with the db!");
    }
  }

  /**
   * Return list of photos created from the request. If only one photo is required you can call
   * {@link List#get} with the index 0.
   *
   * @param ps The request that will be executed
   * @return A list of PhotoDto created form the database
   * @throws FatalException if an SQL error occurred
   */
  private List<PhotoDto> getPhotosViaPs(PreparedStatement ps) throws FatalException {

    try {
      List<PhotoDto> photos = new ArrayList<>();
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          PhotoDto photoDto = photoDtoFactory.getPhoto();
          photoDto.setId(resultSet.getInt(1));
          photoDto.setTitle(resultSet.getString(2));
          photoDto.setBase64(resultSet.getString(3));
          photoDto.setIdQuote(resultSet.getString(4));
          photoDto.setVisible(resultSet.getBoolean(5));
          photoDto.setIdType(resultSet.getInt(6));
          photoDto.setBeforeWork(resultSet.getBoolean(7));
          photos.add(photoDto);
        }
      }
      ps.close();

      return photos;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new FatalException(ex.getMessage());
    }
  }
}
