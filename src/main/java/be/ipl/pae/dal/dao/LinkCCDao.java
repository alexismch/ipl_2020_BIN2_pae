package be.ipl.pae.dal.dao;

import be.ipl.pae.exceptions.FatalException;

public interface LinkCCDao {

  /**
   * Link a customer to a client into the db.
   *
   * @param customerId the customer id
   * @param userId     the user id
   * @throws FatalException if you have an error with the db
   */
  void link(int customerId, int userId) throws FatalException;
}
