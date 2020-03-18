package be.ipl.pae.biz.dto;

import java.time.LocalDate;
import java.util.Date;

public interface QuoteDto {

  public String getIdQuote();


  public void setIdQuote(String idQuote) ;


  public String getIdCustomer();


  public void setIdCustomer(String idCustomer) ;


  public Date getQuoteDate();


  public void setQuoteDate(Date quoteDate);


  public double getTotalAmount() ;


  public void setTotalAmount(double totalAmount) ;


  public int getWorkDuration();


  public void setWorkDuration(int workDuration) ;


  public String getIdState() ;


  public void setIdState(String idState) ;


  public Date getStartDate() ;


  public void setStartDate(Date startDate) ;
}
