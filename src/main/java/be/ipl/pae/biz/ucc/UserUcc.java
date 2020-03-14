package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDto;

import java.util.List;

public interface UserUcc {

  /**
   * Permet à l'utilisateur de se connecter, on va vérifier si le pseudo donné par l'utilisateur
   * existe et si le mdp qu'il a donné est le bon.
   *
   * @param pseudo pseudo de l'utilisateur
   * @param mdp    mot de passe de l'utilisateur
   * @return null si il y a eu une erreur ou bien un objet de type UtilisateurDTO si tout est bon
   */
  UserDto seConnecter(String pseudo, String mdp);

  /**
   * Récupère l'utilisateur avec son id.
   *
   * @param id l'id de l'utilisateur à récupérer
   * @return null si il y a eu une erreur ou bien un objet de type UtilisateurDTO si tout est bon
   */
  UserDto recuprer(int id);

  /**
   * Get all the users saved in the database
   *
   * @return A list of all users
   */
  List<UserDto> getUsers();

}
