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

  @Override
  public List<DevelopmentTypeDto> getDevelopmentTypeList(String quoteId) throws FatalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "SElECT dt.id_type, dt.title FROM mystherbe.development_types dt, mystherbe.quote_types qt "
            + " WHERE qt.id_quote = ?"
            + " AND qt.id_type = dt.id_type");

    try {
      ps.setString(1, quoteId);
      return getDevelopmentTypeDtoViaPs(ps);
    } catch (SQLException ex) {
      throw new FatalException("error with the db!");
    }
  }


  private List<DevelopmentTypeDto> getDevelopmentTypeDtoViaPs(PreparedStatement ps)
      throws SQLException {
    List<DevelopmentTypeDto> listToReturn = new ArrayList<DevelopmentTypeDto>();

    try (ResultSet resultSet = ps.executeQuery()) {
      while (resultSet.next()) {
        DevelopmentTypeDto developmentTypeDto = developmentTypeDtoFactory.getDevelopmentType();
        developmentTypeDto.setIdType(resultSet.getInt(1));
        developmentTypeDto.setTitle(resultSet.getString(2));
        listToReturn.add(developmentTypeDto);
      }
    }
    ps.close();
    return listToReturn;
  }

  @Override
  public DevelopmentTypeDto insert(DevelopmentTypeDto developmentType) throws FatalException {
    PreparedStatement ps = dalService.getPreparedStatement(
        "INSERT INTO mystherbe.development_types (title) VALUES (?) returning id_type");

    try {
      ps.setString(1, developmentType.getTitle());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        developmentType.setIdType(rs.getInt(1));
        return developmentType;
      } else {
        throw new FatalException("error with the db");
      }
    } catch (SQLException ex) {
      throw new FatalException("error with the db");
    }
  }
}
