package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UtilisateurDto;

public interface UtilisateurUcc {

  /**
   * Permet à l'utilisateur de se connecter, on va vérifier si le pseudo donné par l'utilisateur
   * existe et si le mdp qu'il a donné est le bon.
   * 
   * @param pseudo pseudo de l'utilisateur
   * @param mdp mot de passe de l'utilisateur
   * @return null si il y a eu une erreur ou bien un objet de type UtilisateurDTO si tout est bon
   */
  UtilisateurDto seConnecter(String pseudo, String mdp);

}
