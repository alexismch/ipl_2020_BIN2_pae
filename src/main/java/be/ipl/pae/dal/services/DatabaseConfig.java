package be.ipl.pae.dal.services;

import be.ipl.pae.dependencies.AfterInjection;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.FatalException;

class DatabaseConfig {

  @Injected("driver")
  private String diverName;

  @Injected("url")
  private String url;

  @Injected("user")
  private String user;

  @Injected("password")
  private String password;

  @SuppressWarnings("unused")
  @AfterInjection
  private void checkDriver() {
    try {
      Class.forName(this.diverName);
    } catch (ClassNotFoundException ex) {
      throw new FatalException("Database driver manquant !");
    }
  }

  public String getUrl() {
    return url;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }
}
