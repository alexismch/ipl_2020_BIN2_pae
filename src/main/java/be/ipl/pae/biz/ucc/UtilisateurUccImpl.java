package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UtilisateurDto;
import be.ipl.pae.biz.objets.Utilisateur;
import be.ipl.pae.dal.dao.UtilisateurDao;
import config.InjectionService;


public class UtilisateurUccImpl implements UtilisateurUcc {

  private UtilisateurDao utilisateurDao;


  public UtilisateurUccImpl() {
    super();
    this.utilisateurDao = InjectionService.getDependance(UtilisateurDao.class);
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

  @Override
  public UtilisateurDto recuprer(int id) {
    return utilisateurDao.getUser(id);
  }
}
