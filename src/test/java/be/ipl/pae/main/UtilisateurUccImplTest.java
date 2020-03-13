package be.ipl.pae.main;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.UtilisateurUcc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.InjectionService;


public class UtilisateurUccImplTest {


  @Inject
  private DtoFactory utilisateurDtoFactory;

  @Inject
  private UtilisateurUcc ucc;

  /**
   * Permet de faire les injections necessaires.
   */
  @BeforeEach
  public void setUp() {
    InjectionService injectionService = new InjectionService();
    injectionService.loadProperties("test.properties");
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
