package be.ipl.pae.biz.objets;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;

import java.time.LocalDate;
import java.util.ArrayList;

public class QuotesFilterImpl implements QuotesFilter {

  private String customerName;
  private LocalDate quoteDate;
  private int totalAmountMin;
  private int totalAmountMax;
  private ArrayList<DevelopmentTypeDto> developmentTypeList;

  public QuotesFilterImpl() {
    super();
  }

  /**
   * Create a QuotesFilterImpl object.
   *
   * @param customerName the name of the customer
   * @param quoteDate the date of the quote
   * @param totalAmountMin the minimum of the amount range
   * @param totalAmountMax the maximum of the amount range
   * @param developmentType the type of the development
   * 
   */
  public QuotesFilterImpl(String customerName, LocalDate quoteDate, int totalAmountMin,
      int totalAmountMax, ArrayList<DevelopmentTypeDto> developmentTypeDto) {
    super();
    this.customerName = customerName;
    this.quoteDate = quoteDate;
    this.totalAmountMin = totalAmountMin;
    this.totalAmountMax = totalAmountMax;
    this.developmentTypeList = developmentTypeDto;
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
