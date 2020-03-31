package be.ipl.pae.biz.dto;

import be.ipl.pae.biz.objets.DevelopmentType;

import java.time.LocalDate;

public interface QuotesFilterDto {

  String getCustomerName();

  void setCustomerName(String customerName);

  LocalDate getQuoteDate();

  void setQuoteDate(LocalDate quoteDate);

  int getTotalAmountMin();

  void setTotalAmountMin(int totalAmountMin);

  int getTotalAmountMax();

  void setTotalAmountMax(int totalAmountMax);

  DevelopmentType getDevelopmentType();

  void setDevelopmentType(DevelopmentType developmentType);

}
