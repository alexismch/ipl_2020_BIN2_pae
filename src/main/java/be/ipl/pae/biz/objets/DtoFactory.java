package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuoteTypeDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;

public interface DtoFactory {

  DevelopmentType getDevelopmentType();

  PhotoDto getPhoto();

  UserDto getUser();

  QuoteDto getQuote();

  UsersFilterDto getUsersFilterDto();

  QuoteTypeDto getQuoteType();

  CustomerDto getCustomer();

}
