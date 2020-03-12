package be.ipl.pae.dal.services;

import config.LoadProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


public class DalServiceImpl implements DalService {

  private LoadProperties loadProperties;
  private Properties properties;
  private Connection conn = null;
  private String url;
  private String user;
  private String mdp;

  /**
   * Construit un obj de type DalService dont les propriétés sont dans prod.properties
   */
  public DalServiceImpl() {
    this.loadProperties = new LoadProperties();
    loadProperties.loadProperties();
    properties = loadProperties.getProperties();
    this.url = properties.getProperty("url");
    this.user = properties.getProperty("user");
    this.mdp = properties.getProperty("mdp");
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
      this.conn = DriverManager.getConnection(url, user, mdp);
    } catch (SQLException ex) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
  }


  @Override
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
