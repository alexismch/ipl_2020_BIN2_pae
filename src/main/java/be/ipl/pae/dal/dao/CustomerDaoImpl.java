package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dal.util.DalUtils;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.DalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

  @Injected
  private DalService dalService;

  @Injected
  private DtoFactory customerDtoFactory;

  @Override
  public List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) {

    String query;

    if (customersFilterDto == null) {
      query = "SELECT * FROM mystherbe.customers";
    } else {
      if (customersFilterDto.getPostalCode() == 0) {
        query = "SELECT * FROM mystherbe.customers "
            + "WHERE lower(lastname) LIKE lower(?) AND lower(city) LIKE lower(?)";
      } else {
        query = "SELECT * FROM mystherbe.customers "
            + "WHERE lower(lastname) LIKE lower(?) AND lower(city) LIKE lower(?) "
            + "AND postal_code = ?";
      }
      if (customersFilterDto.isOnlyNotLinked()) {
        query += " AND id_user IS NULL";
      }
    }
    query += " ORDER BY lastname, firstname";

    PreparedStatement ps = dalService.getPreparedStatement(query);
    try {

      if (customersFilterDto != null) {
        String name = DalUtils.escapeSpecialLikeChar(customersFilterDto.getName());
        String city = DalUtils.escapeSpecialLikeChar(customersFilterDto.getCity());
        int postalCode = customersFilterDto.getPostalCode();
        ps.setString(1, name + "%");
        ps.setString(2, city + "%");
        if (postalCode != 0) {
          ps.setInt(3, postalCode);
        }
      }
      return getCustomersViaPs(ps);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return new ArrayList<>();
  }

  /**
   * Return list of customers created from the request. If only one customer is required you can
   * call {@link List#get} with the index 0.
   *
   * @param ps The request that will be executed
   * @return A list of CustomerDto created form the database
   * @throws SQLException if an SQL error occurred
   */
  private List<CustomerDto> getCustomersViaPs(PreparedStatement ps) throws SQLException {

    List<CustomerDto> customers = new ArrayList<>();
    try (ResultSet resultSet = ps.executeQuery()) {
      while (resultSet.next()) {
        CustomerDto customerDto = customerDtoFactory.getCustomer();
        customerDto.setIdCustomer(resultSet.getInt(1));
        customerDto.setLastName(resultSet.getString(2));
        customerDto.setFirstName(resultSet.getString(3));
        customerDto.setAddress(resultSet.getString(4));
        customerDto.setPostalCode(resultSet.getInt(5));
        customerDto.setCity(resultSet.getString(6));
        customerDto.setEmail(resultSet.getString(7));
        customerDto.setPhoneNumber(resultSet.getString(8));
        customerDto.setIdUser(resultSet.getInt(9));
        customers.add(customerDto);
      }
    }
    ps.close();

    return customers;
  }

  @Override
  public CustomerDto insertCustomer(CustomerDto customer) throws DalException {

    PreparedStatement ps = dalService.getPreparedStatement("INSERT INTO mystherbe.customers(\r\n"
        + "    id_customer, lastname, firstname, address, postal_code, city, email, tel_nbr)\r\n"
        + "    VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?) RETURNING id_customer;");

    try {
      ps.setString(1, customer.getLastName());
      ps.setString(2, customer.getFirstName());
      ps.setString(3, customer.getAddress());
      ps.setInt(4, customer.getPostalCode());
      ps.setString(5, customer.getCity());
      ps.setString(6, customer.getEmail());
      ps.setString(7, customer.getPhoneNumber());

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        customer.setIdCustomer(rs.getInt(1));
        rs.close();
        return customer;
      } else {
        throw new Exception();
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      throw new DalException("error with the db!");
    }
  }

  @Override
  public boolean exists(int customerId) throws DalException {
    PreparedStatement ps =
        dalService.getPreparedStatement("SELECT * FROM mystherbe.customers WHERE id_customer = ?"
            + " ORDER BY lastname, firstname");

    try {
      ps.setInt(1, customerId);
      return ps.executeQuery().next();
    } catch (SQLException ex) {
      throw new DalException("error with the db!");
    }
  }

  @Override
  public boolean isLinked(int customerId) throws DalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "SELECT * FROM mystherbe.customers WHERE id_customer = ? AND id_user IS NOT NULL"
            + " ORDER BY lastname, firstname");
    try {
      ps.setInt(1, customerId);
      return ps.executeQuery().next();
    } catch (SQLException ex) {
      throw new DalException("error with the db!");
    }
  }

  @Override
  public CustomerDto getCustomer(int idCustomer) throws DalException {
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.customers WHERE id_customer =? "
        + " ORDER BY lastname, firstname");
    return getCustomerViaPs(ps, idCustomer);
  }

  @Override
  public CustomerDto getCustomerByIdUser(int idUser) throws DalException {
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.customers WHERE id_user =? "
        + " ORDER BY lastname, firstname");
    return getCustomerViaPs(ps, idUser);
  }

  /**
   * Get a CustomerDto via PreparedStatement.
   *
   * @param ps the PreparedStatement
   * @param id the first paramter of the ps
   * @return the CustomerDto or null
   * @throws DalException if an error occurred with the db
   */
  private CustomerDto getCustomerViaPs(PreparedStatement ps, int id) throws DalException {
    CustomerDto customerDto = customerDtoFactory.getCustomer();
    try {
      ps.setInt(1, id);
      try (ResultSet resultSet = ps.executeQuery()) {
        if (resultSet.next()) {
          customerDto.setIdCustomer(resultSet.getInt(1));
          customerDto.setLastName(resultSet.getString(2));
          customerDto.setFirstName(resultSet.getString(3));
          customerDto.setAddress(resultSet.getString(4));
          customerDto.setPostalCode(resultSet.getInt(5));
          customerDto.setCity(resultSet.getString(6));
          customerDto.setEmail(resultSet.getString(7));
          customerDto.setPhoneNumber(resultSet.getString(8));
          customerDto.setIdUser(resultSet.getInt(9));
        } else {
          return null;
        }
      }
    } catch (SQLException ex) {
      throw new DalException("error with the db!");
    }
    return customerDto;
  }
}
