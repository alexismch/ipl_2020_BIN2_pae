package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.util.ArrayList;
import java.util.List;

public class MockQuoteDao implements QuoteDao {

  @Injected
  private DtoFactory dtoFactory;

  @Override
  public List<QuoteDto> getAllQuote() {
    List<QuoteDto> liste = new ArrayList<>();
    return liste;
  }

  @Override
  public QuoteDto insertQuote(QuoteDto quoteDto) {
    return quoteDto;
  }

  @Override
  public void linkToType(String quoteId, int typeId) {

  }

  @Override
  public boolean checkQuoteIdInDb(String quoteId) {
    return quoteId != null;
  }

  @Override
  public QuoteDto getQuote(String idQuote) {
    QuoteDto quoteDto = dtoFactory.getQuote();
    quoteDto.setIdQuote(idQuote);
    quoteDto.setIdCustomer(1);
    if (idQuote != null) {
      if (idQuote.equals("confirm")) {
        quoteDto.setState(QuoteState.PLACED_ORDERED);
      } else if (idQuote.equals("setDate")) {
        quoteDto.setState(QuoteState.CONFIRMED_DATE);
      } else {
        quoteDto.setState(QuoteState.QUOTE_ENTERED);
      }
    }
    return quoteDto;
  }

  @Override
  public List<QuoteDto> getCustomerQuotes(int idCustomer) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setStartDate(QuoteDto quote) {

  }

  @Override
  public void setStateQuote(QuoteState confirmedDate, String quoteId) {

  }

  @Override
  public List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto) {
    ArrayList<QuoteDto> testArray = new ArrayList<>();
    if (quotesFilterDto == null) {
      return testArray;
    }
    QuoteDto quoteDto = dtoFactory.getQuote();
    CustomerDto customerDto = dtoFactory.getCustomer();
    if (quotesFilterDto.getCustomerName() != null) {
      customerDto.setLastName(quotesFilterDto.getCustomerName());
      quoteDto.setCustomer(customerDto);
    }
    // if(quotesFilterDto.getTotalAmountMin()!= null) {
    // quoteDto.setTotalAmount(quotesFilterDto.getTotalAmountMin());
    // }
    // if(quotesFilterDto.getTotalAmountMin()!= null) {
    //
    // }
    return null;
  }

  @Override
  public int getWorkduRation(String idQuote) throws FatalException {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public QuoteState getStateQuote(String idQuote) throws FatalException {
    // TODO Auto-generated method stub
    return null;
  }

}
