package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public interface CustomerDao {

  /**
   * Get all the customers saved in the database according to the chosen filters.
   *
   * @param customersFilterDto a filter applied to the results or null if no filter should be
   *        applied
   * @return a list of all customers
   */
  List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto);

  /**
   * Verify if the customer exists in the db.
   *
   * @param customerId the customer id to check
   * @return true if it exists, false if not
   * @throws FatalException if you have an error with the db
   */
  boolean exists(int customerId) throws FatalException;

  CustomerDto insertCustomer(CustomerDto customerDto) throws FatalException;

  /**
   * Verify if the customer is linked to a user.
   *
   * @param customerId the customer id
   * @return true if the customer is linked to a user, false if not
   * @throws FatalException if you have an error with the db
   */
  boolean isLinked(int customerId) throws FatalException;

  /**
   * get a customer thanks to his id.
   * 
   * @param idCustomer
   * @return an customeDto object
   * @throws FatalException if you had a problem with the db
   */
  CustomerDto getCustomer(int idCustomer) throws FatalException;
}
