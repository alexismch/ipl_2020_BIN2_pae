package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.UtilisateurDTO;

public interface Utilisateur extends UtilisateurDTO {

  /**
   * Vérifie si le mdp introduit par l'utilisateur est le meme que celui récupéré dans la bd(celui
   * se trouvant dans la bd est hashé)
   * 
   * @param mdp
   * @return
   */
  boolean verifierMdp(String mdp);

}
