package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.exceptions.BizException;

import java.util.List;

public interface PhotoUcc {

  /**
   * Get the list of all visible photos.
   *
   * @return the list of all visible photos
   * @throws BizException if an error occured with the db
   */
  List<PhotoVisibleDto> getVisiblePhotos() throws BizException;

  /**
   * Get the list of all visible photos for a specific development type.
   *
   * @param typeId the development type id
   * @return the list of all visible photos for the typeId
   * @throws BizException if an error occured with the db
   */
  List<PhotoVisibleDto> getVisiblePhotos(int typeId) throws BizException;

  /**
   * Insert photos into the db.
   *
   * @param photoDtos the photos to insert
   * @return the list of ids of inserted photos
   * @throws BizException if an error occured with the db
   */
  List<Integer> insert(List<PhotoDto> photoDtos) throws BizException;

  /**
   * Get a photo from the db with the id.
   *
   * @param id the photo id
   * @return the photo retrieve with the id
   * @throws BizException if an error occured with the db
   */
  PhotoDto getPhotoById(int id) throws BizException;
}
