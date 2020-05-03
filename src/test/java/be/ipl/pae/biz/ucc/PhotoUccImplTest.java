package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.dependencies.InjectionService;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.main.PropertiesLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

  @DisplayName("insert photo with wrong type")
  @Test
  public void insertKo1() {
    PhotoDto photoDto = dtoFactory.getPhoto();
    photoDto.setIdType(5);
    List<PhotoDto> list = new ArrayList<>();
    list.add(photoDto);
    assertThrows(BizException.class, () -> photoUcc.insert(list));
  }

  @DisplayName("insert photo with wrong quote state")
  @Test
  public void insertKo2() {
    PhotoDto photoDto = dtoFactory.getPhoto();
    photoDto.setIdType(1);
    photoDto.setIdQuote("introduit");
    List<PhotoDto> list = new ArrayList<>();
    list.add(photoDto);
    assertThrows(BizException.class, () -> photoUcc.insert(list));
  }

  @DisplayName("insert photos with right infos")
  @Test
  public void insertOk() {
    PhotoDto photoDto1 = dtoFactory.getPhoto();
    photoDto1.setIdType(1);
    photoDto1.setIdQuote("Total");

    PhotoDto photoDto2 = dtoFactory.getPhoto();
    photoDto2.setIdType(1);
    photoDto2.setIdQuote("ok");

    List<PhotoDto> list = new ArrayList<>();
    list.add(photoDto2);

    assertDoesNotThrow(() -> photoUcc.insert(list));
  }
}
