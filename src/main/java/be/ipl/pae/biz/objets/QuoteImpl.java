package be.ipl.pae.biz.objets;


import java.util.Date;

public class QuoteImpl implements Quote {

  String idQuote;
  String idCustomer;
  Date quoteDate;
  double totalAmount;
  int workDuration;
  String idState;
  Date startDate;
  
  
  public QuoteImpl(String idquote, String idcustomer, Date quotedate, double totalamount,
      int workduration, String idstate, Date startdate) {
    super();
    this.idQuote = idquote;
    this.idCustomer = idcustomer;
    this.quoteDate = quotedate;
    this.totalAmount = totalamount;
    this.workDuration = workduration;
    this.idState = idstate;
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
