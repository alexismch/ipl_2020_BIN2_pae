package be.ipl.pae.dal.services;

import java.sql.PreparedStatement;

public interface DALService {

  /**
   * @param requete La chaine de charactere contenant la requete
   * @return Un objet Prepare Statement a partir de la requete en parametre
   */
  public PreparedStatement getPreparedStatement(String requete);

}
