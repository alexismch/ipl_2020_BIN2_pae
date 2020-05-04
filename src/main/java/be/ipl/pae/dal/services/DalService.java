package be.ipl.pae.dal.services;

import java.sql.PreparedStatement;

public interface DalService {

  /**
   * Get a prepareStatement depending on the request we give him.
   *
   * @param request the request
   * @return An PrepareStatement object
   */
  PreparedStatement getPreparedStatement(String request);
}
