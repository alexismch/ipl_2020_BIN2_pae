package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UtilisateurDTO;
import be.ipl.pae.biz.objets.Factory;

public class UtilisateurUCCImpl implements UtilisateurUCC {

  Factory factory;

  public UtilisateurDTO seConnecter(String pseudo, String mdp) {
    UtilisateurDTO utilisateurDTO = factory.getUtilisateur();

    return null;
  }
}
