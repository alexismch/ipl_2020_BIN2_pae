package be.ipl.pae.biz.dto;

import java.time.LocalDate;

public interface QuoteDto {

  public String getIdQuote();


  public void setIdQuote(String idQuote) ;


  public String getIdCustomer();


  public void setIdCustomer(String idCustomer) ;


  public LocalDate getQuoteDate();


  public void setQuoteDate(LocalDate quoteDate);


  public double getTotalAmount() ;


  public void setTotalAmount(double totalAmount) ;


  public int getWorkDuration();


  public void setWorkDuration(int workDuration) ;


  public String getIdState() ;


  public void setIdState(String idState) ;


  public LocalDate getStartDate() ;


  public void setStartDate(LocalDate startDate) ;
}
