package be.ipl.pae.dal.services;

import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

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

import javax.sql.DataSource;


public class DalServiceImpl implements DalService, DalServiceTransaction {

  private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

  private static volatile DataSource dataSource;

  @Injected
  private DatabaseConfig databaseConfig;

  @Override
  public PreparedStatement getPreparedStatement(String request) {
    if (threadLocal.get() == null) {
      throw new IllegalStateException(
          "You must call startTransaction() before calling getPreparedStatement()");
    }

    PreparedStatement ps;
    try {
      ps = threadLocal.get().prepareStatement(request);
    } catch (SQLException ex) {
      throw new FatalException(ex);
    }
    return ps;
  }

  private DataSource setUpDataSource() {
    try {
      Class.forName(databaseConfig.getDiverName());
    } catch (ClassNotFoundException ex) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }

    String url = databaseConfig.getUrl();
    String user = databaseConfig.getUser();
    String password = databaseConfig.getPassword();

    //
    // First, we'll create a ConnectionFactory that the
    // pool will use to create Connections.
    // We'll use the DriverManagerConnectionFactory.
    //
    ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, user, password);

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

    return new PoolingDataSource<>(connectionPool);
  }

  private void getConnexion() throws FatalException {
    if (dataSource == null) {
      synchronized (DalServiceImpl.class) {
        if (dataSource == null) {
          dataSource = setUpDataSource();
        }
      }
    }

    if (threadLocal.get() == null) {
      try {
        threadLocal.set(dataSource.getConnection());
      } catch (SQLException ex) {
        throw new FatalException(ex);
      }
    }
  }

  private void closeConnection() throws FatalException {
    Connection connection = threadLocal.get();

    if (connection == null) {
      return;
    }

    try {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException se) {
        throw new FatalException(se);
      } finally {
        connection.close();
      }
    } catch (SQLException ex) {
      throw new FatalException(ex);
    } finally {
      threadLocal.remove();
    }
  }


  @Override
  public void startTransaction() throws FatalException {
    getConnexion();
    try {
      threadLocal.get().setAutoCommit(false);
    } catch (SQLException ex) {
      closeConnection();
      throw new FatalException(ex);
    }
  }


  @Override
  public void commitTransaction() throws FatalException {

    if (threadLocal.get() == null) {
      return;
    }

    try {
      threadLocal.get().commit();
    } catch (SQLException ex) {
      throw new FatalException(ex);
    } finally {
      closeConnection();
    }
  }


  @Override
  public void rollbackTransaction() throws FatalException {

    if (threadLocal.get() == null) {
      return;
    }

    try {
      threadLocal.get().rollback();
    } catch (SQLException ex) {
      throw new FatalException(ex);
    } finally {
      closeConnection();
    }
  }
}
