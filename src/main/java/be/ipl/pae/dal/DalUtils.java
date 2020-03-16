package be.ipl.pae.dal;

public class DalUtils {

  public static String changeSpecialLikeChar(String s) {

    if (s == null) {
      return null;
    }

    return s.replace("_", "\\_")
        .replace("%", "\\%");

  }

}
