package be.ipl.pae.biz.objets;

public enum StateQuote {
  QUOTE_ENTERED("Devis introduit"), PLACED_ORDERED("Commande passée"), CONFIRMED_DATE(
      "Date confirmée"), POSTPONED_DATE("Date repoussée"), PARTIAL_INVOICE(
          "Facturé partiellement"), TOTAL_INVOICE(
              "Facturé totalement"), PAID("Payé"), CANCELLED("Annulé");

  private String title;

  StateQuote(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  /**
   * get a StateQuote object when you give him the name of the state.
   *
   * @param state the string that describe the status
   * @return a StateQuote object
   */
  public static StateQuote getStateByName(String state) {
    for (StateQuote stateQuote : StateQuote.values()) {
      if (stateQuote.getTitle().equals(state)) {
        return stateQuote;
      }
    }
    throw new IllegalArgumentException("This status does not exist !");
  }
}
