package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;

import java.util.ArrayList;
import java.util.List;

public class MockPhotoDao implements PhotoDao {

  @Injected
  private DtoFactory dtoFactory;

  @Override
  public Integer insert(PhotoDto photoDto) {
    return 1;
  }

  @Override
  public List<PhotoDto> getPhotos(String idQquote, Boolean isBefore) {
    List<PhotoDto> listToReturn = new ArrayList<>();
    PhotoDto photoDto = dtoFactory.getPhoto();
    if (isBefore) {
      photoDto.setId(1);
    } else {
      photoDto.setId(2);
    }
    photoDto.setBeforeWork(isBefore);
    listToReturn.add(photoDto);
    return listToReturn;
  }

  @Override
  public List<PhotoVisibleDto> getVisiblePhotos() {
    List<PhotoVisibleDto> listToReturn = new ArrayList<>();

    PhotoVisibleDto photoDto1 = dtoFactory.getPhotoVisible();
    photoDto1.setId(1);
    listToReturn.add(photoDto1);

    PhotoVisibleDto photoDto2 = dtoFactory.getPhotoVisible();
    photoDto2.setId(2);
    listToReturn.add(photoDto2);

    PhotoVisibleDto photoDto3 = dtoFactory.getPhotoVisible();
    photoDto3.setId(3);
    listToReturn.add(photoDto3);

    PhotoVisibleDto photoDto4 = dtoFactory.getPhotoVisible();
    photoDto4.setId(4);
    listToReturn.add(photoDto4);

    PhotoVisibleDto photoDto5 = dtoFactory.getPhotoVisible();
    photoDto5.setId(5);
    listToReturn.add(photoDto5);

    return listToReturn;
  }

  @Override
  public List<PhotoVisibleDto> getVisiblePhotos(int typeId) {
    List<PhotoVisibleDto> listToReturn = null;

    if (typeId == 1) {
      listToReturn = new ArrayList<>();

      PhotoVisibleDto photoDto1 = dtoFactory.getPhotoVisible();
      photoDto1.setId(1);
      listToReturn.add(photoDto1);

      PhotoVisibleDto photoDto2 = dtoFactory.getPhotoVisible();
      photoDto2.setId(2);
      listToReturn.add(photoDto2);
    } else if (typeId == 2) {
      listToReturn = new ArrayList<>();

      PhotoVisibleDto photoDto1 = dtoFactory.getPhotoVisible();
      photoDto1.setId(3);
      listToReturn.add(photoDto1);

      PhotoVisibleDto photoDto2 = dtoFactory.getPhotoVisible();
      photoDto2.setId(4);
      listToReturn.add(photoDto2);

      PhotoVisibleDto photoDto3 = dtoFactory.getPhotoVisible();
      photoDto3.setId(5);
      listToReturn.add(photoDto3);
    }

    return listToReturn;
  }

  @Override
  public PhotoDto getPhotoById(int idPhoto) {
    if (idPhoto >= 1 && idPhoto <= 6) {
      PhotoDto photoDto = dtoFactory.getPhoto();
      photoDto.setId(idPhoto);
      photoDto.setIdQuote(idPhoto % 2 == 0 ? "ok" : "introduit");
      return photoDto;
    }
    return null;
  }
}
