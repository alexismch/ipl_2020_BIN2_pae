package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class MockCustomerDao implements CustomerDao {

  @Injected
  private DtoFactory dtoFactory;

  @Override
  public List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean exists(int customerId) throws FatalException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public CustomerDto insertCustomer(CustomerDto customerDto) throws FatalException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isLinked(int customerId) throws FatalException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public CustomerDto getCustomer(int idCustomer) throws FatalException {
    CustomerDto customerDto = dtoFactory.getCustomer();
    customerDto.setIdUser(idCustomer);
    return customerDto;
  }

  @Override
  public CustomerDto getCustomerByIdUser(int idUser) throws FatalException {
    // TODO Auto-generated method stub
    return null;
  }

}
