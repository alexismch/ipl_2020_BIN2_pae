package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UtilisateurDTO;
import be.ipl.pae.biz.objets.Factory;
import be.ipl.pae.dal.dao.UtilisateurDAO;
import be.ipl.pae.dal.dao.UtilisateurDAOImpl;

public class UtilisateurUCCImpl implements UtilisateurUCC {

  Factory factory;

  public UtilisateurDTO seConnecter(String pseudo, String mdp) {
    UtilisateurDTO utilisateurDTO = factory.getUtilisateur();
    UtilisateurDAO utilisateurDao = new UtilisateurDAOImpl();
    utilisateurDTO = utilisateurDao.getUtilisateurParPseudo(pseudo);

    return utilisateurDTO;
  }
}
