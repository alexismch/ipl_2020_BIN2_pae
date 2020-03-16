package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.User;
import be.ipl.pae.dal.dao.UserDao;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;

import java.util.List;


public class UserUccImpl implements UserUcc {

  @Injected
  private UserDao userDao;

  @Override
  public UserDto logIn(String pseudo, String mdp) {
    UserDto utilisateurDto = userDao.getUserByPseudo(pseudo);
    if (utilisateurDto == null) {
      return null;
    }
    if (!((User) utilisateurDto).verifierMdp(mdp)) {
      return null;
    }

    return utilisateurDto;
  }


  @Override
  public UserDto register(UserDto userDto) throws BizException {


    if (userDao.checkPseudoInDb(userDto.getPseudo()))
      throw new BizException("Pseudo already used!");
    if (userDao.checkEmailInDb(userDto.getEmail()))
      throw new BizException("Email already used!");

    return userDao.insertUser(userDto);

  }

  @Override
  public UserDto recuprer(int id) {
    return userDao.getUser(id);
  }

  @Override
  public List<UserDto> getUsers() {
    return userDao.getUsers();
  }

}
