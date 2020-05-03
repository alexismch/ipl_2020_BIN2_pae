package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.main.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LinkUccImplTest {

  @Injected
  private LinkCcUcc linkCcUcc;

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

  @DisplayName("link test with non-existent customer")
  @Test
  public void testLinkKo1() {
    assertThrows(BizException.class, () -> linkCcUcc.link(3, 1));
  }

  @DisplayName("link test with non-existent user")
  @Test
  public void testLinkKo2() {
    assertThrows(BizException.class, () -> linkCcUcc.link(1, 3));
  }

  @DisplayName("link test with linked customer")
  @Test
  public void testLinkKo3() {
    assertThrows(BizException.class, () -> linkCcUcc.link(1, 1));
  }

  @DisplayName("link test with linked user")
  @Test
  public void testLinkKo4() {
    assertThrows(BizException.class, () -> linkCcUcc.link(2, 2));
  }

  @DisplayName("link test with right parameters")
  @Test
  public void testLinkOk() {
    assertDoesNotThrow(() -> linkCcUcc.link(2, 1));
  }
}
