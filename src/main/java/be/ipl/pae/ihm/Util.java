package be.ipl.pae.ihm;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.objets.UserStatus;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.mindrot.bcrypt.BCrypt;

public class Util {

  private static String JWTSECRET;
  private static Algorithm JWTALGORITHM;

  /**
   * Set JWTSECRET and JWTALGORITHM.
   *
   * @param jwt the scret key
   */
  public static void setJwt(String jwt) {
    JWTSECRET = jwt;
    JWTALGORITHM = Algorithm.HMAC256(JWTSECRET);
  }

  /**
   * Check if the string is not empty.
   *
   * @param string the string that you need to check
   * @return true if the string is not empty, otherwise false
   */
  public static boolean verifyNotEmpty(String string) {
    return string != null && !"".equals(string);
  }

  /**
   * Check if the strings are not empty.
   *
   * @param strings the strings that you need to check
   * @return true if all the strings are not empty, false otherwise
   */
  public static boolean verifyNotEmpty(String... strings) {
    for (String string : strings) {
      if (!verifyNotEmpty(string)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if the table is not empty.
   *
   * @param objects the table that you need to check
   * @return true if the table is not empty, otherwise false
   */
  public static boolean verifyNotEmpty(Object[] objects) {
    return objects != null && objects.length > 0;
  }

  /**
   * Check if the objects are not empty.
   *
   * @param objects the objects that you need to check
   * @return true if all the objects are not empty, false otherwise
   */
  public static boolean verifyNotEmpty(Object[]... objects) {
    for (Object[] object : objects) {
      if (!verifyNotEmpty(object)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if the objects have the same length.
   *
   * @param objects the objects that you need to check
   * @return true if all the objects have the same length, false otherwise
   */
  public static boolean verifySameLength(Object[]... objects) {
    int length = objects[0].length;
    for (Object[] object : objects) {
      if (object.length != length) {
        return false;
      }
    }
    return true;
  }

  /**
   * Verify if all elements of arrayB are inside arrayA.
   *
   * @param arrayA the 1st Array
   * @param arrayB the 2nd Array
   * @return true if all elements of arrayB are inside arrayA, false if not
   */
  public static boolean isAllInside(Object[] arrayA, Object[] arrayB) {
    if (!arrayA.getClass().equals(arrayB.getClass())) {
      return false;
    }
    for (Object o : arrayB) {
      if (!isInside(arrayA, o)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Verify if object is inside array.
   *
   * @param array  the Array
   * @param object the object to verify
   * @return true if object is inside array, false if not
   */
  public static boolean isInside(Object[] array, Object object) {
    if (!array[0].getClass().equals(object.getClass())) {
      return false;
    }
    for (Object o : array) {
      if (o.equals(object)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Create a session key.
   *
   * @param userDto the user that asked the session
   * @return the session key
   */
  public static String createToken(UserDto userDto) {
    return JWT.create().withIssuer("auth0")
        .withClaim("uId", userDto.getId())
        .withClaim("uStatus", userDto.getStatus().getCode())
        .sign(JWTALGORITHM);
  }

  /**
   * Decode the key.
   *
   * @param key the key that you need to decode
   * @return the decoded key
   */
  public static DecodedJWT decodeToken(String key) {
    return JWT.require(JWTALGORITHM).withIssuer("auth0").build().verify(key);
  }

  /**
   * Check the decoded key and send the id of the user.
   *
   * @param decodedToken the decoded key
   * @return the user's id
   */
  public static int getUId(DecodedJWT decodedToken) {
    return decodedToken.getClaim("uId").asInt();
  }

  /**
   * Decode the token, check the token and send the user's id.
   *
   * @param token the token that you need to decode
   * @return the user's id
   */
  public static int getUId(String token) {
    return getUId(decodeToken(token));
  }

  /**
   * Check the decoded key and send the state of the user.
   *
   * @param decodedToken the decoded key
   * @return the user's state
   */
  public static String getUStatus(DecodedJWT decodedToken) {
    return decodedToken.getClaim("uStatus").asString();
  }

  /**
   * Decode the token, check the token and send the user's state.
   *
   * @param token the token that you need to decode
   * @return the user's state
   */
  public static String getUStatus(String token) {
    return getUStatus(decodeToken(token));
  }

  /**
   * Verify if the access is autorized.
   *
   * @param token        the session token
   * @param statusNeeded the status needed to access
   * @return true if access is autorized, false if not
   */
  public static boolean hasAccess(String token, UserStatus statusNeeded) {
    System.out.println("\tUsed token : " + token);
    if (token == null) {
      return false;
    }
    try {
      UserStatus status = UserStatus.getStatusByCode(getUStatus(token));

      return hasAccess(statusNeeded, status);
    } catch (Exception ex) {
      return false;
    }
  }

  /**
   * Verify if the accessGiven is higher or equals than statusNeeded.
   *
   * @param statusNeeded the status needed to access
   * @param statusGiven  the status given
   * @return true if accessGiven is higher or equals than statusNeeded
   */
  public static boolean hasAccess(UserStatus statusNeeded, UserStatus statusGiven) {
    switch (statusNeeded) {
      case CUSTOMER:
        if (statusGiven.equals(UserStatus.CUSTOMER)) {
          return true;
        }
        //fall through
      case WORKER:
        if (statusGiven.equals(UserStatus.WORKER)) {
          return true;
        }
        //fall through
      default:
        return false;
    }
  }

  public static String cryptPwd(String pwd) {
    return BCrypt.hashpw(pwd, BCrypt.gensalt());
  }

}
