package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dal.util.DalUtils;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.DalException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


class UserDaoImpl implements UserDao {

  @Injected
  private DalService dalService;

  @Injected
  private DtoFactory userDtoFactory;


  @Override
  public UserDto getUserByPseudo(String pseudo) throws DalException {
    UserDto userDto;
    PreparedStatement ps;
    ps = dalService.getPreparedStatement(
        "Select * FROM mystherbe.users WHERE pseudo =?" + " ORDER BY lastname, firstname");

    try {

      ps.setString(1, pseudo);

      List<UserDto> users = getUsersViaPs(ps);
      if (users.isEmpty()) {
        return null;
      }

      userDto = users.get(0);
    } catch (SQLException ex) {
      throw new DalException("error with the db");
    }
    return userDto;
  }

  @Override
  public UserDto getUser(int idUtilisateur) throws DalException {
    UserDto userDto;
    PreparedStatement ps;
    ps = dalService.getPreparedStatement(
        "Select * FROM mystherbe.users WHERE id_user =?" + " ORDER BY lastname, firstname");

    try {
      ps.setInt(1, idUtilisateur);

      List<UserDto> users = getUsersViaPs(ps);
      if (users.isEmpty()) {
        return null;
      }

      userDto = users.get(0);
    } catch (SQLException ex) {
      throw new DalException(ex);
    }
    return userDto;
  }

  @Override
  public UserStatus getUserStatus(int userId) throws DalException {
    UserStatus userStatus = null;
    PreparedStatement ps = dalService.getPreparedStatement(
        "SELECT status FROM mystherbe.users WHERE id_user = ?" + " ORDER BY lastname, firstname");
    try {
      ps.setInt(1, userId);
      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        userStatus = UserStatus.getStatusByCode(resultSet.getString(1));
      }

      ps.close();
    } catch (SQLException ex) {
      throw new DalException(ex);
    }
    return userStatus;
  }

  @Override
  public List<UserDto> getUsers(UsersFilterDto usersFilterDto) throws DalException {
    String query;

    if (usersFilterDto != null) {
      query = "SELECT * FROM mystherbe.users "
          + "WHERE lower(lastname) LIKE lower(?) AND lower(city) LIKE lower(?)";
    } else {
      query = "SELECT * FROM mystherbe.users";
    }
    query += " ORDER BY lastname, firstname";

    PreparedStatement ps;
    ps = dalService.getPreparedStatement(query);

    try {
      if (usersFilterDto != null) {
        String name = DalUtils.escapeSpecialLikeChar(usersFilterDto.getName());
        ps.setString(1, name + "%");
        String city = DalUtils.escapeSpecialLikeChar(usersFilterDto.getCity());
        ps.setString(2, city + "%");
      }

      return getUsersViaPs(ps);
    } catch (SQLException ex) {
      throw new DalException(ex);
    }
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
        userDto.setStatus(UserStatus.getStatusByCode(resultSet.getString(9)));
        users.add(userDto);
      }
    }
    ps.close();

    return users;
  }

  @Override
  public boolean checkEmailInDb(String email) throws DalException {
    PreparedStatement ps;
    ps = dalService.getPreparedStatement(
        "Select * FROM mystherbe.users usr WHERE usr.email =?" + " ORDER BY lastname, firstname");

    try {

      ps.setString(1, email);

      try (ResultSet resultSet = ps.executeQuery()) {
        return resultSet.next();

      }
    } catch (SQLException ex) {
      throw new DalException("error with the db");
    }
  }

  @Override
  public boolean checkPseudoInDb(String pseudo) throws DalException {
    PreparedStatement ps;
    ps = dalService.getPreparedStatement(
        "Select * FROM mystherbe.users usr WHERE REGEXP_REPLACE(LOWER(usr.pseudo), '\\s', '', 'g') "
            + "=REGEXP_REPLACE(LOWER(?), '\\s', '', 'g')" + " ORDER BY lastname, firstname");

    try {

      ps.setString(1, pseudo);

      try (ResultSet resultSet = ps.executeQuery()) {
        return resultSet.next();

      }
    } catch (SQLException ex) {
      throw new DalException("error with the db");
    }
  }

  @Override
  public UserDto insertUser(UserDto userDto) throws DalException {
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
      ps.setString(8, userDto.getStatus().getCode());

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        userDto.setId(rs.getInt(1));
        rs.close();
        return userDto;
      } else {
        throw new Exception();
      }

    } catch (Exception ex) {
      throw new DalException("error with the db");
    }
  }

  @Override
  public boolean isLinked(int userId) throws DalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "SELECT * FROM mystherbe.customers WHERE id_user = ?" + " ORDER BY lastname, firstname");
    try {
      ps.setInt(1, userId);
      return ps.executeQuery().next();
    } catch (SQLException ex) {
      throw new DalException("error with the db");
    }
  }

  @Override
  public UserDto changeUserStatus(int userId, UserStatus newStatus) throws DalException {
    PreparedStatement ps =
        dalService.getPreparedStatement("UPDATE mystherbe.users SET status = ? WHERE id_user = ?");
    try {
      ps.setString(1, newStatus.getCode());
      ps.setInt(2, userId);

      ps.executeUpdate();
    } catch (SQLException ex) {
      throw new DalException("error with the db");
    }
    return getUser(userId);
  }
}
