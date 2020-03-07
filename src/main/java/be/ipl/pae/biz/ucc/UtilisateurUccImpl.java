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
    UtilisateurDto utilisateurDto = utilisateurDAO.getUtilisateurParPseudo(pseudo);
    if (utilisateurDto == null) {
      return null;
    }
    if (!((Utilisateur) utilisateurDto).verifierMdp(mdp))
      return null;

    return utilisateurDto;
  }
}
