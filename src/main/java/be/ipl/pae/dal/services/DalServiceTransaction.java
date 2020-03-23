package be.ipl.pae.dal.services;

import be.ipl.pae.exceptions.FatalException;

public interface DalServiceTransaction {

  /**
   * Start a new transaction.
   * 
   * @throws FatalException if you have a problem with the db
   * 
   */
  void startTransaction() throws FatalException;

  /**
   * Commits the current transaction, making its changes permanent.
   * 
   * @throws FatalException if you have a problem with the db
   * 
   */
  void commitTransaction() throws FatalException;

  /**
   * Rolls back the current transaction, canceling its changes.
   * 
   * @throws FatalException if you have a problem with the db
   * 
   */
  void rollbackTransaction() throws FatalException;
}
