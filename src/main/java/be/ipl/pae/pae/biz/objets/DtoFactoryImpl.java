package be.ipl.pae.pae.biz.objets;

import be.ipl.pae.pae.biz.dto.UtilisateurDto;

public class DtoFactoryImpl implements DtoFactory {

  @Override
  public UtilisateurDto getUtilisateur() {
    return new UtilisateurImpl();
  }
}
