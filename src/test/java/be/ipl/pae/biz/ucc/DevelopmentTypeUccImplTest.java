package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.main.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DevelopmentTypeUccImplTest {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private DevelopmentTypeUcc developmentTypeUcc;

  private DevelopmentTypeDto developmentType;

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
    developmentType = dtoFactory.getDevelopmentType();
    developmentType.setIdType(1);
    developmentType.setTitle("Aménagement de jardin de ville");

  }

  @DisplayName("Injection worked")
  @Test
  public void testInjection() {
    assertNotNull(developmentType);
  }

  @DisplayName("list not null")
  @Test
  public void testGetDevelopmentTypes1() throws BizException {
    assertNotNull(developmentTypeUcc.getDevelopmentTypes());
  }

  @DisplayName("list not empty")
  @Test
  public void testGetDevelopmentTypes2() throws BizException {
    assertTrue(developmentTypeUcc.getDevelopmentTypes().size() > 0);
  }

}
