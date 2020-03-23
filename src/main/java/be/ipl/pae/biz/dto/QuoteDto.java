package be.ipl.pae.biz.dto;


import be.ipl.pae.biz.objets.StateQuote;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface QuoteDto {

  String getIdQuote();


  void setIdQuote(String idQuote);


  int getIdCustomer();


  void setIdCustomer(int idCustomer);


  LocalDate getQuoteDate();


  void setQuoteDate(LocalDate quoteDate);


  BigDecimal getTotalAmount();


  void setTotalAmount(BigDecimal totalAmount);


  int getWorkDuration();


  void setWorkDuration(int workDuration);


  StateQuote getState();


  void setState(StateQuote state);


  void setState(String state);


  LocalDate getStartDate();


  void setStartDate(LocalDate startDate);


  List<PhotoDto> getListPhotoBefore();


  void addToListPhotoBefore(PhotoDto photoDto);


  List<PhotoDto> getListPhotoAfter();


  void addToListPhotoAfter(PhotoDto photoDto);


  CustomerDto getCustomer();


  void setCustomer(CustomerDto customer);


  StateQuote getStateQuote();


  void setStateQuote(StateQuote stateQuote);


  List<DevelopmentTypeDto> getListDevelopmentType();


  void addListDevelopmentType(DevelopmentTypeDto developmentType);

}
