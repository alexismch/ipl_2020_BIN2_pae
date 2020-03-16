package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.User;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.dal.dao.UserDao;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;


public class UserUccImpl implements UserUcc {

  @Injected
  private UserDao userDao;

  @Override
  public UserDto login(String pseudo, String mdp) throws BizException {
    UserDto userDto = userDao.getUserByPseudo(pseudo);
    if (userDto == null || !((User) userDto).verifierMdp(mdp)) {
      throw new BizException("Pseudo ou mot de passe incorrect !");
    }

    if (UserStatus.NOT_ACCEPTED.equals(userDto.getStatus())) {
      throw new BizException("Votre inscription est en attente de validation!");
    }

    return userDto;
  }


  @Override
  public UserDto register(UserDto userDto) throws BizException, FatalException {


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
  public List<UserDto> getUsers(UsersFilterDto usersFilterDto) {
    return userDao.getUsers(usersFilterDto);
  }

}
