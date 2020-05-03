package be.ipl.pae.biz.objets;


import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class QuoteImpl implements Quote {

  private String idQuote;
  private int idCustomer;
  private LocalDate quoteDate;
  private double totalAmount;
  private int workDuration;
  private QuoteState state;
  private LocalDate startDate;
  private List<PhotoDto> listPhotoBefore = new ArrayList<>();
  private List<PhotoDto> listPhotoAfter = new ArrayList<>();
  private CustomerDto customer;
  private List<DevelopmentTypeDto> developmentType = new ArrayList<>();
  private PhotoDto photo;



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
   */
  public QuoteImpl(String iqQuote, int idCustomer, LocalDate quoteDate, double totalAmount,
      int workDuration, QuoteState state, LocalDate startDate, CustomerDto customer,
      PhotoDto photo) {
    super();
    this.idQuote = iqQuote;
    this.idCustomer = idCustomer;
    this.quoteDate = quoteDate;
    this.totalAmount = totalAmount;
    this.workDuration = workDuration;
    this.state = state;
    this.startDate = startDate;
    this.customer = customer;
    this.photo = photo;
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


  public List<DevelopmentTypeDto> getDevelopmentTypes() {
    return developmentType;
  }


  public void setDevelopmentType(List<DevelopmentTypeDto> developmentType) {
    this.developmentType = developmentType;
  }


  public void addDevelopmentType(DevelopmentTypeDto developmentType) {
    this.developmentType.add(developmentType);
  }

  public void setListPhotoBefore(List<PhotoDto> listPhotoBefore) {
    this.listPhotoBefore = listPhotoBefore;
  }


  public void setListPhotoAfter(List<PhotoDto> listPhotoAfter) {
    this.listPhotoAfter = listPhotoAfter;
  }

  public PhotoDto getPhoto() {
    return photo;
  }

  @Override
  public boolean checkStateQuote(QuoteDto quote, QuoteState state) {
    if (quote.getState() == QuoteState.CANCELLED) {
      return true;
    }
    return quote.getState() == state;
  }



  public void setPhoto(PhotoDto photo) {
    this.photo = photo;
  }

}
