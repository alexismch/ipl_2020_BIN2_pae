package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public interface UserDao {

  /**
   * Update the status of the user.
   *
   * @param userId    the id of the user
   * @param newStatus the status that will be applied to the user
   * @return An object UserDto with the user with new status
   * @throws FatalException if you have an error with the db
   */
  UserDto changeUserStatus(int userId, UserStatus newStatus) throws FatalException;

  /**
   * Return an userDto from the database.
   *
   * @param pseudo the pseudo of the user
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
   * Get the status of the user by id.
   *
   * @param userId the id of the user from who you need to get the status
   * @return an {@link UserStatus} or null if the user do not exist
   * @throws FatalException if an error occurred with the database.
   */
  UserStatus getUserStatus(int userId) throws FatalException;

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
   * @param email email that you need to check
   * @return true if the email is already in the database otherwise false
   * @throws FatalException if you have an error with the db
   */
  boolean checkEmailInDb(String email) throws FatalException;

  /**
   * check if an pseudo is already used.
   *
   * @param pseudo pseudo that you need to check
   * @return true if the pseudo is already in the database otherwise false
   * @throws FatalException if you have an error with the db
   */
  boolean checkPseudoInDb(String pseudo) throws FatalException;

  /**
   * insert a new user in the database.
   *
   * @param userDto the user that you need to insert
   * @return an userDto if he is insert or null if he's not
   * @throws FatalException if you have an error with the db
   */
  UserDto insertUser(UserDto userDto) throws FatalException;

  /**
   * Verify if the user is linked to a customer.
   *
   * @param userId the customer id
   * @return true if the user is linked to a customer, false if not
   * @throws FatalException if you have an error with the db
   */
  boolean isLinked(int userId) throws FatalException;
}
