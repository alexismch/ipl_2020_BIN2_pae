package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuoteTypeDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.dto.DevelopmentTypeDto;

public class DtoFactoryImpl implements DtoFactory {

  @Override
  public UserDto getUser() {
    return new UserImpl();
  }

  @Override
  public UsersFilterDto getUsersFilterDto() {
    return new UsersFilter();
  }

  @Override
  public QuoteDto getQuote() {
    // TODO Auto-generated method stub
    return new QuoteImpl();
  }

  public QuoteTypeDto getQuoteType() {
    // TODO Auto-generated method stub
    return new QuoteTypeImpl();
  }

  @Override
  public PhotoDto getPhoto() {
    // TODO Auto-generated method stub
    return new PhotoImpl();
  }

  @Override
  public DevelopmentTypeDto getDevelopmentType() {
    // TODO Auto-generated method stub
    return new DevelopmentTypeImpl();
  }

  public CustomerDto getCustomer() {
    return new CustomerImpl();
  }

  @Override
  public CustomersFilterDto getCustomersFilter() {
    // TODO Auto-generated method stub
    return new CustomersFilterImpl();
  }
}
