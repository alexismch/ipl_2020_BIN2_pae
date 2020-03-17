package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;

import org.mindrot.bcrypt.BCrypt;

import java.util.List;

public class MockUserDao implements UserDao {

  @Injected
  private DtoFactory dtoFactory;


  @Override
  public UserDto getUserByPseudo(String pseudo) {
    if (!pseudo.equals("sousou")) {
      return null;
    }

    UserDto utilisateurDto = dtoFactory.getUser();
    utilisateurDto.setPseudo("sousou");
    utilisateurDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
    utilisateurDto.setId(1);

    return utilisateurDto;
  }

  @Override
  public UserDto getUser(int idUtilisateur) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<UserDto> getUsers(UsersFilterDto usersFilterDto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean checkEmailInDb(String email) {
    return email.equals("badmail@badmail.badmail");
  }

  @Override
  public boolean checkPseudoInDb(String pseudo) {
    return pseudo.equals("badpseudo");
  }

  @Override
  public UserDto insertUser(UserDto userDto) {
    return userDto;
  }

}
