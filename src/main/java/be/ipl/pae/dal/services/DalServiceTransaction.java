package be.ipl.pae.dal.services;

import be.ipl.pae.exceptions.FatalException;

public interface DalServiceTransaction {

  /**
   * @throws FatalException
   * 
   */
  void startTransaction() throws FatalException;

  /**
   * @throws FatalException
   * 
   */
  void commitTransaction() throws FatalException;

  /**
   * @throws FatalException
   * 
   */
  void rollbackTransaction() throws FatalException;
}
