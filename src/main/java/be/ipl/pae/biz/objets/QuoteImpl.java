package be.ipl.pae.biz.objets;


import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuoteImpl implements Quote {

  private String idQuote;
  private int idCustomer;
  private LocalDate quoteDate;
  private BigDecimal totalAmount;
  private int workDuration;
  private QuoteState state;
  private LocalDate startDate;
  private List<PhotoDto> listPhotoBefore = new ArrayList<PhotoDto>();
  private List<PhotoDto> listPhotoAfter = new ArrayList<PhotoDto>();
  private CustomerDto customer;
  private QuoteState quoteState;
  private List<DevelopmentTypeDto> listDevelopmentType = new ArrayList<DevelopmentTypeDto>();

  /**
   * Create a QuoteImpl object.
   *
   * @param iqQuote the id of the quote
   * @param idCustomer the id of the customer that asked for the quote
   * @param quoteDate the date when the quote was created
   * @param totalAmount the total amount of the quote
   * @param workDuration the work duration
   * @param state the state of the quote
   * @param startDate the start date of work
   * @param customer the customer that has asked a quote
   * @param quoteState the state of the quote
   */
  public QuoteImpl(String iqQuote, int idCustomer, LocalDate quoteDate, BigDecimal totalAmount,
      int workDuration, QuoteState state, LocalDate startDate, CustomerDto customer,
      QuoteState quoteState) {
    super();
    this.idQuote = iqQuote;
    this.idCustomer = idCustomer;
    this.quoteDate = quoteDate;
    this.totalAmount = totalAmount;
    this.workDuration = workDuration;
    this.state = state;
    this.startDate = startDate;
    this.customer = customer;
    this.quoteState = quoteState;
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


  public LocalDate getQuoteDate() {
    return quoteDate;
  }


  public void setQuoteDate(LocalDate quoteDate) {
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


  public QuoteState getState() {
    return state;
  }


  public void setState(QuoteState state) {
    this.state = state;
  }


  public void setState(String state) {
    this.state = QuoteState.valueOf(state);
  }


  public LocalDate getStartDate() {
    return startDate;
  }


  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }


  public List<PhotoDto> getListPhotoBefore() {
    return listPhotoBefore;
  }


  public void addToListPhotoBefore(PhotoDto photoDto) {
    this.listPhotoBefore.add(photoDto);
  }


  public List<PhotoDto> getListPhotoAfter() {
    return listPhotoAfter;
  }


  public void addToListPhotoAfter(PhotoDto photoDto) {
    this.listPhotoAfter.add(photoDto);
  }


  public CustomerDto getCustomer() {
    return customer;
  }


  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }


  public QuoteState getQuoteState() {
    return quoteState;
  }


  public void setQuoteState(QuoteState quoteState) {
    this.quoteState = quoteState;
  }


  public List<DevelopmentTypeDto> getListDevelopmentType() {
    return listDevelopmentType;
  }


  public void addListDevelopmentType(DevelopmentTypeDto developmentType) {
    this.listDevelopmentType.add(developmentType);
  }



}
