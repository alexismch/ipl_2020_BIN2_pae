package be.ipl.pae.dal.service;

import be.ipl.pae.pae.dal.services.DalService;

import java.sql.PreparedStatement;

public class MockDalService implements DalService {

  @Override
  public PreparedStatement getPreparedStatement(String requete) {

    return null;
  }

}
