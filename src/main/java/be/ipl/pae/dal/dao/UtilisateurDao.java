package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UtilisateurDto;

public interface UtilisateurDao {

  UtilisateurDto getUtilisateurParPseudo(String pseudo);
}
