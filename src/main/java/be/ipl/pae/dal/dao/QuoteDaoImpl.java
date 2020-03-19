package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuoteDaoImpl implements QuoteDao {

  @Injected
  DalService dalService;

  @Injected
  DtoFactory quoteDto;

  public ArrayList<QuoteDto> getAllQuote() throws SQLException {
    ArrayList<QuoteDto> quotes = new ArrayList<QuoteDto>();
    PreparedStatement ps = dalService.getPreparedStatement("SELECT * FROM mystherbe.quotes");
    ResultSet res = ps.executeQuery();
    while (res.next()) {
      quotes.add(createQuoteDto(res));
    }
    return quotes;

  }

  public QuoteDto createQuoteDto(ResultSet res) throws SQLException {

    QuoteDto quote = quoteDto.getQuote();
    quote.setIdQuote(res.getNString(1));
    quote.setIdCustomer(res.getInt(2));
    quote.setQuoteDate(res.getDate(3));
    quote.setTotalAmount(res.getBigDecimal(4));
    quote.setWorkDuration(res.getInt(5));
    quote.setStartDate(res.getDate(6));
    quote.setState(res.getString(7));
    return quote;
  }

  public QuoteDto insertQuote(QuoteDto quoteDto) throws FatalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "INSERT INTO mystherbe.quotes "
            + "(id_quote, id_customer, quote_date, total_amount, work_duration, id_state)"
            + " VALUES (?, ?, ?::DATE, ?::MONEY, ?, "
            + "(SELECT id_state FROM mystherbe.states WHERE title = ?))");
    try {
      ps.setString(1, quoteDto.getIdQuote());
      ps.setInt(2, quoteDto.getIdCustomer());
      ps.setDate(3, quoteDto.getQuoteDate());
      ps.setBigDecimal(4, quoteDto.getTotalAmount());
      ps.setInt(5, quoteDto.getWorkDuration());
      ps.setString(6, quoteDto.getState().getTitle());
      if (!ps.execute()) {
        throw new FatalException("error with the db!");
      }
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("error with the db!");
    }

    return quoteDto;
  }

  public boolean checkQuoteIdInDb(String qId) throws FatalException {
    PreparedStatement ps = dalService
        .getPreparedStatement("SELECT * FROM mystherbe.quotes WHERE id_quote = ?");
    try {
      ps.setString(1, qId);
      try (ResultSet resultSet = ps.executeQuery()) {
        return resultSet.next();
      }
    } catch (SQLException ex) {
      throw new FatalException("error with the db");
    }
  }
}
