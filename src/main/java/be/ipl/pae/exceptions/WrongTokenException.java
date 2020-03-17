package be.ipl.pae.exceptions;

public class WrongTokenException extends RuntimeException {

  public WrongTokenException(String message) {
    super(message);
  }
}
