package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

  @DisplayName("Insert with existent names")
  @Test
  public void testInsertDevelopmentTypeKo() {
    DevelopmentTypeDto developmentType1 = dtoFactory.getDevelopmentType();
    developmentType1.setTitle("Aménagement de jardin de ville");
    DevelopmentTypeDto developmentType2 = dtoFactory.getDevelopmentType();
    developmentType2.setTitle("Aménagement de jardin");

    assertAll(
        () -> assertThrows(BizException.class, () -> developmentTypeUcc.insert(developmentType1)),
        () -> assertThrows(BizException.class, () -> developmentTypeUcc.insert(developmentType2))
    );
  }

  @DisplayName("Insert with non-existent name")
  @Test
  public void testInsertDevelopmentTypeOk() throws BizException {
    DevelopmentTypeDto developmentType = dtoFactory.getDevelopmentType();
    developmentType.setTitle("Aménagement de parc paysagiste");
    assertEquals(3, developmentTypeUcc.insert(developmentType).getIdType());
  }

  @DisplayName("test get development type via non-existent id")
  @Test
  public void testGetDevelopmentTypeKo() {
    assertThrows(BizException.class, () -> developmentTypeUcc.getDevelopmentType(3));
  }

  @DisplayName("test get development type via existent ids")
  @Test
  public void testGetDevelopmentTypeOk() {
    assertAll(
        () -> assertEquals("Aménagement de jardin de ville",
            developmentTypeUcc.getDevelopmentType(1).getTitle()),
        () -> assertEquals("Aménagement de jardin",
            developmentTypeUcc.getDevelopmentType(2).getTitle())
    );
  }
}
