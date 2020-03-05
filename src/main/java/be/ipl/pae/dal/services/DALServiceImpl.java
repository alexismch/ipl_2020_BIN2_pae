package be.ipl.pae.dal.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DALServiceImpl implements DALService {

  private Connection conn = null;
  private String url =
      "jdbc:postgresql://coursinfo.ipl.be:5432/dbalexis_michiels?user=alexis_michiels&password=KZQJKY2S";

  public DALServiceImpl() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    try {
      this.conn = DriverManager.getConnection(url);
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
  }

}
