package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.StateQuote;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuoteDaoImpl implements QuoteDao {

  @Injected
  DalService dalService;

  @Injected
  DtoFactory quoteDto;


  public List<QuoteDto> getAllQuote() throws FatalException {
    List<QuoteDto> quotes = new ArrayList<>();
    PreparedStatement ps = dalService.getPreparedStatement("SELECT * FROM mystherbe.quotes");
    try (ResultSet res = ps.executeQuery()) {
      while (res.next()) {
        quotes.add(createQuoteDto(res));
      }
    } catch (SQLException sqlE) {
      sqlE.printStackTrace();
    }

    return quotes;

  }

  @Override
  public QuoteDto createQuoteDto(ResultSet res) throws FatalException {
    QuoteDto quote = null;
    try {
      quote = quoteDto.getQuote();
      quote.setIdQuote(res.getString(1));
      quote.setIdCustomer(res.getInt(2));
      // quote.setQuoteDate(res.getDate(3));
      // quote.setTotalAmount(res.getBigDecimal(4));
      quote.setWorkDuration(res.getInt(5));
      // quote.setStartDate(res.getDate(6));
      // quote.setState(res.getString(7));

    } catch (SQLException ex) {
      throw new FatalException(ex);
    }

    return quote;
  }

  @Override
  public QuoteDto insertQuote(QuoteDto quoteDto) throws FatalException {
    PreparedStatement ps = dalService.getPreparedStatement("INSERT INTO mystherbe.quotes "
        + "(id_quote, id_customer, quote_date, total_amount, work_duration, id_state)"
        + " VALUES (?, ?, ?::DATE, ?::MONEY, ?, "
        + "(SELECT id_state FROM mystherbe.states WHERE title = ?))");
    try {
      ps.setString(1, quoteDto.getIdQuote());
      ps.setInt(2, quoteDto.getIdCustomer());
      ps.setDate(3, Date.valueOf(quoteDto.getQuoteDate()));
      ps.setBigDecimal(4, quoteDto.getTotalAmount());
      ps.setInt(5, quoteDto.getWorkDuration());
      ps.setString(6, quoteDto.getState().getTitle());

      ps.execute();
      ps.close();
    } catch (SQLException sqlE) {
      sqlE.printStackTrace();
      throw new FatalException("Db error!");
    }

    return quoteDto;
  }

  @Override
  public boolean checkQuoteIdInDb(String quoteId) throws FatalException {
    PreparedStatement ps =
        dalService.getPreparedStatement("SELECT * FROM mystherbe.quotes WHERE id_quote = ?");
    try {
      ps.setString(1, quoteId);
      try (ResultSet resultSet = ps.executeQuery()) {
        return resultSet.next();
      }
    } catch (SQLException ex) {
      throw new FatalException("error with the db");
    }
  }

  @Override
  public QuoteDto getQuote(String idQuote) throws FatalException {
    QuoteDto quoteDtoToReturn = quoteDto.getQuote();
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select id_quote, id_customer, quote_date, "
        + "total_amount::decimal, work_duration, id_state, start_date FROM mystherbe.quotes WHERE id_quote =? ");


    try {
      ps.setString(1, idQuote);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          quoteDtoToReturn.setIdQuote(resultSet.getString(1));
          quoteDtoToReturn.setIdCustomer(resultSet.getInt(2));
          quoteDtoToReturn.setQuoteDate(resultSet.getDate(3).toLocalDate());
          quoteDtoToReturn.setTotalAmount(resultSet.getBigDecimal(4));
          quoteDtoToReturn.setWorkDuration(resultSet.getInt(5));
          quoteDtoToReturn.setState(getStateById(resultSet.getInt(6)));
          Date startDate = resultSet.getDate(7);
          if (startDate != null) {
            quoteDtoToReturn.setStartDate(startDate.toLocalDate());
          }
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException("error in the db!");
    }

    System.out.println(quoteDtoToReturn.getQuoteDate());

    return quoteDtoToReturn;
  }

  private StateQuote getStateById(int idState) throws FatalException {
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.states WHERE id_state =? ");
    StateQuote stateQuote = null;

    try {
      ps.setInt(1, idState);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          stateQuote = StateQuote.getStateByName(resultSet.getString(2));
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException("error with the db!");
    }
    return stateQuote;
  }
}
