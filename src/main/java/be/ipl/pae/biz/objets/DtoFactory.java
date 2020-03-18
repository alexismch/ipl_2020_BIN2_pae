package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.dto.UsersFilterDto;

public interface DtoFactory {

  PhotoDto getPhoto();
  
  UserDto getUser();
  
  QuoteDto getQuote();

  UsersFilterDto getUsersFilterDto();

}
