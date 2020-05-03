package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dependencies.AfterInjection;
import be.ipl.pae.dependencies.Injected;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockQuoteDao implements QuoteDao {

  @Injected
  private DtoFactory dtoFactory;

  private ArrayList<QuoteDto> liste = new ArrayList<>();

  @AfterInjection
  private void setList() {
    QuoteDto quoteDto1 = dtoFactory.getQuote();
    quoteDto1.setIdQuote("introduit");
    quoteDto1.setState(QuoteState.QUOTE_ENTERED);
    liste.add(quoteDto1);

    QuoteDto quoteDto2 = dtoFactory.getQuote();
    quoteDto2.setIdQuote("commandePassee");
    quoteDto2.setState(QuoteState.PLACED_ORDERED);
    liste.add(quoteDto2);

    QuoteDto quoteDto3 = dtoFactory.getQuote();
    quoteDto3.setIdQuote("dateConfirme");
    quoteDto3.setState(QuoteState.CONFIRMED_DATE);
    liste.add(quoteDto3);

    QuoteDto quoteDto5 = dtoFactory.getQuote();
    quoteDto5.setIdQuote("partiel");
    quoteDto5.setState(QuoteState.PARTIAL_INVOICE);
    liste.add(quoteDto5);

    QuoteDto quoteDto6 = dtoFactory.getQuote();
    quoteDto6.setIdQuote("Total");
    quoteDto6.setState(QuoteState.TOTAL_INVOICE);
    liste.add(quoteDto6);

    QuoteDto quoteDto7 = dtoFactory.getQuote();
    quoteDto7.setIdQuote("ok");
    quoteDto7.setState(QuoteState.VISIBLE);
    quoteDto7.setIdCustomer(1);
    liste.add(quoteDto7);

    QuoteDto quoteDto8 = dtoFactory.getQuote();
    quoteDto8.setIdQuote("annule");
    quoteDto8.setState(QuoteState.VISIBLE);
    liste.add(quoteDto8);
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
    return "false".equals(quoteId);
  }

  @Override
  public QuoteDto getQuote(String idQuote) {
    if (idQuote != null) {
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
    for (QuoteDto quoteDto : liste) {
      if (quoteDto.getIdQuote().equals(quote.getIdQuote())) {
        quoteDto.setStartDate(LocalDate.now());
        break;
      }
    }
  }

  @Override
  public void setStateQuote(QuoteState state, String quoteId) {
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
    switch (idQuote) {
      case "introduit":
        return QuoteState.QUOTE_ENTERED;
      case "dateConfirme":
      case "Total":
        return QuoteState.CONFIRMED_DATE;
      default:
        return null;
    }
  }

  @Override
  public void setFavoritePhoto(String quoteId, int photoId) {

  }
}
