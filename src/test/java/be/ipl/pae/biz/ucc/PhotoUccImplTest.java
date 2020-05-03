package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.main.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PhotoUccImplTest {

  @Injected
  private DtoFactory dtoFactory;

  @Injected
  private PhotoUcc photoUcc;

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

  @DisplayName("get visible photos with existant typeId's")
  @Test
  public void getVisiblesPhotosOk() throws BizException {
    assertEquals(5, photoUcc.getVisiblePhotos().size());
  }

  @DisplayName("get visible photos with non-existant typeId")
  @Test
  public void getVisiblesPhotosByDevTypeKo() {
    assertThrows(BizException.class, () -> photoUcc.getVisiblePhotos(3));
  }

  @DisplayName("get visible photos with existant typeId's")
  @Test
  public void getVisiblesPhotosByDevTypeOk() {
    assertAll(
        () -> assertEquals(2, photoUcc.getVisiblePhotos(1).size()),
        () -> assertEquals(3, photoUcc.getVisiblePhotos(2).size())
    );
  }
}
