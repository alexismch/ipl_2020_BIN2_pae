package be.ipl.pae.main;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.util.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class UserUccImplTest {


  @Injected
  private DtoFactory userDtoFactory;

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
  public void testloginok() throws BizException {
    assertNotNull(ucc.login("sousou", "123456"));
  }

  @DisplayName("login test when we give a good pseudo and a wrong pwd")

  @Test
  public void testloginko1() {
    assertThrows(BizException.class, () -> ucc.login("sousou", "blabla"));
  }

  @DisplayName("login test when we give a wrong pseudo")

  @Test
  public void testloginko2() {
    assertThrows(BizException.class, () -> ucc.login("blabla", "test"));
  }

  @DisplayName("register test when you give a good pseudo and a good mail")
  @Test
  public void testregisterok() throws BizException, FatalException {
    UserDto userDto = userDtoFactory.getUser();
    userDto.setEmail("goodmail@mail.mail");
    userDto.setPseudo("goodpseudo");
    assertNotNull(ucc.register(userDto));
  }

  @DisplayName("register test when you give a good pseudo and a mail already used")
  @Test
  public void testregisterko1() throws BizException, FatalException {
    UserDto userDto = userDtoFactory.getUser();
    userDto.setEmail("badmail@badmail.badmail");
    userDto.setPseudo("goodpseudo");
    assertThrows(BizException.class, () -> ucc.register(userDto));
  }

  @DisplayName("register test when you give a pseudo already used and a good mail")
  @Test
  public void testregisterko2() throws BizException, FatalException {
    UserDto userDto = userDtoFactory.getUser();
    userDto.setEmail("goodmail@mail.mail");
    userDto.setPseudo("badpseudo");
    assertThrows(BizException.class, () -> ucc.register(userDto));
  }

}
