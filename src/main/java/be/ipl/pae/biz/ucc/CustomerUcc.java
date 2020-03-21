package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

public interface CustomerUcc {

  /**
   * Insert a new customer into the database.
   *
   * @param customerDto the cusyomer to insert
   * @return a CustomerDto object that represents the customer
   */
  CustomerDto insert(CustomerDto customerDto) throws FatalException, BizException;
}
