package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;

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
  public UserDto getUtilisateurParPseudo(String pseudo) {

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
   private void setValeurResutlset(ResultSet rs) { try { ResultSetMetaData rsMetaData =
   rs.getMetaData(); List<Method> tousLesSetters = new ArrayList<Method>(); for (Method method :
   UtilisateurDto.class.getDeclaredMethods()) { if (method.getName().startsWith("set")) {
   tousLesSetters.add(method); } } } catch (SQLException e) {
   e.printStackTrace(); } }
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
   * Return list of users created from the request. If only one user is required you can call {@link
   * List#get} with the index 0.
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
        userDto.setMdp(resultSet.getString(3));
        userDto.setNom(resultSet.getString(4));
        userDto.setPrenom(resultSet.getString(5));
        userDto.setVille(resultSet.getString(6));
        userDto.setEmail(resultSet.getString(7));
        userDto.setDateInscription(resultSet.getDate(8).toLocalDate());
        userDto.setStatut(resultSet.getString(9));
        users.add(userDto);
      }
    }
    ps.close();

    return users;
  }
}
