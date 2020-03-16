package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.UserDto;

public interface User extends UserDto {

  /**
   * Check if the pwd is the same as the one in the database.
   *
   * @param pwd password of the user
   * @return true if the pwd is the same as the one in the db, otherwise false
   */
  boolean verifierMdp(String pwd);
}
