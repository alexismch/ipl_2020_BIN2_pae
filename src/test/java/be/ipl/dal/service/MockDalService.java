package be.ipl.dal.service;

import be.ipl.pae.dal.services.DalService;

import java.sql.PreparedStatement;

public class MockDalService implements DalService {

  @Override
  public PreparedStatement getPreparedStatement(String requete) {

    return null;
  }

}
