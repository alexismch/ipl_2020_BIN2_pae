package be.ipl.pae.dal.services;

public interface DalServiceTransaction {

  /**
   * 
   */
  void startTransaction();

  /**
   * 
   */
  void commitTransaction();

  /**
   * 
   */
  void rollbackTransaction();
}
