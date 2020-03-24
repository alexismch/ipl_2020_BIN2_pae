package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.exceptions.FatalException;


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
}
