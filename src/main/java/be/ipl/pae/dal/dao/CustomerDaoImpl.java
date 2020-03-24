package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.dto.CustomersFilterDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dal.util.DalUtils;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

  @Injected
  DalService dalService;
  @Injected
  DtoFactory customerDtoFactory;

  @Override

  public List<CustomerDto> getCustomers(CustomersFilterDto customersFilterDto) {
    System.out.println("here getCustomers");

    String query;

    if (customersFilterDto == null) {
      query = "SELECT * FROM mystherbe.customers";
    } else {
      if (customersFilterDto.getPostalCode() == 0) {
        query = "SELECT * FROM mystherbe.customers WHERE lastname LIKE ? AND city LIKE ?";
      } else {
        query =
            "SELECT * FROM mystherbe.customers WHERE lastname LIKE ? AND city LIKE ? AND postalCode = ?";
      }
    }

    PreparedStatement ps = dalService.getPreparedStatement(query);
    try {

      if (customersFilterDto != null) {
        System.out.println("here getCustomers condition 1");
        String name = DalUtils.escapeSpecialLikeChar(customersFilterDto.getName());
        String city = DalUtils.escapeSpecialLikeChar(customersFilterDto.getCity());
        int postalCode = customersFilterDto.getPostalCode();
        ps.setString(1, name + "%");
        ps.setString(2, city + "%");
        if (postalCode != 0) {
          ps.setInt(3, postalCode);
        }
      }
      System.out.println("here getCustomers condition 222");
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
    System.out.println("here getCustomers condition 5555");

    List<CustomerDto> customers = new ArrayList<>();
    try (ResultSet resultSet = ps.executeQuery()) {
      while (resultSet.next()) {
        CustomerDto customerDto = customerDtoFactory.getCustomer();
        customerDto.setIdcustomer(resultSet.getInt(1));
        customerDto.setLastname(resultSet.getString(2));
        customerDto.setFirstname(resultSet.getString(3));
        customerDto.setAddress(resultSet.getString(4));
        customerDto.setPostalcode(resultSet.getInt(5));
        customerDto.setCity(resultSet.getString(6));
        customerDto.setEmail(resultSet.getString(7));
        customerDto.setTelnbr(resultSet.getString(8));
        customerDto.setIdUser(resultSet.getInt(9));
        customers.add(customerDto);
        System.out.println("here getCustomerViaPs");
      }
    }
    ps.close();

    return customers;
  }

  @Override
  public CustomerDto insertCustomer(CustomerDto customer) throws FatalException {

    PreparedStatement ps = dalService.getPreparedStatement("INSERT INTO mystherbe.customers(\r\n"
        + "    id_customer, lastname, firstname, address, postal_code, city, email, tel_nbr)\r\n"
        + "    VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?) RETURNING id_customer;");

    try {
      ps.setString(1, customer.getLastname());
      ps.setString(2, customer.getFirstname());
      ps.setString(3, customer.getAddress());
      ps.setInt(4, customer.getPostalcode());
      ps.setString(5, customer.getCity());
      ps.setString(6, customer.getEmail());
      ps.setString(7, customer.getTelnbr());

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        customer.setIdcustomer(rs.getInt(1));
        rs.close();
        return customer;
      } else {
        throw new Exception();
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      throw new FatalException("error with the db!");
    }
  }

  @Override
  public boolean exists(int customerId) throws FatalException {
    PreparedStatement ps =
        dalService.getPreparedStatement("SELECT * FROM mystherbe.customers WHERE id_customer = ?");

    try {
      ps.setInt(1, customerId);
      return ps.executeQuery().next();
    } catch (SQLException ex) {
      throw new FatalException("error with the db!");
    }
  }

  @Override
  public boolean isLinked(int customerId) throws FatalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "SELECT * FROM mystherbe.customers WHERE id_customer = ? AND id_user IS NOT NULL");
    try {
      ps.setInt(1, customerId);
      return ps.executeQuery().next();
    } catch (SQLException ex) {
      throw new FatalException("error with the db!");
    }
  }
}
