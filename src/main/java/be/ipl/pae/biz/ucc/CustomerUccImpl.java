package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.dal.dao.CustomerDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.ihm.Util;

import java.util.List;

public class CustomerUccImpl implements CustomerUcc {

  @Injected
  private CustomerDao customerDao;

  @Injected
  private DalServiceTransaction dalService;

  @Override
  public CustomerDto insert(CustomerDto customerDto) throws BizException {
    CustomerDto customer = null;
    try {
      if (Util.verifyNotEmpty(customerDto.getFirstName())
          || Util.verifyNotEmpty(customerDto.getLastName())
          || Util.verifyNotEmpty(customerDto.getAddress())
          || Util.verifyNotEmpty(customerDto.getEmail())
          || Util.verifyNotEmpty(customerDto.getCity())
          || Util.verifyNotEmpty(customerDto.getPhoneNumber())) {
        throw new BizException("echec insertion: un ou plusieurs champ(s) invalide");
      }

      dalService.startTransaction();
      customer = customerDao.insertCustomer(customerDto);

    } catch (DalException fx) {
      dalService.rollbackTransaction();
      throw new FatalException(fx);
    } finally {
      dalService.commitTransaction();
    }

    return customer;
  }

  @Override
  public List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) {
    List<CustomerDto> list = null;
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
    CustomerDto customer = null;
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
