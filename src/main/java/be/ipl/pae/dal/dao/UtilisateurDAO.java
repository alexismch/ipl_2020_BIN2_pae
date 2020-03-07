package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UtilisateurDto;

public interface UtilisateurDAO {

  UtilisateurDto getUtilisateurParPseudo(String pseudo);
}
