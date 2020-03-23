package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDaoImpl implements CustomerDao {

  @Injected
  DalService dalService;
  @Injected
  DtoFactory customerDto;


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
        customer.setIdcustomer(rs.getString(1));
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
