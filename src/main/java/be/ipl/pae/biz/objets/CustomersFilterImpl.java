package be.ipl.pae.biz.objets;

class CustomersFilterImpl implements CustomersFilter {

  private String name;
  private int postalCode;
  private String city;
  private boolean onlyNotLinked;

  public CustomersFilterImpl() {
    super();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(int postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public boolean isOnlyNotLinked() {
    return onlyNotLinked;
  }

  @Override
  public void setOnlyNotLinked(boolean onlyNotLinked) {
    this.onlyNotLinked = onlyNotLinked;
  }

  @Override
  public String toString() {
    return "CustomersFilterImpl [name=" + name + ", postalCode=" + postalCode + ", city=" + city
        + "]";
  }
}
