package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.dal.dao.CustomerDao;
import be.ipl.pae.dal.services.DalServiceTransaction;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

public class CustomerUccImpl implements CustomerUcc {

  @Injected
  private CustomerDao customerDao;
  @Injected
  private DalServiceTransaction dalService;

  @Override
  public CustomerDto insert(CustomerDto customerDto) throws FatalException, BizException {
    try {
      try {
        dalService.startTransaction();
        return customerDao.insertCustomer(customerDto);

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
