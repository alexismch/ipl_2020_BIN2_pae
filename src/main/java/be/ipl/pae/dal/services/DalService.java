package be.ipl.pae.dal.services;

import java.sql.PreparedStatement;

public interface DalService {

  /**
   * Recupère un prepare statement en fonction de la requete qu'on lui donne.
   *
   * @param requete La chaine de charactere contenant la requete
   * @return Un objet Prepare Statement a partir de la requete en parametre
   */
  PreparedStatement getPreparedStatement(String requete);


}
