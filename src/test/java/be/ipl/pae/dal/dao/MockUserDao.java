package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.dependencies.Injected;

import org.mindrot.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockUserDao implements UserDao {

  @Injected
  private DtoFactory dtoFactory;

  @Override
  public UserDto changeUserStatus(int userId, UserStatus newStatus) {
    switch (userId) {
      case 1: {
        UserDto userDto = dtoFactory.getUser();
        userDto.setPseudo("sousou");
        userDto.setId(userId);
        userDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        userDto.setStatus(newStatus);
        return userDto;
      }
      case 2: {
        UserDto userDto = dtoFactory.getUser();
        userDto.setPseudo("yessai");
        userDto.setId(userId);
        userDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        userDto.setStatus(newStatus);
        return userDto;
      }
      case 3: {
        UserDto userDto = dtoFactory.getUser();
        userDto.setPseudo("nop");
        userDto.setId(userId);
        userDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        userDto.setStatus(newStatus);
        return userDto;
      }
      default:
        return null;
    }
  }

  @Override
  public UserDto getUserByPseudo(String pseudo) {
    switch (pseudo) {
      case "sousou": {
        UserDto utilisateurDto = dtoFactory.getUser();
        utilisateurDto.setPseudo("sousou");
        utilisateurDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        utilisateurDto.setId(1);
        utilisateurDto.setStatus(UserStatus.WORKER);
        return utilisateurDto;
      }
      case "yessai": {
        UserDto utilisateurDto = dtoFactory.getUser();
        utilisateurDto.setPseudo("yessai");
        utilisateurDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        utilisateurDto.setId(2);
        utilisateurDto.setStatus(UserStatus.CUSTOMER);
        return utilisateurDto;
      }
      case "nop": {
        UserDto utilisateurDto = dtoFactory.getUser();
        utilisateurDto.setPseudo("nop");
        utilisateurDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        utilisateurDto.setId(3);
        utilisateurDto.setStatus(UserStatus.NOT_ACCEPTED);
        return utilisateurDto;
      }
      default:
        return null;
    }
  }

  @Override
  public UserDto getUser(int idUtilisateur) {
    switch (idUtilisateur) {
      case 1: {
        UserDto userDto = dtoFactory.getUser();
        userDto.setPseudo("sousou");
        userDto.setId(idUtilisateur);
        userDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        userDto.setStatus(UserStatus.WORKER);
        return userDto;
      }
      case 2: {
        UserDto userDto = dtoFactory.getUser();
        userDto.setPseudo("yessai");
        userDto.setId(idUtilisateur);
        userDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        userDto.setStatus(UserStatus.CUSTOMER);
        return userDto;
      }
      case 3: {
        UserDto userDto = dtoFactory.getUser();
        userDto.setPseudo("nop");
        userDto.setId(idUtilisateur);
        userDto.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
        userDto.setStatus(UserStatus.NOT_ACCEPTED);
        return userDto;
      }
      default:
        return null;
    }
  }

  @Override
  public UserStatus getUserStatus(int id) {
    switch (id) {
      case 1:
        return UserStatus.WORKER;
      case 2:
        return UserStatus.CUSTOMER;
      case 3:
        return UserStatus.NOT_ACCEPTED;
      default:
        return null;
    }
  }

  @Override
  public List<UserDto> getUsers(UsersFilterDto usersFilterDto) {
    List<UserDto> usersList = new ArrayList<>();

    UserDto userDto1 = dtoFactory.getUser();
    usersList.add(userDto1);
    userDto1.setId(1);
    userDto1.setPseudo("OK1");
    userDto1.setCity("1");
    userDto1.setFirstName("az");
    userDto1.setLastName("AZ");

    UserDto userDto2 = dtoFactory.getUser();
    usersList.add(userDto2);
    userDto2.setId(2);
    userDto2.setPseudo("OK2");
    userDto2.setCity("2skyCity");
    userDto2.setFirstName("bz");
    userDto2.setLastName("BZ");

    UserDto userDto3 = dtoFactory.getUser();
    usersList.add(userDto3);
    userDto3.setId(3);
    userDto3.setPseudo("OK3");
    userDto3.setCity("2");
    userDto3.setFirstName("ac");
    userDto3.setLastName("Ac");

    UserDto userDto4 = dtoFactory.getUser();
    usersList.add(userDto4);
    userDto4.setId(4);
    userDto4.setPseudo("OK4");
    userDto4.setCity("1");
    userDto4.setFirstName("a");
    userDto4.setLastName("A");

    if (usersFilterDto == null) {
      return usersList;
    }

    return usersList.stream()
        .filter(userDto -> userDto.getLastName().toLowerCase().startsWith(
            usersFilterDto.getName() == null ? "" : usersFilterDto.getName().toLowerCase()))
        .filter(userDto -> userDto.getCity().toLowerCase().startsWith(
            usersFilterDto.getCity() == null ? "" : usersFilterDto.getCity().toLowerCase()))
        .collect(Collectors.toList());
  }

  @Override
  public boolean checkEmailInDb(String email) {
    return email.equals("badmail@badmail.badmail");
  }

  @Override
  public boolean checkPseudoInDb(String pseudo) {
    return pseudo.replaceAll("\\s", "").equalsIgnoreCase("badpseudo");
  }

  @Override
  public UserDto insertUser(UserDto userDto) {
    return userDto;
  }

  @Override
  public boolean isLinked(int userId) {
    return userId == 2;
  }
}
