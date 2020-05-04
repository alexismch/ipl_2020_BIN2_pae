package be.ipl.pae.biz.ucc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
  public void testGetVisiblesPhotosOk() throws BizException {
    assertEquals(5, photoUcc.getVisiblePhotos().size());
  }

  @DisplayName("get visible photos with non-existant typeId")
  @Test
  public void testGetVisiblesPhotosByDevTypeKo() {
    assertThrows(BizException.class, () -> photoUcc.getVisiblePhotos(3));
  }

  @DisplayName("get visible photos with existant typeId's")
  @Test
  public void testGetVisiblesPhotosByDevTypeOk() {
    assertAll(
        () -> assertEquals(2, photoUcc.getVisiblePhotos(1).size()),
        () -> assertEquals(3, photoUcc.getVisiblePhotos(2).size())
    );
  }

  @DisplayName("insert photo with wrong type")
  @Test
  public void testInsertKo1() {
    PhotoDto photoDto = dtoFactory.getPhoto();
    photoDto.setIdType(5);
    List<PhotoDto> list = new ArrayList<>();
    list.add(photoDto);
    assertThrows(BizException.class, () -> photoUcc.insert(list));
  }

  @DisplayName("insert photo with wrong quote state")
  @Test
  public void testInsertKo2() {
    PhotoDto photoDto = dtoFactory.getPhoto();
    photoDto.setIdType(1);
    photoDto.setIdQuote("introduit");
    List<PhotoDto> list = new ArrayList<>();
    list.add(photoDto);
    assertThrows(BizException.class, () -> photoUcc.insert(list));
  }

  @DisplayName("insert photos with right infos, state 6")
  @Test
  public void testInsertOk1() {
    PhotoDto photoDto = dtoFactory.getPhoto();
    photoDto.setIdType(1);
    photoDto.setIdQuote("Total");

    List<PhotoDto> list = new ArrayList<>();
    list.add(photoDto);

    assertDoesNotThrow(() -> photoUcc.insert(list));
  }

  @DisplayName("insert photos with right infos, state 7")
  @Test
  public void testInsertOk2() {
    PhotoDto photoDto = dtoFactory.getPhoto();
    photoDto.setIdType(1);
    photoDto.setIdQuote("ok");

    List<PhotoDto> list = new ArrayList<>();
    list.add(photoDto);

    assertDoesNotThrow(() -> photoUcc.insert(list));
  }

  @DisplayName("get photo with non-existent id")
  @Test
  public void testGetPhotoByIdKo() throws BizException {
    assertNull(photoUcc.getPhotoById(7));
  }

  @DisplayName("get photo with existent ids")
  @Test
  public void testGetPhotoByIdOk() throws BizException {
    for (int i = 1; i <= 6; i++) {
      assertEquals(i % 2 == 0 ? "ok" : "introduit",
          photoUcc.getPhotoById(i).getIdQuote());
    }
  }
}
