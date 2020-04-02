package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.ResultSet;
import java.util.List;

public class MockQuoteDao implements QuoteDao {

  @Injected
  private DtoFactory dtoFactory;

  @Override
  public List<QuoteDto> getAllQuote() throws FatalException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public QuoteDto createQuoteDto(ResultSet res) throws FatalException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public QuoteDto insertQuote(QuoteDto quoteDto) throws FatalException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void linkToType(String quoteId, int typeId) throws FatalException {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean checkQuoteIdInDb(String quoteId) throws FatalException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public QuoteDto getQuote(String idQuote) throws FatalException {
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
  public List<QuoteDto> getCustomerQuotes(int idCustomer) throws FatalException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setStartDate(QuoteDto quote) throws FatalException {

  }

  @Override
  public void setStateQuote(QuoteState confirmedDate, String quoteId) throws FatalException {

  }

}
