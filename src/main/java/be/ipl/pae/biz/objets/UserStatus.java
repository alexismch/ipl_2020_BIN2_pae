package be.ipl.pae.biz.objets;

public enum UserStatus {
  NOT_ACCEPTED("n", "Non-accepté"),
  CUSTOMER("c", "Client"),
  WORKER("o", "Ouvrier");

  private final String code;
  private final String name;

  UserStatus(String code, String name) {
    this.code = code;
    this.name = name;
  }

  /**
   * get an UserStatus for the corresponding code.
   *
   * @param code the string that that should match a UserStatus code
   * @return an UserStatus object
   */
  public static UserStatus getStatusByCode(String code) {
    for (UserStatus userStatus : UserStatus.values()) {
      if (userStatus.getCode().equals(code)) {
        return userStatus;
      }
    }
    throw new IllegalArgumentException("This status does not exist !");
  }

  public String getCode() {
    return this.code;
  }

  public String getName() {
    return name;
  }
}
