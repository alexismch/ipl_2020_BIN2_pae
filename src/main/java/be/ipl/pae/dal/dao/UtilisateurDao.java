package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UtilisateurDto;

public interface UtilisateurDao {

  /**
   * Recupere les données d'un utilisateur depuis la bd grace au pseudo
   * 
   * @param pseudo
   * @return Un objet UtilisateurDto avec les informations de la db, sinon renvoi null
   */
  public UtilisateurDto getUtilisateurParPseudo(String pseudo);

  /**
   * Recupere les données d'un utilisateur depuis la bd grace a son identifiant
   * 
   * @param idUtilisateur
   * @return Un objet UtilisateurDto avec les informations de la db, sinon renvoi null
   */
  public UtilisateurDto getUser(int idUtilisateur);
}
