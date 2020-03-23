package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;

import org.mindrot.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    List<UserDto> usersList = new ArrayList<>();

    UserDto userDto1 = dtoFactory.getUser();
    userDto1.setId(1);
    userDto1.setPseudo("OK1");
    userDto1.setCity("1");
    userDto1.setFirstName("az");
    userDto1.setLastName("AZ");
    usersList.add(userDto1);

    UserDto userDto2 = dtoFactory.getUser();
    userDto2.setId(2);
    userDto2.setPseudo("OK2");
    userDto2.setCity("2skyCity");
    userDto2.setFirstName("bz");
    userDto2.setLastName("BZ");
    usersList.add(userDto2);

    UserDto userDto3 = dtoFactory.getUser();
    userDto3.setId(3);
    userDto3.setPseudo("OK3");
    userDto3.setCity("2");
    userDto3.setFirstName("ac");
    userDto3.setLastName("Ac");
    usersList.add(userDto3);

    UserDto userDto4 = dtoFactory.getUser();
    userDto4.setId(4);
    userDto4.setPseudo("OK4");
    userDto4.setCity("1");
    userDto4.setFirstName("a");
    userDto4.setLastName("A");
    usersList.add(userDto4);

    if (usersFilterDto == null) {
      return usersList;
    }

    return usersList.stream()
        .filter(userDto -> userDto.getLastName()
            .startsWith(usersFilterDto.getName() == null ? "" : usersFilterDto.getName()))
        .filter(userDto -> userDto.getCity()
            .startsWith(usersFilterDto.getCity() == null ? "" : usersFilterDto.getCity()))
        .collect(Collectors.toList());
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
