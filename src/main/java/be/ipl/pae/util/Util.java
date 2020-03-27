package be.ipl.pae.util;

import be.ipl.pae.biz.objets.QuoteState;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.exceptions.WrongTokenException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import org.mindrot.bcrypt.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {

  private static final String JWTSECRET = "mystherbePAE";
  private static final Algorithm JWTALGORITHM = Algorithm.HMAC256(JWTSECRET);

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
   * Create a session key.
   *
   * @param ip ip of the request
   * @param id id of the user
   * @return the session key
   */
  public static String createToken(String ip, int id) {
    return JWT.create().withIssuer("auth0").withClaim("ip", ip).withClaim("uId", id)
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
   * Check the decoded key with the ip and send the id of the user.
   *
   * @param decodedKey the decoded key
   * @param ip         the ip that you need to check
   * @return the user's id
   */
  public static int getUId(DecodedJWT decodedKey, String ip) {
    if (!ip.equals(decodedKey.getClaim("ip").asString())) {
      throw new WrongTokenException("Mauvaise adresse IP");
    }
    return decodedKey.getClaim("uId").asInt();
  }

  /**
   * Decode the key, check the key with the ip, and send the user's id.
   *
   * @param key the key that you need to decode
   * @param ip  the ip that you need to check
   * @return the user's id
   */
  public static int getUId(String key, String ip) {
    return getUId(decodeToken(key), ip);
  }

  /**
   * check if the word that you give has the format that you want.
   *
   * @param word    the word that you need to check
   * @param maxSize the size that you don't want to surpass
   * @param regex   the regex that word need to match
   * @return true if the word has a good format otherwise false
   */
  public static boolean checkFormat(String word, int maxSize, String regex) {
    if (!verifyNotEmpty(word)) {
      return false;
    }
    if (word.length() > maxSize) {
      return false;
    }
    return word.matches(regex);
  }

  /**
   * check if the word that you give has the format that you want.
   *
   * @param word    the word that you need to check
   * @param maxSize the size that you don't want to surpass
   * @return true if the word has a good format otherwise false
   */
  public static boolean checkFormat(String word, int maxSize) {
    if (!verifyNotEmpty(word)) {
      return false;
    }
    return word.length() <= maxSize;
  }

  public static String cryptPwd(String pwd) {
    return BCrypt.hashpw(pwd, BCrypt.gensalt());
  }

  /**
   * For debug purpose: return a string form an InputStream.
   *
   * @param inputStream InputStream that will be converted to string
   * @return String UTF-8 with the inputStream content
   * @throws IOException If error with inputStream
   */
  public static String convertInputStreamToString(InputStream inputStream) throws IOException {

    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    while ((length = inputStream.read(buffer)) != -1) {
      result.write(buffer, 0, length);
    }

    return result.toString(StandardCharsets.UTF_8.name());

  }

  /**
   * Create a Genson Builder.
   *
   * @return the Genson Builder
   */
  public static GensonBuilder createGensonBuilder() {
    GensonBuilder gensonBuilder =
        new GensonBuilder().exclude("password").useMethods(true).useRuntimeType(true);

    Util.addSerializer(gensonBuilder, LocalDate.class,
        (value, writer, ctx) -> writer.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE)));

    Util.addSerializer(gensonBuilder, UserStatus.class,
        (value, writer, ctx) -> writer.writeName("status").beginObject()
            .writeString("id", value.toString()).writeString("name", value.getName()).endObject());

    Util.addSerializer(gensonBuilder, QuoteState.class,
        (value, writer, ctx) -> writer.writeName("state").beginObject()
            .writeString("id", value.toString()).writeString("title", value.getTitle())
            .endObject());

    return gensonBuilder;
  }

  public static <T> void addSerializer(GensonBuilder builder, Class<T> type,
      SerializerConverter<T> converter) {
    builder.withConverter(converter, type);
  }

  public static <T> void addDeserializer(GensonBuilder builder, Class<T> type,
      DeserializerConverter<T> converter) {
    builder.withConverter(converter, type);
  }

  @FunctionalInterface
  public interface SerializerConverter<T> extends Converter<T> {

    @Override
    default T deserialize(ObjectReader reader, Context ctx) {
      return null;
    }

  }

  @FunctionalInterface
  public interface DeserializerConverter<T> extends Converter<T> {

    @Override
    default void serialize(T object, ObjectWriter writer, Context ctx) {
    }

  }

}
