package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UtilisateurDTO;

public interface UtilisateurUCC {

  UtilisateurDTO seConnecter(String pseudo, String mdp);
}
