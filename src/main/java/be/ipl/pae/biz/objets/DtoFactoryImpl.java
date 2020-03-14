package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.UserDto;

public class DtoFactoryImpl implements DtoFactory {

  @Override
  public UserDto getUtilisateur() {
    return new UserImpl();
  }
}
