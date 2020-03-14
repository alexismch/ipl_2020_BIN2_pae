package be.ipl.pae.biz.dto;

import java.time.LocalDate;

public interface UserDto {

  String getPseudo();

  void setPseudo(String pseudo);

  int getId();

  void setId(int id);

  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getMdp();

  void setMdp(String mdp);

  String getVille();

  void setVille(String ville);

  String getEmail();

  void setEmail(String email);

  LocalDate getDateInscription();

  void setDateInscription(LocalDate dateInscription);

  String getStatut();

  void setStatut(String statut);

  /**
   * Renvoie l'objet courant sérialisé en json.
   *
   * @return le json
   */
  default String toJson() {
    return "{"
        + "\"id\":\"" + getId() + "\", "
        + "\"pseudo\":\"" + getPseudo() + "\", "
        + "\"lastName\":\"" + getNom() + "\", "
        + "\"firstName\":\"" + getPrenom() + "\", "
        + "\"email\":\"" + getEmail() + "\", "
        + "\"city\":\"" + getVille() + "\", "
        + "\"registrationDate\":\"" + getDateInscription() + "\", "
        + "\"status\":\"" + getStatut() + "\""
        + "}";
  }
}
