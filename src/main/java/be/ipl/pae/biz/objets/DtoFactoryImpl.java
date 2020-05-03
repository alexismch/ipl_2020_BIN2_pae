package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.PhotoVisibleDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;

class DtoFactoryImpl implements DtoFactory {

  @Override
  public UserDto getUser() {
    return new UserImpl();
  }

  @Override
  public UsersFilterDto getUsersFilterDto() {
    return new UsersFilterImpl();
  }

  @Override
  public QuoteDto getQuote() {
    return new QuoteImpl();
  }

  @Override
  public PhotoDto getPhoto() {
    return new PhotoImpl();
  }

  @Override
  public DevelopmentTypeDto getDevelopmentType() {
    return new DevelopmentTypeImpl();
  }

  public CustomerDto getCustomer() {
    return new CustomerImpl();
  }

  @Override
  public CustomersFilterDto getCustomersFilter() {
    return new CustomersFilterImpl();
  }

  @Override
  public PhotoVisibleDto getPhotoVisible() {
    return new PhotoVisibleImpl();
  }

  public QuotesFilterDto getQuotesFilter() {
    return new QuotesFilterImpl();
  }
}
