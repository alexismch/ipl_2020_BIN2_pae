package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.dal.dao.CustomerDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class CustomerUccImpl implements CustomerUcc {

  @Injected
  private CustomerDao customerDao;

  @Injected
  private DalServiceTransaction dalService;

  @Override
  public CustomerDto insert(CustomerDto customerDto) throws BizException {
    try {
      try {

        dalService.startTransaction();
        return customerDao.insertCustomer(customerDto);

      } catch (DalException fx) {
        dalService.rollbackTransaction();
        throw new BizException(fx);
      } finally {
        dalService.commitTransaction();
      }
    } catch (DalException ex) {
      throw new BizException(ex);
    }
  }

  @Override
  public List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) {
    try {
      dalService.startTransaction();
      return customerDao.getCustomers(customersFilterDto);

    } catch (DalException ex) {
      dalService.rollbackTransaction();
      throw new FatalException(ex);
    } finally {
      dalService.commitTransaction();
    }
  }

  @Override
  public CustomerDto getCustomerByIdUser(int idUser) {
    try {

      dalService.startTransaction();
      return customerDao.getCustomerByIdUser(idUser);

    } catch (DalException ex) {

      dalService.rollbackTransaction();

      throw new DalException(ex);
    } finally {

      dalService.commitTransaction();
    }
  }
}
