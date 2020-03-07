package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.UtilisateurDto;

public interface Utilisateur extends UtilisateurDto {

  /**
   * Vérifie si le mdp introduit par l'utilisateur est le meme que celui récupéré dans la bd(celui
   * se trouvant dans la bd est hashé)
   * 
   * @param mdp mot de passe de l'utilisateur
   * @return true si le mdp donné est le meme que celui se trouvant dans la bd, false sinon
   */
  boolean verifierMdp(String mdp);

}
