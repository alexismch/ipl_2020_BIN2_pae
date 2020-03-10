package be.ipl.pae.biz.objets;

import java.time.LocalDate;
import org.mindrot.bcrypt.BCrypt;

class UtilisateurImpl implements Utilisateur {

  // Je pense que tu as oublie le pseudo, je l'ai change et aussi adapte le
  // reste(interface,getter,sett,constructeur,...) //Ahmed
  private String pseudo;
  private int id;
  private String nom;
  private String prenom;
  private String mdp;
  private String ville;
  private String email;
  private LocalDate dateInscription;
  private String statut;

  public UtilisateurImpl() {
    super();
  }

  public UtilisateurImpl(String pseudo, String nom, String prenom, String mdp, String ville,
      String email, String statut) {
    this.pseudo = pseudo;
    this.nom = nom;
    this.prenom = prenom;
    this.mdp = mdp;
    this.ville = ville;
    this.email = email;
    this.dateInscription = LocalDate.now();
    this.statut = statut;
  }

  public String getPseudo() {
    return this.pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getMdp() {
    return mdp;
  }

  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getDateInscription() {
    return dateInscription;
  }

  public void setDateInscription(LocalDate dateInscription) {
    this.dateInscription = dateInscription;
  }

  public String getStatut() {
    return statut;
  }

  public void setStatut(String statut) {
    this.statut = statut;
  }

  @Override
  public boolean verifierMdp(String mdp) {
    return BCrypt.checkpw(mdp, this.mdp);
  }

}
