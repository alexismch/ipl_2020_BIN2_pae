
package be.ipl.pae.biz.objets;

public class DevelopmentTypeImpl implements DevelopmentType {

  private int idType;
  private String title;

  public DevelopmentTypeImpl() {
    super();
  }

  /**
   * Create a DevelopmentTypeImpl object.
   *
   * @param idType the development type id
   * @param title the development type title
   */
  public DevelopmentTypeImpl(int idType, String title) {
    super();
    this.idType = idType;
    this.title = title;
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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DevelopmentTypeImpl other = (DevelopmentTypeImpl) obj;
    if (idType != other.idType)
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    return true;
  }


}

