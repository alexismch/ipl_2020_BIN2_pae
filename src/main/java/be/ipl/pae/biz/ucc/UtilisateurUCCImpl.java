package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UtilisateurDTO;
import be.ipl.pae.biz.objets.Utilisateur;
import be.ipl.pae.dal.dao.UtilisateurDAO;


public class UtilisateurUCCImpl implements UtilisateurUCC {

  private UtilisateurDAO utilisateurDAO;


  public UtilisateurUCCImpl(UtilisateurDAO utilisateurDAO) {
    super();
    this.utilisateurDAO = utilisateurDAO;
  }


  public UtilisateurDTO seConnecter(String pseudo, String mdp) {
    UtilisateurDTO utilisateurDTO = utilisateurDAO.getUtilisateurParPseudo(pseudo);
    if (utilisateurDTO == null) {
      return null;
    }
    if (!((Utilisateur) utilisateurDTO).verifierMdp(mdp))
      return null;

    return utilisateurDTO;
  }
}
