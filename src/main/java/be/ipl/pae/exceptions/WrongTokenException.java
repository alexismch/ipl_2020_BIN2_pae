package be.ipl.pae.exceptions;

public class WrongTokenException extends RuntimeException {

  public WrongTokenException(String message) {
    super(message);
  }

  public WrongTokenException() {
    super();
  }

  public WrongTokenException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public WrongTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  public WrongTokenException(Throwable cause) {
    super(cause);
  }
}
