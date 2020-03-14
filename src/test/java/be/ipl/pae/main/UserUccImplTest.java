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
   * Permet de faire les injections necessaires.
   */
  @BeforeEach
  public void setUp() {
    InjectionService injectionService = new InjectionService();
    try {
      injectionService.loadProperties("test.properties");
    } catch (IOException e) {
      e.printStackTrace();
    }
    injectionService.inject(this);
  }

  @DisplayName("Test ucc différent de null")
  @Test
  public void testUcc() {
    assertNotNull(ucc);
  }


  @DisplayName("Test se connecter avec bon pseudo et mdp")

  @Test
  public void testSeConnecterOk() {
    assertNotNull(ucc.seConnecter("sousou", "123456"));
  }

  @DisplayName("Test se connecter avec mauvais mdp")

  @Test
  public void testSeConnecterko1() {
    assertNull(ucc.seConnecter("sousou", "blabla"));
  }

  @DisplayName("Test se connecter avec mauvais pseudo")

  @Test
  public void testSeConnecterko2() {
    assertNull(ucc.seConnecter("blabla", "test"));
  }

}
