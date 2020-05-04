package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;

import java.time.LocalDate;
import java.util.ArrayList;

class QuotesFilterImpl implements QuotesFilter {

  private String customerName;
  private LocalDate quoteDate;
  private int totalAmountMin;
  private int totalAmountMax;
  private ArrayList<DevelopmentTypeDto> developmentTypeList;

  public QuotesFilterImpl() {
    super();
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public LocalDate getQuoteDate() {
    return quoteDate;
  }

  public void setQuoteDate(LocalDate quoteDate) {
    this.quoteDate = quoteDate;
  }

  public int getTotalAmountMin() {
    return totalAmountMin;
  }

  public void setTotalAmountMin(int totalAmountMin) {
    this.totalAmountMin = totalAmountMin;
  }

  public int getTotalAmountMax() {
    return totalAmountMax;
  }

  public void setTotalAmountMax(int totalAmountMax) {
    this.totalAmountMax = totalAmountMax;
  }

  public ArrayList<DevelopmentTypeDto> getDevelopmentTypeDto() {
    return developmentTypeList;
  }

  public void setDevelopmentType(ArrayList<DevelopmentTypeDto> developmentTypeDto) {
    this.developmentTypeList = developmentTypeDto;
  }
}
