package be.ipl.pae.biz.ucc;

import be.ipl.pae.dal.dao.CustomerDao;

import be.ipl.pae.dependencies.Injected;

public class CustomerUccImpl {

  @Injected
  private CustomerDao customerDao;
}
