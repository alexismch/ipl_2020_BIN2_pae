package be.ipl.pae.dal.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import config.LoadProperties;

public class DalServiceImpl implements DalService {

  private LoadProperties properties;
  private Connection conn = null;
  private String url =
      "jdbc:postgresql://coursinfo.ipl.be:5432/dbalexis_michiels?user=alexis_michiels&password=KZQ"
          + "JKY2S";

  public DalServiceImpl() {

    initierConnexion();

  }

  private void initierConnexion() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException ex) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    try {
      this.conn = DriverManager.getConnection(url);
    } catch (SQLException ex) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
  }

  public PreparedStatement getPreparedStatement(String requete) {
    PreparedStatement ps = null;
    try {
      ps = conn.prepareStatement(requete);
    } catch (SQLException ex) {
      System.out.println("probleme prepareStatement");
      ex.printStackTrace();
    }

    return ps;

  }


}
