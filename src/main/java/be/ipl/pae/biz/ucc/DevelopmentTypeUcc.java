package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.exceptions.BizException;

import java.util.List;


public interface DevelopmentTypeUcc {

  /**
   * Get all the development types.
   *
   * @return a list of DevelopmentTypeDto
   * @throws BizException
   */
  List<DevelopmentTypeDto> getDevelopmentTypes() throws BizException;

}
