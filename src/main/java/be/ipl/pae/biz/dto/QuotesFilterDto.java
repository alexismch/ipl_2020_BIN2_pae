package be.ipl.pae.biz.dto;

import be.ipl.pae.biz.objets.DevelopmentType;

import java.time.LocalDate;
import java.util.ArrayList;

public interface QuotesFilterDto {

  String getCustomerName();

  void setCustomerName(String customerName);

  LocalDate getQuoteDate();

  void setQuoteDate(LocalDate quoteDate);

  int getTotalAmountMin();

  void setTotalAmountMin(int totalAmountMin);

  int getTotalAmountMax();

  void setTotalAmountMax(int totalAmountMax);

  ArrayList<DevelopmentType> getDevelopmentType();

  void setDevelopmentType(ArrayList<DevelopmentType> developmentType);

}
