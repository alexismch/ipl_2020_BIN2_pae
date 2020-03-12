package be.ipl.pae.main;

import config.InjectionService;

public class Main {

  public static void main(String[] args) throws Exception {
    Serveur serveur = new Serveur();
    InjectionService injectionService = new InjectionService();
    injectionService.chargerProperties("dependance.properties");
    injectionService.injecter(serveur);
    serveur.demarrer();
  }
}
