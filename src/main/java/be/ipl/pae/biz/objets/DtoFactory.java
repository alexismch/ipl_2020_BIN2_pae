package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;

public interface DtoFactory {

  DevelopmentTypeDto getDevelopmentType();

  PhotoDto getPhoto();

  UserDto getUser();

  QuoteDto getQuote();

  CustomersFilterDto getCustomersFilter();

  QuotesFilterDto getQuotesFilter();

  UsersFilterDto getUsersFilterDto();

  CustomerDto getCustomer();

}
