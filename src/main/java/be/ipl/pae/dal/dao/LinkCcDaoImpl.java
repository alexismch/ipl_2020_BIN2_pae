package be.ipl.pae.dal.dao;

import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.DalException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LinkCcDaoImpl implements LinkCcDao {

  @Injected
  private DalService dalService;

  @Override
  public void link(int customerId, int userId) throws DalException {
    PreparedStatement ps = dalService
        .getPreparedStatement("UPDATE mystherbe.customers SET id_user = ?  WHERE id_customer = ?");

    try {
      ps.setInt(1, userId);
      ps.setInt(2, customerId);
      ps.execute();
    } catch (SQLException ex) {
      throw new DalException("error with the db");
    }
  }
}
