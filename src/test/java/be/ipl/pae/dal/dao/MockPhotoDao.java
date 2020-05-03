package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.DalException;

import java.util.ArrayList;
import java.util.List;

public class MockPhotoDao implements PhotoDao {

  @Injected
  private DtoFactory dtoFactory;

  @Override
  public void insert(PhotoDto photoDto) {

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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<PhotoVisibleDto> getVisiblePhotos(int typeId) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public PhotoDto getPhotoById(int idPhoto) throws DalException {
    // TODO Auto-generated method stub
    return null;
  }

}
