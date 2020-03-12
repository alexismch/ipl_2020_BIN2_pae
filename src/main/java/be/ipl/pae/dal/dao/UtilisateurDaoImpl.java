package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UtilisateurDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.main.Inject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UtilisateurDaoImpl implements UtilisateurDao {

  @Inject
  DalService dalService;
  @Inject
  DtoFactory utilisateurDtoFactory;


  @Override
  public UtilisateurDto getUtilisateurParPseudo(String pseudo) {

    UtilisateurDto utilisateurDto = null;
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.utilisateurs WHERE pseudo =?");

    try {

      ps.setString(1, pseudo);

      utilisateurDto = getUserViaPs(ps);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return utilisateurDto;
  }

  // pas sur si je dois utiliser ca pour l'instant

  /**
   * private void setValeurResutlset(ResultSet rs) { try { ResultSetMetaData rsMetaData =
   * rs.getMetaData(); List<Method> tousLesSetters = new ArrayList<Method>(); for (Method method :
   * UtilisateurDto.class.getDeclaredMethods()) { if (method.getName().startsWith("set")) {
   * tousLesSetters.add(method); } } } catch (SQLException e) { // TODO Auto-generated catch block
   * e.printStackTrace(); } }
   */

  @Override
  public UtilisateurDto getUser(int idUtilisateur) {

    UtilisateurDto utilisateurDto = null;
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.utilisateurs WHERE id_util =?");

    try {
      ps.setInt(1, idUtilisateur);
      utilisateurDto = getUserViaPs(ps);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return utilisateurDto;
  }

  /**
   * Retourne l'utilisateur depuis la requête.
   *
   * @param ps la requête exécutée
   * @return Un objet UtilisateurDto avec les informations de la db, sinon renvoie null
   * @throws SQLException en cas d'erreur de requête
   */
  private UtilisateurDto getUserViaPs(PreparedStatement ps) throws SQLException {
    UtilisateurDto utilisateurDto = null;
    try (ResultSet resultSet = ps.executeQuery()) {
      while (resultSet.next()) {
        utilisateurDto = utilisateurDtoFactory.getUtilisateur();
        utilisateurDto.setId(resultSet.getInt(1));
        utilisateurDto.setPseudo(resultSet.getString(2));
        utilisateurDto.setMdp(resultSet.getString(3));
        utilisateurDto.setNom(resultSet.getString(4));
        utilisateurDto.setPrenom(resultSet.getString(5));
        utilisateurDto.setVille(resultSet.getString(6));
        utilisateurDto.setEmail(resultSet.getString(7));
        utilisateurDto.setDateInscription(resultSet.getDate(8).toLocalDate());
        utilisateurDto.setStatut(resultSet.getString(9));
      }
    }
    ps.close();

    return utilisateurDto;
  }
}
