package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.dal.dao.CustomerDao;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

public class CustomerUccImpl implements CustomerUcc {

  @Injected
  private CustomerDao customerDao;

  @Override
  public CustomerDto insert(CustomerDto customerDto) throws FatalException, BizException {
    return null;
  }
}
