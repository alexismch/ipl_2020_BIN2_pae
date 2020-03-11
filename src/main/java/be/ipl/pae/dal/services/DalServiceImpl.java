package be.ipl.pae.dal.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import config.LoadProperties;

public class DalServiceImpl implements DalService {

  private LoadProperties loadProperties;
  private Properties properties;
  private Connection conn = null;
  private String url;
  private String user;
  private String mdp;

  public DalServiceImpl() {
    this.loadProperties = new LoadProperties();
    loadProperties.createPropertiesFile();
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