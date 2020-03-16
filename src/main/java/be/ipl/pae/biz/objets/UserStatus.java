package be.ipl.pae.biz.objets;

public enum UserStatus {

  NOT_ACCEPTED("nv"),
  CUSTOMER("c"),
  WORKER("o");

  private String name;

  UserStatus(String name) {
    this.name = name;
  }

  public static UserStatus getStatusByName(String status) {
    for (UserStatus userStatus : UserStatus.values()) {
      if (userStatus.getName().equals(status)) {
        return userStatus;
      }
    }
    throw new IllegalArgumentException("This status does not exist !");
  }

  public String getName() {
    return this.name;
  }

}
