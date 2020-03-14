package be.ipl.pae.biz.objets;

public enum UserStatus {

  NON_VALIDE("nv"),
  CLIENT("c"),
  OUVRIER("o");

  private String status;

  UserStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return this.status;
  }

}
