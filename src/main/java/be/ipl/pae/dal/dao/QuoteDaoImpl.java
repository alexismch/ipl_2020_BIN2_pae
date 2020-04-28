package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dal.util.DalUtils;
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
  DevelopmentTypeDao developmentTypeDao;

  @Injected
  DtoFactory quoteDtoFactory;

  @Injected
  CustomerDao customerDao;

  @Override
  public List<QuoteDto> getAllQuote() throws FatalException {
    List<QuoteDto> quotes = new ArrayList<>();
    PreparedStatement ps =
        dalService.getPreparedStatement("SELECT id_quote, id_customer, quote_date, "
            + "total_amount::decimal, work_duration, id_state, start_date "
            + "FROM mystherbe.quotes");
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
  public List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto) throws FatalException {

    ArrayList<QuoteDto> quotesList = new ArrayList<>();

    String querySelect = "SELECT q.id_quote, q.quote_date, q.total_amount::decimal, "
        + "q.work_duration, c.id_customer, q.id_state, q.start_date ";

    String queryFrom = "FROM mystherbe.quotes q, mystherbe.customers c ";

    String queryWhere = "WHERE (q.id_customer = c.id_customer)";

    boolean[] ref = new boolean[5];
    if (quotesFilterDto.getCustomerName() != null) {
      queryWhere += " AND lower(c.lastname) LIKE lower(?)";
      ref[0] = true;
    }
    if (quotesFilterDto.getTotalAmountMin() != -1) {
      queryWhere += "AND (q.total_amount::decimal > ?) ";
      ref[1] = true;

    }
    if (quotesFilterDto.getTotalAmountMax() != -1) {
      queryWhere += "AND (q.total_amount::decimal < ?) ";
      ref[2] = true;

    }
    if (quotesFilterDto.getQuoteDate() != null) {
      queryWhere += "AND (q.quote_date = ?) ";
      ref[3] = true;
    }
    int nbDevTypes = 0;
    if (quotesFilterDto.getDevelopmentTypeDto() != null
        && quotesFilterDto.getDevelopmentTypeDto().size() > 0) {
      ref[4] = true;
      for (DevelopmentTypeDto developementType : quotesFilterDto.getDevelopmentTypeDto()) {
        nbDevTypes++;
        querySelect += ", qt" + nbDevTypes + ".id_type ";
        queryFrom += ", mystherbe.quote_types qt" + nbDevTypes + " ";
        queryWhere += "AND (q.id_quote = qt" + nbDevTypes + ".id_quote) AND (qt" + nbDevTypes
            + ".id_type = ?) ";
      }
    }

    String query = querySelect + queryFrom + queryWhere;
    PreparedStatement ps = dalService.getPreparedStatement(query);
    int indexSet = 1;
    try {
      if (ref[0]) {
        String name = DalUtils.escapeSpecialLikeChar(quotesFilterDto.getCustomerName());
        ps.setString(indexSet, name + "%");
        indexSet++;
      }
      if (ref[1]) {
        ps.setInt(indexSet, quotesFilterDto.getTotalAmountMin());
        indexSet++;
      }
      if (ref[2]) {
        ps.setInt(indexSet, quotesFilterDto.getTotalAmountMax());
        indexSet++;
      }
      if (ref[3]) {
        ps.setDate(indexSet, Date.valueOf(quotesFilterDto.getQuoteDate()));
        indexSet++;
      }
      if (ref[4]) {
        for (DevelopmentTypeDto developmentType : quotesFilterDto.getDevelopmentTypeDto()) {
          ps.setInt(indexSet, developmentType.getIdType());
          indexSet++;
        }
      }

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          int inc = 1;
          QuoteDto quoteDto = quoteDtoFactory.getQuote();
          quoteDto.setIdQuote(rs.getString(inc));
          inc++;
          quoteDto.setQuoteDate(rs.getDate(inc).toLocalDate());
          inc++;
          quoteDto.setTotalAmount(rs.getBigDecimal(inc));
          inc++;
          quoteDto.setWorkDuration(rs.getInt(inc));
          inc++;
          quoteDto.setCustomer(customerDao.getCustomer(rs.getInt(inc)));
          inc++;
          quoteDto.setState(QuoteState.getById(rs.getInt(inc)));
          inc++;
          Date startDate = rs.getDate(inc);
          if (startDate != null) {
            quoteDto.setStartDate(startDate.toLocalDate());
          }
          inc++;
          if (quotesFilterDto.getDevelopmentTypeDto() != null
              && quotesFilterDto.getDevelopmentTypeDto().size() > 0) {
            ArrayList<DevelopmentTypeDto> listDevelopment = new ArrayList<>();
            for (DevelopmentTypeDto developmentType : quotesFilterDto.getDevelopmentTypeDto()) {
              listDevelopment.add(developmentTypeDao.getDevelopmentType(rs.getInt(inc)));
              inc++;
            }
            quoteDto.setDevelopmentType(listDevelopment);
          }
          quotesList.add(quoteDto);
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return quotesList;
  }

  @Override
  public List<QuoteDto> getCustomerQuotes(int idCustomer) throws FatalException {
    String query = "Select id_quote, id_customer, quote_date, "
        + "total_amount::decimal, work_duration, id_state, start_date"
        + " FROM mystherbe.quotes WHERE id_customer =?";

    PreparedStatement ps = dalService.getPreparedStatement(query);

    try {
      ps.setInt(1, idCustomer);
      return getCustomerQuotesViaPs(ps);
    } catch (SQLException ex) {
      throw new FatalException(ex);
    }
  }

  private List<QuoteDto> getCustomerQuotesViaPs(PreparedStatement ps)
      throws SQLException, FatalException {

    List<QuoteDto> customerQuotes = new ArrayList<>();
    try (ResultSet resultSet = ps.executeQuery()) {
      while (resultSet.next()) {
        QuoteDto quoteDto = quoteDtoFactory.getQuote();
        quoteDto.setIdQuote(resultSet.getString(1));
        quoteDto.setIdCustomer(resultSet.getInt(2));
        quoteDto.setQuoteDate(resultSet.getDate(3).toLocalDate());
        quoteDto.setTotalAmount(resultSet.getBigDecimal(4));
        quoteDto.setWorkDuration(resultSet.getInt(5));
        quoteDto.setState(QuoteState.getById(resultSet.getInt(6)));
        Date startDate = resultSet.getDate(7);
        if (startDate != null) {
          quoteDto.setStartDate(startDate.toLocalDate());
        }
        customerQuotes.add(quoteDto);
      }
    } catch (SQLException ex) {
      throw new FatalException(ex);
    }
    ps.close();

    return customerQuotes;
  }

  /**
   * Create a new Quote with all the informations collected in the db.
   *
   * @param res the result from the query
   * @return the quote collected as a dto object
   * @throws FatalException if a problem occurred with the db
   */
  private QuoteDto createQuoteDto(ResultSet res) throws FatalException {
    QuoteDto quote = quoteDtoFactory.getQuote();
    try {

      quote.setIdQuote(res.getString(1));
      quote.setIdCustomer(res.getInt(2));
      quote.setQuoteDate(res.getDate(3).toLocalDate());

      quote.setTotalAmount(res.getBigDecimal(4));
      quote.setWorkDuration(res.getInt(5));
      Date startDate = res.getDate(7);
      if (startDate != null) {
        quote.setStartDate(startDate.toLocalDate());
      }
      quote.setState(QuoteState.getById(res.getInt(6)));

      CustomerDto customer = customerDao.getCustomer(res.getInt(2));
      quote.setCustomer(customer);
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException(ex);

    }

    return quote;
  }

  @Override
  public void linkToType(String quoteId, int typeId) throws FatalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "INSERT INTO mystherbe.quote_types (id_quote, id_type) VALUES (?, ?)");

    try {
      ps.setString(1, quoteId);
      ps.setInt(2, typeId);
      ps.execute();
    } catch (SQLException ex) {
      throw new FatalException("error with the db");
    }
  }

  @Override
  public QuoteDto insertQuote(QuoteDto quoteDto) throws FatalException {
    PreparedStatement ps = dalService.getPreparedStatement("INSERT INTO mystherbe.quotes "
        + "(id_quote, id_customer, quote_date, total_amount, work_duration, id_state)"
        + " VALUES (?, ?, ?::DATE, ?::MONEY, ?, ?)");
    try {
      ps.setString(1, quoteDto.getIdQuote());
      ps.setInt(2, quoteDto.getIdCustomer());
      ps.setDate(3, Date.valueOf(quoteDto.getQuoteDate()));
      ps.setBigDecimal(4, quoteDto.getTotalAmount());
      ps.setInt(5, quoteDto.getWorkDuration());
      ps.setInt(6, quoteDto.getState().getId());

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
    QuoteDto quoteDtoToReturn = quoteDtoFactory.getQuote();
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select id_quote, id_customer, quote_date, "
        + "total_amount::decimal, work_duration, id_state, start_date "
        + "FROM mystherbe.quotes WHERE id_quote =? ");

    try {
      ps.setString(1, idQuote);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          quoteDtoToReturn.setIdQuote(resultSet.getString(1));
          quoteDtoToReturn.setIdCustomer(resultSet.getInt(2));
          quoteDtoToReturn.setQuoteDate(resultSet.getDate(3).toLocalDate());
          quoteDtoToReturn.setTotalAmount(resultSet.getBigDecimal(4));
          quoteDtoToReturn.setWorkDuration(resultSet.getInt(5));
          quoteDtoToReturn.setState(QuoteState.getById(resultSet.getInt(6)));
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
    return quoteDtoToReturn;
  }

  @Override
  public void setStartDate(QuoteDto quote) throws FatalException {
    PreparedStatement ps;
    ps = dalService
        .getPreparedStatement("UPDATE mystherbe.quotes SET start_date = ? WHERE id_quote = ? ");

    try {
      if (quote.getStartDate() == null) {
        ps.setDate(1, null);
      } else {
        ps.setDate(1, Date.valueOf(quote.getStartDate()));
      }
      ps.setString(2, quote.getIdQuote());
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException("error with the db!");
    }
  }

  @Override
  public void setStateQuote(QuoteState confirmedDate, String quoteId) throws FatalException {
    PreparedStatement ps;
    ps = dalService
        .getPreparedStatement("UPDATE mystherbe.quotes SET id_state = ? WHERE id_quote = ?");

    try {
      ps.setInt(1, confirmedDate.getId());
      ps.setString(2, quoteId);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException("error with the db!");
    }
  }

  @Override
  public int getWorkduRation(String idQuote) throws FatalException {
    QuoteDto quoteDtoToReturn = quoteDtoFactory.getQuote();
    PreparedStatement ps;
    ps = dalService
        .getPreparedStatement("Select work_duration " + "FROM mystherbe.quotes WHERE id_quote =? ");

    try {
      ps.setString(1, idQuote);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          quoteDtoToReturn.setWorkDuration(resultSet.getInt(1));
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException("error in the db!");
    }
    return quoteDtoToReturn.getWorkDuration();
  }

  @Override
  public QuoteState getStateQuote(String idQuote) throws FatalException {
    QuoteDto quoteDtoToReturn = quoteDtoFactory.getQuote();
    PreparedStatement ps;
    ps = dalService
        .getPreparedStatement("Select id_state " + "FROM mystherbe.quotes WHERE id_quote =? ");

    try {
      ps.setString(1, idQuote);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          quoteDtoToReturn.setState(QuoteState.getById(resultSet.getInt(1)));
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException("error in the db!");
    }
    return quoteDtoToReturn.getState();
  }

}
