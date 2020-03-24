package be.ipl.pae.biz.ucc;

import be.ipl.pae.exceptions.BizException;

public interface LinkCCUcc {

  /**
   * Link a customer to a client into the db.
   *
   * @param customerId the customer id
   * @param userId     the user id
   * @throws BizException if the customer or the user are already linked
   */
  void link(int customerId, int userId) throws BizException;
}
