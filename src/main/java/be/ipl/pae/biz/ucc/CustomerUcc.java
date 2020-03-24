package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public interface CustomerUcc {

  /**
   * Insert a new customer into the database.
   *
   * @param customerDto the cusyomer to insert
   * @return a CustomerDto object that represents the customer
   */
  CustomerDto insert(CustomerDto customerDto) throws FatalException, BizException;

  /**
   * Get all customers saved in the database
   * 
   * @param customersFilterDto a filter applied to the results or null if no filter should be
   *        applied
   * @return A list of all customers
   * @throws FatalException Thrown if a fatal error happened during the data retrieving
   */
  List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) throws FatalException;

}
