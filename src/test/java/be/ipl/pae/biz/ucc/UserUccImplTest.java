package be.ipl.pae.biz.ucc;

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
import be.ipl.pae.exceptions.FatalException;
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

  @DisplayName("register test when you give a good pseudo and a good mail")
  @Test
  public void testRegisterOk() throws BizException, FatalException {
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
  public void usersList() throws FatalException {
    assertEquals(4, ucc.getUsers(null).size());
  }

  @DisplayName("users list with filter on name")
  @Test
  public void usersListFilterName() throws FatalException {
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
  public void usersListFilterNameIgnoreCase() throws FatalException {
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
  public void usersListFilterCity() throws FatalException {
    UsersFilterDto usersFilterDto = dtoFactory.getUsersFilterDto();

    usersFilterDto.setCity("2");
    List<UserDto> users = ucc.getUsers(usersFilterDto);

    assertEquals(2, users.size());

    for (UserDto userDto : users) {
      assertTrue(userDto.getCity().startsWith("2"));
    }

  }

  @Test
  void changeUserStatusWrongId() {

    for (UserStatus userStatus : UserStatus.values()) {
      assertThrows(BizException.class, () -> ucc.changeUserStatus(3, userStatus));
    }

  }


  @Test
  void changeUserStatusSameAsExisting() throws FatalException, BizException {

    for (int i = 1; i <= 2; i++) {

      UserDto userDto = ucc.getUser(i);
      assertNotNull(userDto);
      assertEquals(userDto.getPseudo(), ucc.changeUserStatus(i, userDto.getStatus()).getPseudo());

    }

  }

}