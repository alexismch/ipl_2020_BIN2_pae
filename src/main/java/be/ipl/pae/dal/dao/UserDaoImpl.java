package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {

  @Injected
  DalService dalService;
  @Injected
  DtoFactory userDtoFactory;


  @Override
  public UserDto getUserByPseudo(String pseudo) {

    UserDto utilisateurDto = null;
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.utilisateurs WHERE pseudo =?");

    try {

      ps.setString(1, pseudo);

      utilisateurDto = getUsersViaPs(ps).get(0);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return utilisateurDto;
  }

  // pas sur si je dois utiliser ca pour l'instant

  /*
   * private void setValeurResutlset(ResultSet rs) { try { ResultSetMetaData rsMetaData =
   * rs.getMetaData(); List<Method> tousLesSetters = new ArrayList<Method>(); for (Method method :
   * UtilisateurDto.class.getDeclaredMethods()) { if (method.getName().startsWith("set")) {
   * tousLesSetters.add(method); } } } catch (SQLException e) { e.printStackTrace(); } }
   */

  @Override
  public UserDto getUser(int idUtilisateur) {

    UserDto utilisateurDto = null;
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.utilisateurs WHERE id_util =?");

    try {
      ps.setInt(1, idUtilisateur);
      utilisateurDto = getUsersViaPs(ps).get(0);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return utilisateurDto;
  }

  @Override
  public List<UserDto> getUsers() {

    PreparedStatement ps;
    ps = dalService.getPreparedStatement("SELECT * FROM mystherbe.utilisateurs");

    try {
      return getUsersViaPs(ps);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return new ArrayList<>();
  }

  /**
   * Return list of users created from the request. If only one user is required you can call
   * {@link List#get} with the index 0.
   *
   * @param ps The request that will be executed
   * @return A list of UserDto created form the database
   * @throws SQLException if an SQL error occurred
   */
  private List<UserDto> getUsersViaPs(PreparedStatement ps) throws SQLException {
    List<UserDto> users = new ArrayList<>();
    try (ResultSet resultSet = ps.executeQuery()) {
      while (resultSet.next()) {
        UserDto userDto = userDtoFactory.getUtilisateur();
        userDto.setId(resultSet.getInt(1));
        userDto.setPseudo(resultSet.getString(2));
        userDto.setPassword(resultSet.getString(3));
        userDto.setLastName(resultSet.getString(4));
        userDto.setFirstName(resultSet.getString(5));
        userDto.setCity(resultSet.getString(6));
        userDto.setEmail(resultSet.getString(7));
        userDto.setRegistrationDate(resultSet.getDate(8).toLocalDate());
        userDto.setStatus(UserStatus.getStatusByName(resultSet.getString(9)));
        users.add(userDto);
      }
    }
    ps.close();

    return users;
  }

  @Override
  public boolean checkEmailInDb(String email) throws FatalException {
    PreparedStatement ps;
    ps = dalService
        .getPreparedStatement("Select * FROM mystherbe.utilisateurs util WHERE util.email =?");

    try {

      ps.setString(1, email);

      try (ResultSet resultSet = ps.executeQuery()) {
        return resultSet.next();

      }
    } catch (SQLException ex) {
      throw new FatalException("error with the db");
    }
  }

  @Override
  public boolean checkPseudoInDb(String pseudo) throws FatalException {
    PreparedStatement ps;
    ps = dalService
        .getPreparedStatement("Select * FROM mystherbe.utilisateurs util WHERE util.pseudo =?");

    try {

      ps.setString(1, pseudo);

      try (ResultSet resultSet = ps.executeQuery()) {
        return resultSet.next();

      }
    } catch (SQLException ex) {
      throw new FatalException("error with the db");
    }
  }

  @Override
  public UserDto insertUser(UserDto userDto) throws FatalException {
    PreparedStatement ps;
    String INSERT_USER = "INSERT INTO mystherbe.utilisateurs "
        + "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?) " + "RETURNING id_util;";
    ps = dalService.getPreparedStatement(INSERT_USER);

    try {
      ps.setString(1, userDto.getPseudo());
      ps.setString(2, userDto.getPassword());
      ps.setString(3, userDto.getLastName());
      ps.setString(4, userDto.getFirstName());
      ps.setString(5, userDto.getCity());
      ps.setString(6, userDto.getEmail());
      ps.setDate(7, Date.valueOf(userDto.getRegistrationDate()));
      ps.setString(8, userDto.getStatus().getName());

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        userDto.setId(rs.getInt(1));
        rs.close();
        return userDto;
      } else {
        throw new Exception();
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      throw new FatalException("error with the db!");
    }
  }
}
