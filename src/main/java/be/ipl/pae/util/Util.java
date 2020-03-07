package be.ipl.pae.util;

import be.ipl.pae.exceptions.MauvaiseClefException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Util {

  private static final String JWTSECRET = "mystherbePAE";
  private static final Algorithm JWTALGORITHM = Algorithm.HMAC256(JWTSECRET);

  /**
   * Vérifie si la chaine n'est pas vide.
   *
   * @param chaine la chaine à vérifier
   * @return true si la chaine n'est pas vide, false si oui
   */
  public static boolean verifNonVide(String chaine) {
    return chaine != null && !"".equals(chaine);
  }

  /**
   * Vérifie si toutes les chaines ne sont pas vides.
   *
   * @param chaines les chaines à vérifier
   * @return true si toutes les chaines ne sont pas vides, false si oui
   */
  public static boolean verifNonVide(String... chaines) {
    for (String chaine : chaines) {
      if (!verifNonVide(chaine)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Crée une clef de session.
   *
   * @param ip l'ip de la requête
   * @param id l'id de l'utilisateur
   * @return la clef de session
   */
  public static String creerClef(String ip, int id) {
    return JWT.create()
        .withIssuer("auth0")
        .withClaim("ip", ip)
        .withClaim("uId", id)
        .sign(JWTALGORITHM);
  }

  /**
   * Décode la clef.
   *
   * @param clef la clef à décoder
   * @return la clé décodée
   */
  public static DecodedJWT decoderClef(String clef) {
    return JWT.require(JWTALGORITHM)
        .withIssuer("auth0")
        .build()
        .verify(clef);
  }

  /**
   * Vérifie la clef décodée avec l'ip, et renvoie l'id de l'utilisateur.
   *
   * @param clefDecodee la clef décodée
   * @param ip          l'ip à vérifier
   * @return l'id de l'utilisateur
   */
  public static int recupererUId(DecodedJWT clefDecodee, String ip) {
    if (!ip.equals(clefDecodee.getClaim("ip").asString())) {
      throw new MauvaiseClefException("Mauvaise adresse IP");
    }
    return clefDecodee.getClaim("uId").asInt();
  }

  /**
   * Décode la clef, vérifie la clef avec l'ip, et renvoie l'id de l'utilisateur.
   *
   * @param clef clef à décoder
   * @param ip   l'ip à vérifier
   * @return l'id de l'utilisateur
   */
  public static int recuperUId(String clef, String ip) {
    return recupererUId(decoderClef(clef), ip);
  }
}
