package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public interface UserDao {

  /**
   * Return an userDto from the database.
   *
   * @param pseudo le pseudo de la personne
   * @return An object UserDto with the information from the db or return null
   */
  UserDto getUserByPseudo(String pseudo);

  /**
   * Get the data of an user thanks to his id.
   *
   * @param userId id of the user
   * @return An userDto object with all the informations that the db gave you or null
   */
  UserDto getUser(int userId);

  /**
   * Get all the users saved in the database.
   *
   * @param usersFilterDto a filter applied to the results or null if no filter should be applied
   * @return A list of all users
   */
  List<UserDto> getUsers(UsersFilterDto usersFilterDto);

  /**
   * check if an email is already used.
   * 
   * @param email
   * @return true if the email is already in the database otherwise false
   * @throws FatalException
   */
  boolean checkEmailInDb(String email) throws FatalException;

  /**
   * check if an pseudo is already used.
   * 
   * @param pseudo
   * @return true if the pseudo is already in the database otherwise false
   * @throws FatalException
   */
  boolean checkPseudoInDb(String pseudo) throws FatalException;

  /**
   * insert a new user in the database.
   * 
   * @param userDto the user that you need to insert
   * @return an userDto if he is insert or null if he's not
   * @throws FatalException
   */
  UserDto insertUser(UserDto userDto) throws FatalException;
}
