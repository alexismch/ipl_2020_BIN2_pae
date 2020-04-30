package be.ipl.pae.dal.services;

import be.ipl.pae.exceptions.DalException;

public class MockDalService implements DalServiceTransaction {

  private boolean transactionStarted;

  @Override
  public void startTransaction() throws DalException {
    if (this.transactionStarted) {
      throw new DalException("Transaction already started");
    }
    this.transactionStarted = true;
  }

  @Override
  public void commitTransaction() throws DalException {
    if (!this.transactionStarted) {
      throw new DalException("Transaction not started");
    }
    this.transactionStarted = false;
  }

  @Override
  public void rollbackTransaction() throws DalException {
    if (!this.transactionStarted) {
      throw new DalException("Transaction not started");
    }
    this.transactionStarted = false;
  }

}
