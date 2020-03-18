package be.ipl.pae.biz.objets;

import java.time.LocalDate;

public class QuoteImpl implements Quote {

  String id_quote;
  String id_customer;
  LocalDate quote_date;
  double total_amount;
  int work_duration;
  String id_state;
  LocalDate start_date;
  
  
  public QuoteImpl(String id_quote, String id_customer, LocalDate quote_date, double total_amount,
      int work_duration, String id_state, LocalDate start_date) {
    super();
    this.id_quote = id_quote;
    this.id_customer = id_customer;
    this.quote_date = quote_date;
    this.total_amount = total_amount;
    this.work_duration = work_duration;
    this.id_state = id_state;
    this.start_date = start_date;
  }


  public QuoteImpl() {
    super();
  }




  public String getId_quote() {
    return id_quote;
  }


  public void setId_quote(String id_quote) {
    this.id_quote = id_quote;
  }


  public String getId_customer() {
    return id_customer;
  }


  public void setId_customer(String id_customer) {
    this.id_customer = id_customer;
  }


  public LocalDate getQuote_date() {
    return quote_date;
  }


  public void setQuote_date(LocalDate quote_date) {
    this.quote_date = quote_date;
  }


  public double getTotal_amount() {
    return total_amount;
  }


  public void setTotal_amount(double total_amount) {
    this.total_amount = total_amount;
  }


  public int getWork_duration() {
    return work_duration;
  }


  public void setWork_duration(int work_duration) {
    this.work_duration = work_duration;
  }


  public String getId_state() {
    return id_state;
  }


  public void setId_state(String id_state) {
    this.id_state = id_state;
  }


  public LocalDate getStart_date() {
    return start_date;
  }


  public void setStart_date(LocalDate start_date) {
    this.start_date = start_date;
  }
  
  
  
  
  
}
