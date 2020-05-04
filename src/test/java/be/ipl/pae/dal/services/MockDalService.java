package be.ipl.pae.dal.services;

import be.ipl.pae.exceptions.FatalException;

public class MockDalService implements DalServiceTransaction {

  @Override
  public void startTransaction() throws FatalException {
  }

  @Override
  public void commitTransaction() throws FatalException {
  }

  @Override
  public void rollbackTransaction() throws FatalException {
  }
}
