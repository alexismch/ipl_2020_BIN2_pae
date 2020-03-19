package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;

import java.util.List;


public interface DevelopmentTypeUcc {

  /**Get all the development types 
   * 
   * @return a list of DevelopmentTypeDto
   */
  List<DevelopmentTypeDto> getDevelopmentTypes();

}
