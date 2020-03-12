package be.ipl.pae.pae.dal.dao;

import be.ipl.pae.pae.biz.dto.UtilisateurDto;

public interface UtilisateurDao {

  /**
   * Recupere les données d'un utilisateur depuis la bd grace au pseudo.
   *
   * @param pseudo le pseudo de la personne
   * @return Un objet UtilisateurDto avec les informations de la db, sinon renvoi null
   */
  UtilisateurDto getUtilisateurParPseudo(String pseudo);

  /**
   * Recupere les données d'un utilisateur depuis la bd grace a son identifiant.
   *
   * @param idUtilisateur l'id de l'utilisateur
   * @return Un objet UtilisateurDto avec les informations de la db, sinon renvoi null
   */
  UtilisateurDto getUser(int idUtilisateur);
}
