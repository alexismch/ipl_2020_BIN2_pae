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

  /**
   * Create a QuoteImpl object.
   *
   * @param iqQuote      the id of the quote
   * @param idCustomer   the id of the customer that asked for the quote
   * @param quoteDate    the date when the quote was created
   * @param totalAmount  the total amount of the quote
   * @param workDuration the work duration
   * @param state        the state of the quote
   * @param startDate    the start date of work
   */
  public QuoteImpl(String iqQuote, int idCustomer, Date quoteDate, BigDecimal totalAmount,
      int workDuration, State state, Date startDate) {
    super();
    this.idQuote = iqQuote;
    this.idCustomer = idCustomer;
    this.quoteDate = quoteDate;
    this.totalAmount = totalAmount;
    this.workDuration = workDuration;
    this.state = state;
    this.startDate = startDate;
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
