package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public interface UserUcc {

  /**
   * Allows the user to connect, we will check if the pseudo exists and if the user gave the correct
   * password.
   *
   * @param pseudo pseudo of the user
   * @param pwd user's password
   * @return an UserDto object that represent the user
   * @throws BizException Thrown if pseudo or pwd is incorrect or if the user status equals to
   *         NOT_ACCEPTED
   */
  UserDto login(String pseudo, String pwd) throws BizException;

  /**
   * Insert an user in the database.
   *
   * @param userDto the user that we need to insert in the db
   * @return the registered user
   * @throws BizException if the email or pseudo is already used
   * @throws FatalException if you had a problem with the db
   */
  UserDto register(UserDto userDto) throws BizException, FatalException;

  /**
   * Get the user with his id.
   *
   * @param id the id of the user that you need to get
   * @return null if you had an error or an object of type UserDto
   */
  UserDto recuprer(int id);

  /**
   * Get all the users saved in the database.
   *
   * @param usersFilterDto a filter applied to the results or null if no filter should be applied
   * @return A list of all users
   */
  List<UserDto> getUsers(UsersFilterDto usersFilterDto);

}
