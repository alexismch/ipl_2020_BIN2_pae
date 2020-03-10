package be.ipl.pae.main;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.UtilisateurUcc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.InjectionService;



public class UtilisateurUccImplTest {


  private DtoFactory utilisateurDtoFactory;
  private UtilisateurUcc ucc;

  @BeforeEach
  public void setUp() {
    utilisateurDtoFactory = InjectionService.getDependance(DtoFactory.class);
    ucc = InjectionService.getDependance(UtilisateurUcc.class);

  }

  @DisplayName("Test ucc différent de null")
  @Test
  public void testUcc() {
    assertNotNull(ucc);
  }


  @DisplayName("Test se connecter avec bon pseudo et mdp")
  @Test
  public void testSeConnecterOk() {
    assertNull(ucc.seConnecter("alexismch", "test"));
  }

  @DisplayName("Test se connecter avec mauvais mdp")
  @Test
  public void testSeConnecterko1() {
    assertNull(ucc.seConnecter("alexismch", "blabla"));
  }


  @DisplayName("Test se connecter avec mauvais pseudo")
  @Test
  public void testSeConnecterko2() {
    assertNull(ucc.seConnecter("blabla", "test"));
  }


}
