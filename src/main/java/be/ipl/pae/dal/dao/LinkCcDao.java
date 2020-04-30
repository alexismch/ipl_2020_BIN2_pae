package be.ipl.pae.dal.dao;

import be.ipl.pae.exceptions.DalException;

public interface LinkCcDao {

  /**
   * Link a customer to a client into the db.
   *
   * @param customerId the customer id
   * @param userId     the user id
   * @throws DalException if you have an error with the db
   */
  void link(int customerId, int userId) throws DalException;
}
