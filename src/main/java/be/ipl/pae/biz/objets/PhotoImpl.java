package be.ipl.pae.biz.objets;

class PhotoImpl implements Photo {

  private String base64;
  private int id;
  private String title;
  private String idQuote;
  private boolean isVisible;
  private int idType;
  private boolean beforeWork;

  public PhotoImpl() {
    super();
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getBase64()
   */
  @Override
  public String getBase64() {
    return base64;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setBase64(java.lang.String)
   */
  @Override
  public void setBase64(String base64) {
    this.base64 = base64;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getId()
   */
  @Override
  public int getId() {
    return id;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setId(int)
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getTitle()
   */
  @Override
  public String getTitle() {
    return title;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setTitle(int)
   */
  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getIdQuote()
   */
  @Override
  public String getIdQuote() {
    return idQuote;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setIdQuote(java.lang.String)
   */
  @Override
  public void setIdQuote(String idQuote) {
    this.idQuote = idQuote;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#isVisible()
   */
  @Override
  public boolean isVisible() {
    return isVisible;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setVisible(boolean)
   */
  @Override
  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#getIdType()
   */
  @Override
  public int getIdType() {
    return idType;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setIdType(int)
   */
  @Override
  public void setIdType(int idType) {
    this.idType = idType;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#isBeforeWork()
   */
  @Override
  public boolean isBeforeWork() {
    return beforeWork;
  }

  /*
   * (non-Javadoc)
   *
   * @see be.ipl.pae.biz.objets.PhotoTest#setBeforeWork(boolean)
   */
  @Override
  public void setBeforeWork(boolean beforeWork) {
    this.beforeWork = beforeWork;
  }
}
