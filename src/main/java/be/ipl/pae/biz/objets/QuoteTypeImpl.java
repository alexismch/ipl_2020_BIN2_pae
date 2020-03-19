package be.ipl.pae.biz.objets;

public class QuoteTypeImpl implements QuoteType {

  private String idQuote;

  private int idType;


  public QuoteTypeImpl() {
    super();
  }

  public QuoteTypeImpl(String idQuote, int idType) {
    super();
    this.idQuote = idQuote;
    this.idType = idType;
  }

  public String getIdQuote() {
    return idQuote;
  }

  public void setIdQuote(String idQuote) {
    this.idQuote = idQuote;
  }

  public int getIdType() {
    return idType;
  }

  public void setIdType(int idtype) {
    this.idType = idtype;
  }
}
