package be.ipl.pae.biz.objets;

public enum StatutUtilisateur {

  NON_VALIDE("nv"), CLIENT("c"), OUVRIER("o");

  private String statut;

  private StatutUtilisateur(String statut) {
    this.statut = statut;
  }

  public String getStatut() {
    return this.statut;
  }

}
