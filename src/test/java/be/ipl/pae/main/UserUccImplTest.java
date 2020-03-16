package be.ipl.pae.main;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.UserUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class UserUccImplTest {


  @Injected
  private DtoFactory utilisateurDtoFactory;

  @Injected
  private UserUcc ucc;

  /**
   * Allows you to make the necessary injections.
   */
  @BeforeEach
  public void setUp() {
    InjectionService injectionService = new InjectionService();
    try {
      injectionService.loadProperties("test.properties");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    injectionService.inject(this);
  }

  @DisplayName("ucc test different from null")
  @Test
  public void testUcc() {
    assertNotNull(ucc);
  }


  @DisplayName("login test when we give a good pseudo and pwd")

  @Test
  public void testSeConnecterOk() {
    assertNotNull(ucc.logIn("sousou", "123456"));
  }

  @DisplayName("login test when we give a good pseudo and a wrong pwd")

  @Test
  public void testSeConnecterko1() {
    assertNull(ucc.logIn("sousou", "blabla"));
  }

  @DisplayName("login test when we give a wrong pseudo")

  @Test
  public void testSeConnecterko2() {
    assertNull(ucc.logIn("blabla", "test"));
  }

}
