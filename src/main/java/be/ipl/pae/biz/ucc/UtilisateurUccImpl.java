package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UtilisateurDto;
import be.ipl.pae.biz.objets.Utilisateur;
import be.ipl.pae.dal.dao.UtilisateurDao;


public class UtilisateurUccImpl implements UtilisateurUcc {

  private UtilisateurDao utilisateurDao;


  public UtilisateurUccImpl(UtilisateurDao utilisateurDao) {
    super();
    this.utilisateurDao = utilisateurDao;
  }


  public UtilisateurDto seConnecter(String pseudo, String mdp) {
    UtilisateurDto utilisateurDto = utilisateurDao.getUtilisateurParPseudo(pseudo);
    if (utilisateurDto == null) {
      return null;
    }
    if (!((Utilisateur) utilisateurDto).verifierMdp(mdp)) {
      return null;
    }

    return utilisateurDto;
  }
}
