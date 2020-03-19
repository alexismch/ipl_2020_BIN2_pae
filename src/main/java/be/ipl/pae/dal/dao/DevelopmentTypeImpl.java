package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevelopmentTypeImpl {

  @Injected
  DalService dalService;
  @Injected
  DtoFactory developmentTypeDto;
  
  public List<DevelopmentTypeDto> getAllDevelopmentType() {
    List<DevelopmentTypeDto> list = new ArrayList<DevelopmentTypeDto>();
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.development_types");

    try(ResultSet rs = ps.executeQuery()){
      while(rs.next()) {
        DevelopmentTypeDto developmentType =developmentTypeDto.getDevelopmentType();
        developmentType.setIdType(rs.getInt(1));
        developmentType.setTitle(rs.getString(2));
        list.add(developmentType);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}
