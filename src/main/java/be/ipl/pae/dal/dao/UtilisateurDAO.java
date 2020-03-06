package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UtilisateurDTO;

public interface UtilisateurDAO {

  UtilisateurDTO getUtilisateurParPseudo(String pseudo);
}
