package be.ipl.pae.biz.objets;

public enum QuoteState {
  QUOTE_ENTERED("Devis introduit",1),
  PLACED_ORDERED("Commande passée",2),
  CONFIRMED_DATE(
      "Date confirmée",3),
  POSTPONED_DATE("Date repoussée",4),
  PARTIAL_INVOICE(
      "Facturé partiellement",5),
  TOTAL_INVOICE(
      "Facturé totalement",6),
  PAID("Payé",7),
  CANCELLED("Annulé",8);

  private String title;
  private int id;

  QuoteState(String title, int id) {
    this.title = title;
    this.id = id;
  }

  public String getTitle() {
    return title;
  }
  

  public int getId() {
    return id;
  }

  /**
   * get a StateQuote object when you give him the name of the state.
   *
   * @param state the string that describe the status
   * @return a StateQuote object
   */
  public static QuoteState getStateByName(String state) {
    for (QuoteState quoteState : QuoteState.values()) {
      if (quoteState.getTitle().equals(state)) {
        return quoteState;
      }
    }
    throw new IllegalArgumentException("This status does not exist !");
  }
}
