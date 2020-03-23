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
    // TODO Auto-generated method stub

    String query;

    if (customersFilterDto == null) {
      query = "SELECT * FROM mystherbe.customers";
    } else {
      query = "SELECT * FROM mystherbe.users WHERE lastname LIKE ? AND city LIKE ? AND postal_code LIKE ?";
    }

    PreparedStatement ps = dalService.getPreparedStatement(query);
    if (customersFilterDto != null) {
      String name = DalUtils.escapeSpecialLikeChar(customersFilterDto.getName());
      String city = DalUtils.escapeSpecialLikeChar(customersFilterDto.getCity());
      int postalCode = customersFilterDto.getPostalCode();
    }

    return null;
  }

  /**
   * Return list of customers created from the request. If only one customer is required you can
   * call {@link List#get} with the index 0.
   *
   * @param ps The request that will be executed
   * @return A list of CustomerDto created form the database
   * @throws SQLException if an SQL error occurred
   */
  private List<CustomerDto> getUsersViaPs(PreparedStatement ps) throws SQLException {
    List<CustomerDto> customers = new ArrayList<>();
    try (ResultSet resultSet = ps.executeQuery()) {
      while (resultSet.next()) {
        CustomerDto customerDto = customerDtoFactory.getCustomer();
        customers.add(customerDto);
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
}
