package be.ipl.pae.dal.dao;

import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LinkCCDaoImpl implements LinkCCDao {

  @Injected
  DalService dalService;

  @Override
  public void link(int customerId, int userId) throws FatalException {
    PreparedStatement ps = dalService
        .getPreparedStatement("UPDATE mystherbe.customers SET id_user = ? WHERE id_customer = ?");

    try {
      ps.setInt(1, userId);
      ps.setInt(2, customerId);
      ps.execute();
    } catch (SQLException e) {
      throw new FatalException("error with the db!");
    }
  }
}
