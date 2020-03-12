package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.UtilisateurDto;

public class DtoFactoryImpl implements DtoFactory {

  @Override
  public UtilisateurDto getUtilisateur() {
    return new UtilisateurImpl();
  }
}
