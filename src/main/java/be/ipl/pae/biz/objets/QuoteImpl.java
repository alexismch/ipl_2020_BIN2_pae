package be.ipl.pae.biz.objets;


import java.math.BigDecimal;
import java.sql.Date;

public class QuoteImpl implements Quote {

  String idQuote;
  int idCustomer;
  Date quoteDate;
  BigDecimal totalAmount;
  int workDuration;
  State state;
  Date startDate;


  public QuoteImpl(String idquote, int idcustomer, Date quotedate, BigDecimal totalamount,
      int workduration, State state, Date startdate) {
    super();
    this.idQuote = idquote;
    this.idCustomer = idcustomer;
    this.quoteDate = quotedate;
    this.totalAmount = totalamount;
    this.workDuration = workduration;
    this.state = state;
    this.startDate = startdate;
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


  public int getIdCustomer() {
    return idCustomer;
  }


  public void setIdCustomer(int idCustomer) {
    this.idCustomer = idCustomer;
  }


  public Date getQuoteDate() {
    return quoteDate;
  }


  public void setQuoteDate(Date quoteDate) {
    this.quoteDate = quoteDate;
  }


  public BigDecimal getTotalAmount() {
    return totalAmount;
  }


  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }


  public int getWorkDuration() {
    return workDuration;
  }


  public void setWorkDuration(int workDuration) {
    this.workDuration = workDuration;
  }


  public State getState() {
    return state;
  }


  public void setState(State state) {
    this.state = state;
  }


  public void setState(String state) {
    this.state = State.valueOf(state);
  }


  public Date getStartDate() {
    return startDate;
  }


  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }



  
  
}
