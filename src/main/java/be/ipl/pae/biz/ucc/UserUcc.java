package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.exceptions.BizException;

import java.util.List;

public interface UserUcc {

  /**
   * Allows the user to connect, we will check if the usersname giver by the user exists and if he
   * gave the correct password
   *
   * @param pseudo pseudo of the user
   * @param pwd user's password
   * @return null if we have an error or an UserDto object if we don't have a problem
   */
  UserDto logIn(String pseudo, String pwd);

  /**
   * insert an user in the database
   * 
   * @param userDto the user that we need to insert in the db
   * @return
   * @throws BizException
   * @throws FatalException
   */
  UserDto register(UserDto userDto) throws BizException, FatalException;

  /**
   * Récupère l'utilisateur avec son id.
   *
   * @param id l'id de l'utilisateur à récupérer
   * @return null si il y a eu une erreur ou bien un objet de type UtilisateurDTO si tout est bon
   */
  UserDto recuprer(int id);

  /**
   * Get all the users saved in the database.
   *
   * @return A list of all users
   */
  List<UserDto> getUsers();

}
