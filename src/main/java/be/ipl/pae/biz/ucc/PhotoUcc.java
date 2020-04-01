package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public interface PhotoUcc {

  /**
   * Get the list of all visible photos.
   *
   * @return the list of all visible photos
   * @throws BizException   if an error occured with the db
   * @throws FatalException if an error occurred with transaction
   */
  List<PhotoVisibleDto> getVisiblePhotos() throws FatalException, BizException;

  /**
   * Get the list of all visible photos for a specific development type.
   *
   * @param typeId the development type id
   * @return the list of all visible photos for the typeId
   * @throws BizException   if an error occured with the db
   * @throws FatalException if an error occurred with transaction
   */
  List<PhotoVisibleDto> getVisiblePhotos(int typeId) throws BizException, FatalException;
}
