package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dependencies.Injected;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockQuoteDao implements QuoteDao {

  @Injected
  private DtoFactory dtoFactory;

  private ArrayList<QuoteDto> liste = new ArrayList<>();

  private void setList() {
    if (liste.isEmpty()) {
      QuoteDto quoteDto1 = dtoFactory.getQuote();
      QuoteDto quoteDto2 = dtoFactory.getQuote();
      QuoteDto quoteDto3 = dtoFactory.getQuote();
      QuoteDto quoteDto4 = dtoFactory.getQuote();

      quoteDto1.setIdQuote("introduit");
      quoteDto2.setIdQuote("ok");
      quoteDto3.setIdQuote("dateConfirme");
      quoteDto4.setIdQuote("Total");

      quoteDto2.setIdCustomer(1);

      liste.add(quoteDto1);
      liste.add(quoteDto2);
      liste.add(quoteDto3);
      liste.add(quoteDto4);
    }
  }

  /*
   * @Override public List<QuoteDto> getAllQuote() { List<QuoteDto> liste = new ArrayList<>();
   * return liste; }
   */

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
    if (idQuote != null) {
      setList();
      for (QuoteDto quoteDto : liste) {
        if (quoteDto.getIdQuote().equals(idQuote)) {
          return quoteDto;
        }
      }
    }
    return dtoFactory.getQuote();
  }

  @Override
  public List<QuoteDto> getCustomerQuotes(int idCustomer) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setStartDate(QuoteDto quote) {
    setList();
    for (QuoteDto quoteDto : liste) {
      if (quoteDto.getIdQuote().equals(quote.getIdQuote())) {
        quoteDto.setStartDate(LocalDate.now());
        break;
      }
    }
  }

  @Override
  public void setStateQuote(QuoteState state, String quoteId) {
    setList();
    for (QuoteDto quoteDto : liste) {
      if (quoteDto.getIdQuote().equals(quoteId)) {
        quoteDto.setState(state);
        break;
      }
    }
  }

  @Override
  public List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto, int idCustomer) {
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
    testArray.add(quoteDto);

    return testArray;
  }

  @Override
  public int getWorkDuration(String idQuote) {
    if (idQuote.equals("dateConfirme")) {
      return 20;
    }
    return 5;
  }

  @Override
  public QuoteState getStateQuote(String idQuote) {
    if (idQuote.equals("introduit")) {
      return QuoteState.QUOTE_ENTERED;
    } else if (idQuote.equals("dateConfirme")) {
      return QuoteState.CONFIRMED_DATE;
    } else if (idQuote.equals("Total")) {
      return QuoteState.CONFIRMED_DATE;
    }
    return null;
  }

  @Override
  public void setFavoritePhoto(String quoteId, int photoId) {

  }
}
