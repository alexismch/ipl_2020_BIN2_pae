package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.QuotesFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dal.util.DalUtils;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.DalException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class QuoteDaoImpl implements QuoteDao {

  @Injected
  private DalService dalService;

  @Injected
  private DevelopmentTypeDao developmentTypeDao;

  @Injected
  private DtoFactory quoteDtoFactory;

  @Injected
  private CustomerDao customerDao;

  @Injected
  private PhotoDao photoDao;

  @Override
  public List<QuoteDto> getQuotesFiltered(QuotesFilterDto quotesFilterDto, int idCustomer)
      throws DalException {

    ArrayList<QuoteDto> quotesList = new ArrayList<>();

    StringBuilder querySelect = new StringBuilder(
        "SELECT q.id_quote, q.quote_date, q.total_amount, "
            + "q.work_duration, c.id_customer, q.id_state, q.start_date,id_photo ");

    StringBuilder queryFrom = new StringBuilder("FROM mystherbe.quotes q, mystherbe.customers c ");

    StringBuilder queryWhere = new StringBuilder("WHERE (q.id_customer = c.id_customer) ");

    boolean[] ref = new boolean[6];

    if (idCustomer > 0) {
      queryWhere.append("AND (c.id_customer = ?)");
      ref[0] = true;
    }
    if (quotesFilterDto.getCustomerName() != null) {
      queryWhere.append("AND lower(c.lastname) LIKE lower(?) ");
      ref[1] = true;
    }
    if (quotesFilterDto.getTotalAmountMin() != -1) {
      queryWhere.append("AND (q.total_amount > ?) ");
      ref[2] = true;

    }
    if (quotesFilterDto.getTotalAmountMax() != -1) {
      queryWhere.append("AND (q.total_amount < ?) ");
      ref[3] = true;

    }
    if (quotesFilterDto.getQuoteDate() != null) {
      queryWhere.append("AND (q.quote_date = ?) ");
      ref[4] = true;
    }
    if (quotesFilterDto.getDevelopmentTypeDto() != null
        && quotesFilterDto.getDevelopmentTypeDto().size() > 0) {
      ref[5] = true;
      for (int i = 1; i <= quotesFilterDto.getDevelopmentTypeDto().size(); i++) {
        querySelect.append(", qt").append(i).append(".id_type ");
        queryFrom.append(", mystherbe.quote_types qt").append(i).append(" ");
        queryWhere.append("AND (q.id_quote = qt").append(i).append(".id_quote) AND (qt").append(i)
            .append(".id_type = ?) ");
      }
    }

    String query = querySelect.toString() + queryFrom.toString() + queryWhere.toString();
    query += " ORDER BY id_quote";
    PreparedStatement ps = dalService.getPreparedStatement(query);
    int indexSet = 1;
    try {
      if (ref[0]) {
        ps.setInt(indexSet, idCustomer);
        indexSet++;
      }
      if (ref[1]) {
        String name = DalUtils.escapeSpecialLikeChar(quotesFilterDto.getCustomerName());
        ps.setString(indexSet, name + "%");
        indexSet++;
      }
      if (ref[2]) {
        ps.setInt(indexSet, quotesFilterDto.getTotalAmountMin());
        indexSet++;
      }
      if (ref[3]) {
        ps.setInt(indexSet, quotesFilterDto.getTotalAmountMax());
        indexSet++;
      }
      if (ref[4]) {
        ps.setDate(indexSet, Date.valueOf(quotesFilterDto.getQuoteDate()));
        indexSet++;
      }
      if (ref[5]) {
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
          quoteDto.setTotalAmount(rs.getDouble(inc));
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

          List<DevelopmentTypeDto> listDevelopment;
          listDevelopment = developmentTypeDao.getDevelopmentTypeList(rs.getString(1));
          quoteDto.setDevelopmentType(listDevelopment);

          PhotoDto photo = photoDao.getPhotoById(rs.getInt(8));
          if (photo != null) {
            quoteDto.setPhoto(photo);
          }
          quotesList.add(quoteDto);
        }
      }
    } catch (SQLException ex) {
      throw new DalException("error with the db");
    }

    return quotesList;
  }

  @Override
  public void linkToType(String quoteId, int typeId) throws DalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "INSERT INTO mystherbe.quote_types (id_quote, id_type) VALUES (?, ?)");

    try {
      ps.setString(1, quoteId);
      ps.setInt(2, typeId);
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("error with the db");
    }
  }

  @Override
  public QuoteDto insertQuote(QuoteDto quoteDto) throws DalException {
    PreparedStatement ps = dalService.getPreparedStatement("INSERT INTO mystherbe.quotes "
        + "(id_quote, id_customer, quote_date, total_amount, work_duration, id_state)"
        + " VALUES (?, ?, ?::DATE, ?, ?, ?)");
    try {
      ps.setString(1, quoteDto.getIdQuote());
      ps.setInt(2, quoteDto.getIdCustomer());
      ps.setDate(3, Date.valueOf(quoteDto.getQuoteDate()));
      ps.setDouble(4, quoteDto.getTotalAmount());
      ps.setInt(5, quoteDto.getWorkDuration());
      ps.setInt(6, quoteDto.getState().getId());

      ps.execute();
      ps.close();
    } catch (SQLException sqlE) {
      sqlE.printStackTrace();
      throw new DalException("Db error!");
    }

    return quoteDto;
  }

  @Override
  public boolean checkQuoteIdInDb(String quoteId) throws DalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "SELECT * FROM mystherbe.quotes WHERE id_quote = ?" + " ORDER BY id_quote");
    try {
      ps.setString(1, quoteId);
      try (ResultSet resultSet = ps.executeQuery()) {
        return resultSet.next();
      }
    } catch (SQLException ex) {
      throw new DalException("error with the db");
    }
  }

  @Override
  public QuoteDto getQuote(String idQuote) throws DalException {
    QuoteDto quoteDtoToReturn = quoteDtoFactory.getQuote();
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select id_quote, id_customer, quote_date, "
        + "total_amount, work_duration, id_state, start_date, id_photo "
        + "FROM mystherbe.quotes WHERE id_quote =? " + " ORDER BY id_quote");

    try {
      ps.setString(1, idQuote);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          quoteDtoToReturn.setIdQuote(resultSet.getString(1));
          quoteDtoToReturn.setIdCustomer(resultSet.getInt(2));
          quoteDtoToReturn.setQuoteDate(resultSet.getDate(3).toLocalDate());
          quoteDtoToReturn.setTotalAmount(resultSet.getDouble(4));
          quoteDtoToReturn.setWorkDuration(resultSet.getInt(5));
          quoteDtoToReturn.setState(QuoteState.getById(resultSet.getInt(6)));
          Date startDate = resultSet.getDate(7);
          PhotoDto photoDto = quoteDtoFactory.getPhoto();
          photoDto.setId(resultSet.getInt(8));
          quoteDtoToReturn.setPhoto(photoDto);
          if (startDate != null) {
            quoteDtoToReturn.setStartDate(startDate.toLocalDate());
          }
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new DalException("error in the db!");
    }
    return quoteDtoToReturn;
  }

  @Override
  public void setStartDate(QuoteDto quote) throws DalException {
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
      throw new DalException("error with the db!");
    }
  }

  @Override
  public void setStateQuote(QuoteState confirmedDate, String quoteId) throws DalException {
    PreparedStatement ps;
    ps = dalService
        .getPreparedStatement("UPDATE mystherbe.quotes SET id_state = ? WHERE id_quote = ?");

    try {
      ps.setInt(1, confirmedDate.getId());
      ps.setString(2, quoteId);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new DalException("error with the db!");
    }
  }

  @Override
  public int getWorkDuration(String idQuote) throws DalException {
    QuoteDto quoteDtoToReturn = quoteDtoFactory.getQuote();
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select work_duration "
        + "FROM mystherbe.quotes WHERE id_quote =? " + " ORDER BY id_quote");

    try {
      ps.setString(1, idQuote);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          quoteDtoToReturn.setWorkDuration(resultSet.getInt(1));
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new DalException("error in the db!");
    }
    return quoteDtoToReturn.getWorkDuration();
  }

  @Override
  public QuoteState getStateQuote(String idQuote) throws DalException {
    QuoteDto quoteDtoToReturn = quoteDtoFactory.getQuote();
    PreparedStatement ps;
    ps = dalService.getPreparedStatement(
        "Select id_state " + "FROM mystherbe.quotes WHERE id_quote =? " + " ORDER BY id_quote");

    try {
      ps.setString(1, idQuote);
      try (ResultSet resultSet = ps.executeQuery()) {
        while (resultSet.next()) {
          quoteDtoToReturn.setState(QuoteState.getById(resultSet.getInt(1)));
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new DalException("error in the db!");
    }
    return quoteDtoToReturn.getState();
  }

  @Override
  public void setFavoritePhoto(String quoteId, int photoId) throws DalException {
    PreparedStatement ps = dalService
        .getPreparedStatement("UPDATE mystherbe.quotes SET id_photo = ? WHERE id_quote = ?");

    try {
      ps.setInt(1, photoId);
      ps.setString(2, quoteId);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new DalException("error with the db!");
    }
  }
}
