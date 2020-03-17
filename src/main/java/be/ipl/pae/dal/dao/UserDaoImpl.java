package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.dal.DalUtils;
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

    UserDto userDto = null;
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.users WHERE pseudo =?");

    try {

      ps.setString(1, pseudo);

      List<UserDto> users = getUsersViaPs(ps);
      if (users.isEmpty()) {
        return null;
      }

      userDto = users.get(0);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return userDto;
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

    UserDto userDto = null;
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.users WHERE id_user =?");

    try {
      ps.setInt(1, idUtilisateur);

      List<UserDto> users = getUsersViaPs(ps);
      if (users.isEmpty()) {
        return null;
      }

      userDto = users.get(0);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return userDto;
  }

  @Override
  public List<UserDto> getUsers(UsersFilterDto usersFilterDto) {

    String query;

    if (usersFilterDto != null) {
      query = "SELECT * FROM mystherbe.users WHERE (? IS NULL OR lastname LIKE ?)"
          + " AND (? IS NULL OR city LIKE ?)";
    } else {
      query = "SELECT * FROM mystherbe.users";
    }

    PreparedStatement ps;
    ps = dalService.getPreparedStatement(query);

    try {
      if (usersFilterDto != null) {
        String name = DalUtils.changeSpecialLikeChar(usersFilterDto.getName());
        ps.setString(1, name);
        ps.setString(2, name + "%");
        String city = DalUtils.changeSpecialLikeChar(usersFilterDto.getCity());
        ps.setString(3, city);
        ps.setString(4, city + "%");
      }

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
        UserDto userDto = userDtoFactory.getUser();
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
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.users usr WHERE usr.email =?");

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
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.users usr WHERE usr.pseudo =?");

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
    String query = "INSERT INTO mystherbe.users " + "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?) "
        + "RETURNING id_user;";
    ps = dalService.getPreparedStatement(query);

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
