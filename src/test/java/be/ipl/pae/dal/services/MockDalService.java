package be.ipl.pae.dal.services;

import be.ipl.pae.exceptions.FatalException;

public class MockDalService implements DalServiceTransaction {

  private boolean transactionStarted;

  @Override
  public void startTransaction() throws FatalException {
    if (this.transactionStarted) {
      throw new FatalException("Transaction already started");
    }
    this.transactionStarted = true;
  }

  @Override
  public void commitTransaction() throws FatalException {
    if (!this.transactionStarted) {
      throw new FatalException("Transaction not started");
    }
    this.transactionStarted = false;
  }

  @Override
  public void rollbackTransaction() throws FatalException {
    if (!this.transactionStarted) {
      throw new FatalException("Transaction not started");
    }
    this.transactionStarted = false;
  }

}
