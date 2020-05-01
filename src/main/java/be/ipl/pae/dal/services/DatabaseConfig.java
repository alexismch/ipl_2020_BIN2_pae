package be.ipl.pae.dal.services;

import be.ipl.pae.dependencies.Injected;

class DatabaseConfig {

  @Injected("driver")
  private String diverName;

  @Injected("url")
  private String url;

  @Injected("user")
  private String user;

  @Injected("password")
  private String password;

  public String getDiverName() {
    return diverName;
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
