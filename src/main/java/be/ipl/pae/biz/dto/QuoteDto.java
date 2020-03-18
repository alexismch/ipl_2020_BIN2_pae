package be.ipl.pae.biz.dto;

import java.time.LocalDate;

public interface QuoteDto {

  
  public String getId_quote();


  public void setId_quote(String id_quote) ;


  public String getId_customer() ;

  public void setId_customer(String id_customer) ;


  public LocalDate getQuote_date() ;


  public void setQuote_date(LocalDate quote_date);


  public double getTotal_amount() ;


  public void setTotal_amount(double total_amount) ;


  public int getWork_duration() ;


  public void setWork_duration(int work_duration) ;


  public String getId_state() ;


  public void setId_state(String id_state) ;


  public LocalDate getStart_date() ;


  public void setStart_date(LocalDate start_date);
}
