package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.DevelopmentTypeDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.dal.services.DalService;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevelopmentTypeDaoImpl implements DevelopmentTypeDao {

  @Injected
  DalService dalService;

  @Injected
  DtoFactory developmentTypeDtoFactory;

  @Override
  public List<DevelopmentTypeDto> getdevelopmentTypes() {
    List<DevelopmentTypeDto> list = new ArrayList<>();
    PreparedStatement ps;
    ps = dalService.getPreparedStatement("Select * FROM mystherbe.development_types");

    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        DevelopmentTypeDto developmentType = developmentTypeDtoFactory.getDevelopmentType();
        developmentType.setIdType(rs.getInt(1));
        developmentType.setTitle(rs.getString(2));
        list.add(developmentType);
      }
    } catch (SQLException sqlE) {
      sqlE.printStackTrace();
    }
    return list;
  }

  @Override
  public DevelopmentTypeDto getDevelopmentType(int typeId) throws FatalException {
    PreparedStatement ps = dalService
        .getPreparedStatement("SElECT * FROM mystherbe.development_types WHERE id_type = ?");

    try {
      ps.setInt(1, typeId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        DevelopmentTypeDto developmentType = developmentTypeDtoFactory.getDevelopmentType();
        developmentType.setIdType(rs.getInt(1));
        developmentType.setTitle(rs.getString(2));
        return developmentType;
      } else {
        return null;
      }
    } catch (SQLException ex) {
      throw new FatalException("error with the db");
    }
  }
}
