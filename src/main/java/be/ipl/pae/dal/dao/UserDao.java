package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;

public interface UserDao {

  /**
   * Recupere les données d'un utilisateur depuis la bd grace au pseudo.
   *
   * @param pseudo le pseudo de la personne
   * @return Un objet UtilisateurDto avec les informations de la db, sinon renvoie null
   */
  UserDto getUtilisateurParPseudo(String pseudo);

  /**
   * Recupere les données d'un utilisateur depuis la bd grace a son identifiant.
   *
   * @param idUtilisateur l'id de l'utilisateur
   * @return Un objet UtilisateurDto avec les informations de la db, sinon renvoie null
   */
  UserDto getUser(int idUtilisateur);
}
