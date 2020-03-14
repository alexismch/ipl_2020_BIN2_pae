package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;

import org.mindrot.bcrypt.BCrypt;

public class MockUserDao implements UserDao {

  @Injected
  private DtoFactory dtoFactory;


  @Override
  public UserDto getUtilisateurParPseudo(String pseudo) {
    if (!pseudo.equals("sousou")) {
      return null;
    }

    UserDto utilisateurDto = dtoFactory.getUtilisateur();
    utilisateurDto.setPseudo("sousou");
    utilisateurDto.setMdp(BCrypt.hashpw("123456", BCrypt.gensalt()));
    utilisateurDto.setId(1);


    return utilisateurDto;
  }

  @Override
  public UserDto getUser(int idUtilisateur) {
    // TODO Auto-generated method stub
    return null;
  }

}
