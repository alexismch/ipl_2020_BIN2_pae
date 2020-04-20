package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dependencies.Injected;

import java.util.ArrayList;
import java.util.List;

public class MockCustomerDao implements CustomerDao {

  @Injected
  private DtoFactory dtoFactory;

  @Override
  public List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) {
    List<CustomerDto> list = new ArrayList<>();

    CustomerDto customerDto = dtoFactory.getCustomer();
    customerDto.setIdCustomer(1);
    customerDto.setIdUser(2);
    list.add(customerDto);

    customerDto = dtoFactory.getCustomer();
    customerDto.setIdCustomer(2);
    list.add(customerDto);

    return list;
  }

  @Override
  public boolean exists(int customerId) {
    return customerId == 1 || customerId == 2;
  }

  @Override
  public CustomerDto insertCustomer(CustomerDto customerDto) {
    customerDto.setIdCustomer(3);
    return customerDto;
  }

  @Override
  public boolean isLinked(int customerId) {
    return customerId == 1;
  }

  @Override
  public CustomerDto getCustomer(int idCustomer) {
    CustomerDto customerDto = dtoFactory.getCustomer();
    customerDto.setIdCustomer(idCustomer);
    return customerDto;
  }

  @Override
  public CustomerDto getCustomerByIdUser(int idUser) {
    if (idUser != 2) {
      return null;
    }
    CustomerDto customerDto = dtoFactory.getCustomer();
    customerDto.setIdUser(idUser);
    customerDto.setIdCustomer(1);
    return customerDto;
  }

}
