package be.ipl.pae.biz.dto;


import be.ipl.pae.biz.objets.State;

import java.util.Date;

public interface QuoteDto {

  String getIdQuote();


  void setIdQuote(String idQuote);


  int getIdCustomer();


  void setIdCustomer(int idCustomer);


  Date getQuoteDate();


  void setQuoteDate(Date quoteDate);


  double getTotalAmount();


  void setTotalAmount(double totalAmount);


  int getWorkDuration();


  void setWorkDuration(int workDuration);


  State getState();


  void setState(State state);


  void setState(String state);


  Date getStartDate();


  void setStartDate(Date startDate);
}
