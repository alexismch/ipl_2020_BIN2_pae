package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;


public interface DevelopmentTypeDao {

  /**
   * Get the entire list of development types.
   *
   * @return the list of development types.
   */
  List<DevelopmentTypeDto> getdevelopmentTypes();

  /**
   * Get the development type with his id.
   *
   * @param typeId the development type id
   * @return An DevelopmentTypeDto object with all the informations that the db gave you or null
   * @throws FatalException if an error occurred with the db.
   */
  DevelopmentTypeDto getDevelopmentType(int typeId) throws FatalException;

  /**
   * Insert a new development type into the database.
   *
   * @param developmentType the development type to insert
   * @return a DevelopmentTypeDto object that represents the development type
   * @throws FatalException if an error occurred with the db.
   */
  DevelopmentTypeDto insert(DevelopmentTypeDto developmentType) throws FatalException;

  /**
   * Get a list of all the development type of a quote.
   *
   * @param quoteId the quote of the id
   * @return a list of DevelopmentTypeDto
   * @throws FatalException if you had a problem with the db
   */
  List<DevelopmentTypeDto> getDevelopmentTypeList(String quoteId) throws FatalException;

  /**
   * Verify if a development type exists with a specific title.
   *
   * @param title the title
   * @return true if exists, true if not
   */
  boolean exists(String title) throws FatalException;
}
