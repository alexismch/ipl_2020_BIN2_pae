package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UtilisateurDto;
import be.ipl.pae.biz.objets.Utilisateur;
import be.ipl.pae.dal.dao.UtilisateurDAO;


public class UtilisateurUccImpl implements UtilisateurUcc {

  private UtilisateurDAO utilisateurDAO;


  public UtilisateurUccImpl(UtilisateurDAO utilisateurDAO) {
    super();
    this.utilisateurDAO = utilisateurDAO;
  }


  public UtilisateurDto seConnecter(String pseudo, String mdp) {
    UtilisateurDto utilisateurDTO = utilisateurDAO.getUtilisateurParPseudo(pseudo);
    if (utilisateurDTO == null) {
      return null;
    }
    if (!((Utilisateur) utilisateurDTO).verifierMdp(mdp))
      return null;

    return utilisateurDTO;
  }
}
