package be.ipl.pae.biz.objets;

public class PhotoImpl implements Photo {
  
  private String base64;
  private int id;
  private int title;
  private String idQuote;
  private boolean isVisible;
  private int idType;
  private boolean beforeWork;
  
  public PhotoImpl() {
    super();
  }
  
  public PhotoImpl(String base64, int id, int title, String idQuote, boolean isVisible, int idType,
      boolean beforeWork) {
    super();
    this.base64 = base64;
    this.id = id;
    this.title = title;
    this.idQuote = idQuote;
    this.isVisible = isVisible;
    this.idType = idType;
    this.beforeWork = beforeWork;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#getBase64()
   */
  public String getBase64() {
    return base64;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#setBase64(java.lang.String)
   */
  public void setBase64(String base64) {
    this.base64 = base64;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#getId()
   */
  public int getId() {
    return id;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#setId(int)
   */
  public void setId(int id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#getTitle()
   */
  public int getTitle() {
    return title;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#setTitle(int)
   */
  public void setTitle(int title) {
    this.title = title;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#getIdQuote()
   */
  public String getIdQuote() {
    return idQuote;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#setIdQuote(java.lang.String)
   */
  public void setIdQuote(String idQuote) {
    this.idQuote = idQuote;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#isVisible()
   */
  public boolean isVisible() {
    return isVisible;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#setVisible(boolean)
   */
  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#getIdType()
   */
  public int getIdType() {
    return idType;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#setIdType(int)
   */
  public void setIdType(int idType) {
    this.idType = idType;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#isBeforeWork()
   */
  public boolean isBeforeWork() {
    return beforeWork;
  }

  /* (non-Javadoc)
   * @see be.ipl.pae.biz.objets.PhotoTest#setBeforeWork(boolean)
   */
  public void setBeforeWork(boolean beforeWork) {
    this.beforeWork = beforeWork;
  }
  
  
  
}
