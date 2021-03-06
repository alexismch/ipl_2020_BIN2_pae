package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.exceptions.DalException;

import java.util.List;

public interface CustomerDao {

  /**
   * Get all the customers saved in the database according to the chosen filters.
   *
   * @param customersFilterDto a filter applied to the results or null if no filter should be
   *        applied
   * @return a list of all customers
   * @throws DalException if you have an error with the db
   */
  List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) throws DalException;

  /**
   * Verify if the customer exists in the db.
   *
   * @param customerId the customer id to check
   * @return true if it exists, false if not
   * @throws DalException if you have an error with the db
   */
  boolean exists(int customerId) throws DalException;

  /**
   * Insert a customer in the db.
   *
   * @param customerDto the customer to insert
   * @return the customer inserted
   * @throws DalException if an error occurred with the db
   */
  CustomerDto insertCustomer(CustomerDto customerDto) throws DalException;

  /**
   * Verify if the customer is linked to a user.
   *
   * @param customerId the customer id
   * @return true if the customer is linked to a user, false if not
   * @throws DalException if you have an error with the db
   */
  boolean isLinked(int customerId) throws DalException;

  /**
   * get a customer thanks to his id.
   *
   * @param idCustomer id of the customer
   * @return an customeDto object
   * @throws DalException if you had a problem with the db
   */
  CustomerDto getCustomer(int idCustomer) throws DalException;

  /**
   * Get a customer from his user id.
   *
   * @param idUser Id of the user used to get the customer
   * @return an CustomerDto object
   * @throws DalException If error with the database.
   */
  CustomerDto getCustomerByIdUser(int idUser) throws DalException;
}
