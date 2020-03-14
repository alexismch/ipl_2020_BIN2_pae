package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.User;
import be.ipl.pae.dal.dao.UserDao;
import be.ipl.pae.dependencies.Injected;

import java.util.List;


public class UserUccImpl implements UserUcc {

  @Injected
  private UserDao userDao;

  @Override
  public UserDto seConnecter(String pseudo, String mdp) {
    UserDto utilisateurDto = userDao.getUtilisateurParPseudo(pseudo);
    if (utilisateurDto == null) {
      return null;
    }
    if (!((User) utilisateurDto).verifierMdp(mdp)) {
      return null;
    }

    return utilisateurDto;
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
