package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

  @Injected
  DalService dalService;
  @Injected
  DtoFactory customerDto;
  @Override
  
  
  public List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) {
    // TODO Auto-generated method stub
    
    String query;
    
    if(customersFilterDto==null) {
      query="SELECT * FROM mystherbe.customers";
    }else {
      query ="SELECT * FROM mystherbe.customers WHERE (? IS NULL OR lastname LIKE ?)"
          + " AND (? IS NULL OR city LIKE ?)";
    }
    
    return null;
  }
}
