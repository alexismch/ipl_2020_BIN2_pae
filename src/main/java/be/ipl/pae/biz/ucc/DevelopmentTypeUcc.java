package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;


public interface DevelopmentTypeUcc {

  /**
   * Get all the development types.
   *
   * @return a list of DevelopmentTypeDto
   * @throws BizException * @throws BizException if an error occured
   */
  List<DevelopmentTypeDto> getDevelopmentTypes() throws BizException;

  /**
   * Get the development type with his id.
   *
   * @param typeId the development type id
   * @return An DevelopmentTypeDto object with all the informations that the db gave you or null
   * @throws BizException if an error occured
   */
  DevelopmentTypeDto getDevelopmentType(int typeId) throws BizException, FatalException;

  /**
   * Insert a new development type into the database.
   *
   * @param developmentType the development type to insert
   * @return a DevelopmentTypeDto object that represents the development type
   * @throws BizException if an error occurred with the db
   * @throws FatalException if an error occurred with transaction
   */
  DevelopmentTypeDto insert(DevelopmentTypeDto developmentType) throws BizException, FatalException;
}
