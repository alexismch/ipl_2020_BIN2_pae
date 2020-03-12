package be.ipl.pae.main;

import config.InjectionService;


public class Main {

  /**
   * Point d'entrée de l'application.
   * 
   * @param args tableau vide
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    Serveur serveur = new Serveur();
    InjectionService injectionService = new InjectionService();
    injectionService.chargerProperties("dependance.properties");
    injectionService.injecter(serveur);
    serveur.demarrer();
  }
}
