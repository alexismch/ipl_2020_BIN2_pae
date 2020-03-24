package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;


public interface DevelopmentTypeDao {

  List<DevelopmentTypeDto> getAllDevelopmentType();

  /**
   * Get the development type with his id.
   *
   * @param typeId the development type id
   * @return An DevelopmentTypeDto object with all the informations that the db gave you or null
   * @throws FatalException if an error occurred with the db.
   */
  DevelopmentTypeDto getDevelopmentType(int typeId) throws FatalException;
}
