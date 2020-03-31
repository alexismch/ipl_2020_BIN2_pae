package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;


public interface PhotoDao {

  /**
   * ???.
   *
   * @return ???
   */
  PhotoDto getPhotoPerDevelopmentType();

  /**
   * Insert a photo to the db.
   *
   * @param photoDto the photo to insert
   * @throws FatalException if you have an error with the db
   */
  void insert(PhotoDto photoDto) throws FatalException;

  /**
   * get a list of photos.
   *
   * @param idQquote the id of the quote
   * @param isBefore if it's true it means that that you will get a list of photos before the
   *                 development otherwise you will get the photos after the development
   * @return list of photos
   * @throws FatalException if you had a problem with the db
   */
  List<PhotoDto> getPhotos(String idQquote, Boolean isBefore) throws FatalException;

  /**
   * Get the list of all visible photos.
   *
   * @return the list of all visible photos
   * @throws FatalException if an error occurred with the db
   */
  List<PhotoVisibleDto> getVisiblePhotos() throws FatalException;

  /**
   * Get the list of all visible photos for a specific development type.
   *
   * @param typeId the development type id
   * @return the list of all visible photos for the typeId
   * @throws FatalException if an error occurred with the db
   */
  List<PhotoVisibleDto> getVisiblePhotos(int typeId) throws FatalException;
}
