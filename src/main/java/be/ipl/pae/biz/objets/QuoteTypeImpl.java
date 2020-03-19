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

  @Override
  public String getIdQuote() {
    return idQuote;
  }

  @Override
  public void setIdQuote(String idQuote) {
    this.idQuote = idQuote;
  }

  @Override
  public int getIdType() {
    return idType;
  }

  @Override
  public void setIdType(int idtype) {
    this.idType = idtype;
  }
}
