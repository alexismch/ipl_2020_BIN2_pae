package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;

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

  public QuoteImpl() {
    super();
  }

  @Override
  public String getIdQuote() {
    return idQuote;
  }

  @Override
  public void setIdQuote(String idQuote) {
    this.idQuote = idQuote;
  }

  @Override
  public int getIdCustomer() {
    return idCustomer;
  }

  @Override
  public void setIdCustomer(int idCustomer) {
    this.idCustomer = idCustomer;
  }

  @Override
  public LocalDate getQuoteDate() {
    return quoteDate;
  }

  @Override
  public void setQuoteDate(LocalDate quoteDate) {
    this.quoteDate = quoteDate;
  }

  @Override
  public double getTotalAmount() {
    return totalAmount;
  }

  @Override
  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  @Override
  public int getWorkDuration() {
    return workDuration;
  }

  @Override
  public void setWorkDuration(int workDuration) {
    this.workDuration = workDuration;
  }

  @Override
  public QuoteState getState() {
    return state;
  }

  @Override
  public void setState(QuoteState state) {
    this.state = state;
  }

  @Override
  public void setState(String state) {
    this.state = QuoteState.valueOf(state);
  }

  @Override
  public LocalDate getStartDate() {
    return startDate;
  }

  @Override
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  @Override
  public List<PhotoDto> getListPhotoBefore() {
    return listPhotoBefore;
  }

  @Override
  public void setListPhotoBefore(List<PhotoDto> listPhotoBefore) {
    this.listPhotoBefore = listPhotoBefore;
  }

  @Override
  public void addToListPhotoBefore(PhotoDto photoDto) {
    this.listPhotoBefore.add(photoDto);
  }

  @Override
  public List<PhotoDto> getListPhotoAfter() {
    return listPhotoAfter;
  }

  public void setListPhotoAfter(List<PhotoDto> listPhotoAfter) {
    this.listPhotoAfter = listPhotoAfter;
  }

  @Override
  public CustomerDto getCustomer() {
    return customer;
  }

  @Override
  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
  }

  @Override
  public List<DevelopmentTypeDto> getDevelopmentTypes() {
    return developmentType;
  }

  @Override
  public void setDevelopmentType(List<DevelopmentTypeDto> developmentType) {
    this.developmentType = developmentType;
  }

  @Override
  public void addDevelopmentType(DevelopmentTypeDto developmentType) {
    this.developmentType.add(developmentType);
  }

  @Override
  public PhotoDto getPhoto() {
    return photo;
  }

  @Override
  public void setPhoto(PhotoDto photo) {
    this.photo = photo;
  }

  @Override
  public boolean checkState(QuoteState state) {
    if (this.state == QuoteState.CANCELLED) {
      return true;
    }
    return this.state == state;
  }
}
