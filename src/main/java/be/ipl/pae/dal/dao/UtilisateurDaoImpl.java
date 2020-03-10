package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UtilisateurDto;
import be.ipl.pae.dal.services.DalService;
import config.InjectionService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UtilisateurDaoImpl implements UtilisateurDao {

  DalService dalService = InjectionService.getDependance(DalService.class);

  @Override
  public UtilisateurDto getUtilisateurParPseudo(String pseudo) {
    UtilisateurDto utilisateurDto = null;
    PreparedStatement ps;
    ps = dalService
        .getPreparedStatement("Select * FROM mystherbe.utilisateurs WHERE pseudo =" + pseudo);

    try (ResultSet resultSet = ps.executeQuery()) {
      resultSet.next();

      utilisateurDto.setId(resultSet.getInt(1));
      utilisateurDto.setPseudo(resultSet.getString(2));
      utilisateurDto.setMdp(resultSet.getString(3));
      utilisateurDto.setNom((resultSet.getString(4)));
      utilisateurDto.setPrenom(resultSet.getString(5));
      utilisateurDto.setVille(resultSet.getString(6));
      utilisateurDto.setEmail(resultSet.getString(7));
      utilisateurDto.setDateInscription(resultSet.getDate(8).toLocalDate());
      utilisateurDto.setId(resultSet.getInt(9));

    } catch (SQLException e) {
      e.printStackTrace();
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
    // TODO Auto-generated method stub
    return null;
  }
}
