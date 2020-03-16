package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;

public class DtoFactoryImpl implements DtoFactory {

  @Override
  public UserDto getUtilisateur() {
    return new UserImpl();
  }

  @Override
  public UsersFilterDto getUsersFilterDto() {
    return new UsersFilter();
  }
}
