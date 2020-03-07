package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.UtilisateurDto;

public class FactoryImpl implements Factory {

  @Override
  public UtilisateurDto getUtilisateur() {
    return new UtilisateurImpl();
  }
}
