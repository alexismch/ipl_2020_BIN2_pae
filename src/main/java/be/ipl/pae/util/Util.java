package be.ipl.pae.util;

import be.ipl.pae.exceptions.MauvaiseClefException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.mindrot.bcrypt.BCrypt;

public class Util {

  private static final String JWTSECRET = "mystherbePAE";
  private static final Algorithm JWTALGORITHM = Algorithm.HMAC256(JWTSECRET);

  /**
   * Check if the string is not empty.
   *
   * @param string the string that you need to check
   * @return true if the string is not empty, otherwise false
   */
  public static boolean verifNonVide(String string) {
    return string != null && !"".equals(string);
  }

  /**
   * Check if the strings are not empty.
   *
   * @param strings the strings that you need to check
   * @return true if all the strings are not empty, false otherwise
   */
  public static boolean verifNonVide(String... strings) {
    for (String string : strings) {
      if (!verifNonVide(string)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Create a session key.
   *
   * @param ip ip of the request
   * @param id id of the user
   * @return the session key
   */
  public static String creerClef(String ip, int id) {
    return JWT.create().withIssuer("auth0").withClaim("ip", ip).withClaim("uId", id)
        .sign(JWTALGORITHM);
  }

  /**
   * Decode the key.
   *
   * @param key the key that you need to decode
   * @return the decoded key
   */
  public static DecodedJWT decoderClef(String key) {
    return JWT.require(JWTALGORITHM).withIssuer("auth0").build().verify(key);
  }

  /**
   * Check the decoded key with the ip and send the id of the user.
   *
   * @param decodedKey the decoded key
   * @param ip the ip that you need to check
   * @return the user's id
   */
  public static int recupererUId(DecodedJWT decodedKey, String ip) {
    if (!ip.equals(decodedKey.getClaim("ip").asString())) {
      throw new MauvaiseClefException("Mauvaise adresse IP");
    }
    return decodedKey.getClaim("uId").asInt();
  }

  /**
   * Decode the key, check the key with the ip, and send the user's id.
   *
   * @param key the key that you need to decode
   * @param ip the ip that you need to check
   * @return the user's id
   */
  public static int recuperUId(String key, String ip) {
    return recupererUId(decoderClef(key), ip);
  }

  /**
   * 
   * @param word the word that you need to check
   * @param maxSize the size that you don't want to surpass
   * @param regex the regex that word need to match
   * @return
   */
  public static boolean checkFormat(String word, int maxSize, String regex) {
    if (!verifNonVide(word))
      return false;
    if (word.length() > maxSize)
      return false;
    if (!word.matches(regex))
      return false;
    return true;
  }

  /**
   * 
   * @param word the word that you need to check
   * @param maxSize the size that you don't want to surpass
   * @return
   */
  public static boolean checkFormat(String word, int maxSize) {
    if (!verifNonVide(word))
      return false;
    if (word.length() > maxSize)
      return false;
    return true;
  }

  public static String cryptPwd(String pwd) {
    return BCrypt.hashpw(pwd, BCrypt.gensalt());
  }
}
