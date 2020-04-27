package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.main.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class CustommerUccTest {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private CustomerUcc custcc;

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
    assertNotNull(custcc);
  }

  public void testInsertCustomer() {

  }



}
