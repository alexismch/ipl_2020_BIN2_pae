package be.ipl.pae.dal.services;

import java.sql.PreparedStatement;

public class MockDalService implements DalService {

  @Override
  public PreparedStatement getPreparedStatement(String requete) {

    return null;
  }

}
