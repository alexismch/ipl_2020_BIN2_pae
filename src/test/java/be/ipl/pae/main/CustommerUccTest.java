package be.ipl.pae.main;

import static org.junit.jupiter.api.Assertions.fail;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.CustomerUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.util.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class CustommerUccTest {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private CustomerUcc qcc;

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

  @Test
  void test() {
    fail("Not yet implemented");
  }



}
