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
   *                           applied
   * @return a list of all customers
   */
  List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto);

  CustomerDto insertCustomer(CustomerDto customerDto) throws FatalException;
}
