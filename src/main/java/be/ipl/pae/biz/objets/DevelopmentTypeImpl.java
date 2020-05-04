package be.ipl.pae.biz.objets;

class DevelopmentTypeImpl implements DevelopmentType {

  private int idType;
  private String title;

  public DevelopmentTypeImpl() {
    super();
  }

  @Override
  public int getIdType() {
    return idType;
  }

  @Override
  public void setIdType(int idType) {
    this.idType = idType;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + idType;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }
    DevelopmentTypeImpl other = (DevelopmentTypeImpl) obj;
    if (idType != other.idType) {
      return false;
    }
    if (title == null) {
      return other.title == null;
    } else {
      return title.equals(other.title);
    }
  }
}

