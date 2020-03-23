package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.exceptions.FatalException;

public interface CustomerDao {

  /**
   * insert a new Customer in the database.
   *
   * @param customer the user that you need to insert
   * @return a CustomerDto if he is insert or null if he's not
   * @throws FatalException if you have an error with the db
   */
  public CustomerDto insertCustomer(CustomerDto customer) throws FatalException;
}
