package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.exceptions.DalException;

import java.util.List;


public interface PhotoDao {

  /**
   * Insert a photo to the db.
   *
   * @param photoDto the photo to insert
   * @return the id of the photo inserted
   * @throws DalException if you have an error with the db
   */
  Integer insert(PhotoDto photoDto) throws DalException;

  /**
   * get a list of photos.
   *
   * @param idQquote the id of the quote
   * @param isBefore if it's true it means that that you will get a list of photos before the
   *                 development otherwise you will get the photos after the development
   * @return list of photos
   * @throws DalException if you had a problem with the db
   */
  List<PhotoDto> getPhotos(String idQquote, Boolean isBefore) throws DalException;

  /**
   * Get the list of all visible photos.
   *
   * @return the list of all visible photos
   * @throws DalException if an error occurred with the db
   */
  List<PhotoVisibleDto> getVisiblePhotos() throws DalException;

  /**
   * Get the list of all visible photos for a specific development type.
   *
   * @param typeId the development type id
   * @return the list of all visible photos for the typeId
   * @throws DalException if an error occurred with the db
   */
  List<PhotoVisibleDto> getVisiblePhotos(int typeId) throws DalException;

  /**
   * retrieve a photo from the db with the id.
   *
   * @param idPhoto the id of the photo to retrieve
   * @return the photo
   * @throws DalException if you have an error with the db
   */
  PhotoDto getPhotoById(int idPhoto) throws DalException;
}
