package be.ipl.pae.biz.objets;

import java.time.LocalDate;
import java.util.Date;

public class QuoteImpl implements Quote {

  String idQuote;
  String idCustomer;
  Date quoteDate;
  double totalAmount;
  int workDuration;
  String idState;
  Date startDate;
  
  
  public QuoteImpl(String id_quote, String id_customer, Date quote_date, double total_amount,
      int work_duration, String id_state, Date start_date) {
    super();
    this.idQuote = id_quote;
    this.idCustomer = id_customer;
    this.quoteDate = quote_date;
    this.totalAmount = total_amount;
    this.workDuration = work_duration;
    this.idState = id_state;
    this.startDate = start_date;
  }


  public QuoteImpl() {
    super();
  }


  public String getIdQuote() {
    return idQuote;
  }


  public void setIdQuote(String idQuote) {
    this.idQuote = idQuote;
  }


  public String getIdCustomer() {
    return idCustomer;
  }


  public void setIdCustomer(String idCustomer) {
    this.idCustomer = idCustomer;
  }


  public Date getQuoteDate() {
    return quoteDate;
  }


  public void setQuoteDate(Date quoteDate) {
    this.quoteDate = quoteDate;
  }


  public double getTotalAmount() {
    return totalAmount;
  }


  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }


  public int getWorkDuration() {
    return workDuration;
  }


  public void setWorkDuration(int workDuration) {
    this.workDuration = workDuration;
  }


  public String getIdState() {
    return idState;
  }


  public void setIdState(String idState) {
    this.idState = idState;
  }


  public Date getStartDate() {
    return startDate;
  }


  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }



  
  
}
