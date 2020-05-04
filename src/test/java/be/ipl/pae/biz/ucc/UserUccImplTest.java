package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.main.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class UserUccImplTest {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private UserUcc ucc;

  /**
   * Allows you to make the necessary injections.
   */
  @BeforeEach
  public void setUp() {
    PropertiesLoader propertiesLoader = new PropertiesLoader();
    try {
      propertiesLoader.loadProperties("props/test.properties");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    InjectionService injectionService = new InjectionService(propertiesLoader);
    injectionService.inject(this);
  }

  @DisplayName("ucc test different from null")
  @Test
  public void testUcc() {
    assertNotNull(ucc);
  }

  @DisplayName("login test when we give a good pseudo and pwd")
  @Test
  public void testLoginOk() throws BizException {
    assertNotNull(ucc.login("sousou", "123456"));
  }

  @DisplayName("login test when we give a good pseudo and a wrong pwd")
  @Test
  public void testLoginKo1() {
    assertThrows(BizException.class, () -> ucc.login("sousou", "blabla"));
  }

  @DisplayName("login test when we give a wrong pseudo")
  @Test
  public void testLoginKo2() {
    assertThrows(BizException.class, () -> ucc.login("blabla", "test"));
  }

  @DisplayName("login test when we give a good pseudo and pwd but not accepted status")
  @Test
  public void testLoginKo3() {
    assertThrows(BizException.class, () -> ucc.login("nop", "123456"));
  }

  @DisplayName("register test when you give a good pseudo and a good mail")
  @Test
  public void testRegisterOk() throws BizException {
    UserDto userDto = dtoFactory.getUser();
    userDto.setEmail("goodmail@mail.mail");
    userDto.setPseudo("goodpseudo");
    assertNotNull(ucc.register(userDto));
  }

  @DisplayName("register test when you give a good pseudo and a mail already used")
  @Test
  public void testRegisterKo1() {
    UserDto userDto = dtoFactory.getUser();
    userDto.setEmail("badmail@badmail.badmail");
    userDto.setPseudo("goodpseudo");
    assertThrows(BizException.class, () -> ucc.register(userDto));
  }

  @DisplayName("register test when you give a pseudo already used and a good mail")
  @Test
  public void testRegisterKo2() {
    UserDto userDto = dtoFactory.getUser();
    userDto.setEmail("goodmail@mail.mail");
    userDto.setPseudo("badpseudo");
    assertThrows(BizException.class, () -> ucc.register(userDto));
  }

  @DisplayName("users list with no filter")
  @Test
  public void usersList() {
    assertEquals(4, ucc.getUsers(null).size());
  }

  @DisplayName("users list with filter on name")
  @Test
  public void usersListFilterName() {
    UsersFilterDto usersFilterDto = dtoFactory.getUsersFilterDto();

    usersFilterDto.setName("A");
    List<UserDto> users = ucc.getUsers(usersFilterDto);

    assertEquals(3, users.size());

    for (UserDto userDto : users) {
      assertTrue(userDto.getLastName().startsWith("A"));
    }
  }

  @DisplayName("users list with filter on name case insensitive")
  @Test
  public void usersListFilterNameIgnoreCase() {
    UsersFilterDto usersFilterDto = dtoFactory.getUsersFilterDto();

    usersFilterDto.setName("a");
    List<UserDto> users = ucc.getUsers(usersFilterDto);

    assertEquals(3, users.size());

    for (UserDto userDto : users) {
      assertTrue(userDto.getLastName().startsWith("A"));
    }
  }

  @DisplayName("users list with filter on city")
  @Test
  public void usersListFilterCity() {
    UsersFilterDto usersFilterDto = dtoFactory.getUsersFilterDto();

    usersFilterDto.setCity("2");
    List<UserDto> users = ucc.getUsers(usersFilterDto);

    assertEquals(2, users.size());

    for (UserDto userDto : users) {
      assertTrue(userDto.getCity().startsWith("2"));
    }
  }

  @DisplayName("test change user status with wrong id")
  @Test
  void testChangeUserStatusKo1() {
    for (UserStatus userStatus : UserStatus.values()) {
      assertThrows(BizException.class, () -> ucc.changeUserStatus(3, userStatus));
    }
  }

  @DisplayName("test change user status with already good status")
  @Test
  void testChangeUserStatusKo2() {
    assertAll(
        () -> assertThrows(BizException.class, () -> ucc.changeUserStatus(1, UserStatus.CUSTOMER)),
        () -> assertThrows(BizException.class, () -> ucc.changeUserStatus(2, UserStatus.WORKER))
    );
  }

  @DisplayName("test change user status with not connected status")
  @Test
  void testChangeUserStatusOk() {
    assertAll(
        () -> assertEquals(UserStatus.CUSTOMER,
            ucc.changeUserStatus(3, UserStatus.CUSTOMER).getStatus()),
        () -> assertEquals(UserStatus.WORKER,
            ucc.changeUserStatus(3, UserStatus.WORKER).getStatus())
    );
  }
}
