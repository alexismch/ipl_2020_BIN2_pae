package be.ipl.pae.util;

public class Util {

  public static boolean verifNonVide(String chaine) {
    return chaine == null || "".equals(chaine);
  }

  public static boolean verifNonVide(String... chaines) {
    for (String chaine : chaines) {
      if (!verifNonVide(chaine)) {
        return false;
      }
    }
    return true;
  }
}
