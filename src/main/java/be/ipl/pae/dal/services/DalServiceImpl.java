package be.ipl.pae.dal.services;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import config.LoadProperties;


public class DalServiceImpl implements DalService, DalServiceTransaction {

  private LoadProperties loadProperties;
  private Properties properties;
  private Connection conn = null;
  private String url;
  private String user;
  private String pwd;
  private static DataSource dataSource;
  private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

  /**
   * Builds an obj of type DalService whose properties are in prod.properties
   */
  public DalServiceImpl() {
    this.loadProperties = new LoadProperties();
    loadProperties.loadProperties();
    properties = loadProperties.getProperties();
    this.url = properties.getProperty("url");
    this.user = properties.getProperty("user");
    this.pwd = properties.getProperty("mdp");
  }


  @Override
  public PreparedStatement getPreparedStatement(String requete) {
    PreparedStatement ps = null;
    try {
      ps = threadLocal.get().prepareStatement(requete);
    } catch (SQLException ex) {
      System.out.println("probleme prepareStatement");
      ex.printStackTrace();
    }

    return ps;

  }


  private DataSource setUpDataSource() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException ex) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    //
    // First, we'll create a ConnectionFactory that the
    // pool will use to create Connections.
    // We'll use the DriverManagerConnectionFactory.
    //
    ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, user, pwd);

    //
    // Next we'll create the PoolableConnectionFactory, which wraps
    // the "real" Connections created by the ConnectionFactory with
    // the classes that implement the pooling functionality.
    //
    PoolableConnectionFactory poolableConnectionFactory =
        new PoolableConnectionFactory(connectionFactory, null);

    //
    // Now we'll need a ObjectPool that serves as the
    // actual pool of connections.
    //
    // We'll use a GenericObjectPool instance, although
    // any ObjectPool implementation will suffice.
    //
    ObjectPool<PoolableConnection> connectionPool =
        new GenericObjectPool<>(poolableConnectionFactory);

    // Set the factory's pool property to the owning pool
    poolableConnectionFactory.setPool(connectionPool);

    //
    // Finally, we create the PoolingDriver itself,
    // passing in the object pool we created.
    //
    PoolingDataSource<PoolableConnection> dataSourceToReturn =
        new PoolingDataSource<>(connectionPool);

    return dataSourceToReturn;
  }

  private void getConnexion() {
    synchronized (dataSource) {
      if (dataSource == null) {
        dataSource = setUpDataSource();
      }
    }

    if (threadLocal.get() == null) {
      try {
        threadLocal.set(dataSource.getConnection());
      } catch (SQLException ex) {
        // throw new FatalException(message);
        ex.printStackTrace();
      }
    }
  }

  private void closeConnection() {

    try {
      threadLocal.get().setAutoCommit(true);
    } catch (SQLException se) {
      se.printStackTrace();
    } finally {
      try {
        threadLocal.get().close();
      } catch (SQLException ex) {
        // TODO Auto-generated catch block
        ex.printStackTrace();
      } finally {
        threadLocal.remove();
      }
    }
  }


  @Override
  public void startTransaction() {
    getConnexion();
    try {
      threadLocal.get().setAutoCommit(false);
    } catch (SQLException ex) {
      closeConnection();
      ex.printStackTrace();
    }

  }


  @Override
  public void commitTransaction() {
    try {
      threadLocal.get().commit();
    } catch (SQLException ex) {
      // TODO Auto-generated catch block
      rollbackTransaction();
      ex.printStackTrace();
    } finally {
      closeConnection();
    }

  }


  @Override
  public void rollbackTransaction() {
    // TODO Auto-generated method stub
    try {
      threadLocal.get().rollback();
    } catch (SQLException ex) {
      // TODO Auto-generated catch block
      closeConnection();
      ex.printStackTrace();
    }
  }


}
