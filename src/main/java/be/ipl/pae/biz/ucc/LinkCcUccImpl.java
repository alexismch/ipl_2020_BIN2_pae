package be.ipl.pae.biz.ucc;

import be.ipl.pae.dal.dao.CustomerDao;
import be.ipl.pae.dal.dao.LinkCcDao;
import be.ipl.pae.dal.dao.UserDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

public class LinkCcUccImpl implements LinkCcUcc {

  @Injected
  private UserDao userDao;

  @Injected
  private CustomerDao customerDao;

  @Injected
  private LinkCcDao linkCcDao;

  @Injected
  private DalServiceTransaction dalService;

  @Override
  public void link(int customerId, int userId) throws BizException {
    try {
      try {
        dalService.startTransaction();

        if (!customerDao.exists(customerId)) {
          throw new BizException("Le client n'existe pas.");
        }
        if (userDao.getUser(userId) == null) {
          throw new BizException("L'utilisateur n'existe pas.");
        }
        if (customerDao.isLinked(customerId)) {
          throw new BizException("Le client est déjà lié à un utilisateur.");
        }
        if (userDao.isLinked(userId)) {
          throw new BizException("L'utilisateur est déjà lié à un client.");
        }

        linkCcDao.link(customerId, userId);
      } catch (FatalException fx) {
        dalService.rollbackTransaction();
        throw new BizException(fx);
      } finally {
        dalService.commitTransaction();
      }
    } catch (FatalException ex) {
      throw new BizException(ex);
    }
  }
}
