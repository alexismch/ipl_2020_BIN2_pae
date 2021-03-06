package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.dal.dao.CustomerDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

class CustomerUccImpl implements CustomerUcc {

  @Injected
  private CustomerDao customerDao;

  @Injected
  private DalServiceTransaction dalService;

  @Override
  public CustomerDto insert(CustomerDto customerDto) {
    try {
      dalService.startTransaction();

      return customerDao.insertCustomer(customerDto);
    } catch (DalException fx) {
      dalService.rollbackTransaction();
      throw new FatalException(fx);
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) {
    List<CustomerDto> list;
    try {
      dalService.startTransaction();
      list = customerDao.getCustomers(customersFilterDto);
    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
    return list;
  }

  @Override
  public CustomerDto getCustomerByIdUser(int idUser) {
    CustomerDto customer;
    try {
      dalService.startTransaction();
      customer = customerDao.getCustomerByIdUser(idUser);

    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }

    return customer;
  }
}
