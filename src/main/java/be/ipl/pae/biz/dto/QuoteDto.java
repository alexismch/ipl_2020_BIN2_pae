package be.ipl.pae.biz.dto;


import be.ipl.pae.biz.objets.QuoteState;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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


  QuoteState getState();


  void setState(QuoteState state);


  void setState(String state);


  LocalDate getStartDate();


  void setStartDate(LocalDate startDate);


  List<PhotoDto> getListPhotoBefore();


  void addToListPhotoBefore(PhotoDto photoDto);


  List<PhotoDto> getListPhotoAfter();


  void addToListPhotoAfter(PhotoDto photoDto);


  CustomerDto getCustomer();


  void setCustomer(CustomerDto customer);


  List<DevelopmentTypeDto> getDevelopmentTypes();

  /**
   * Add a development type to the list.
   *
   * @param developmentType the type to add
   */
  void addDevelopmentType(DevelopmentTypeDto developmentType);

  void setListPhotoBefore(List<PhotoDto> listPhotoBefore);

  void setListPhotoAfter(List<PhotoDto> listPhotoAfter);


  /**
   * Add a development type to the set.
   * 
   * @param developmentTypes the type to add
   */
  void addDevelopmentTypesSet(DevelopmentTypeDto developmentTypes);

  Set<DevelopmentTypeDto> getDevelopmentTypesSet();


}
