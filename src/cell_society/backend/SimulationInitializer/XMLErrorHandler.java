package cell_society.backend.SimulationInitializer;

import java.io.Serial;

/**
 * This class represents what might go wrong when using XML files.
 *
 * @author Robert C. Duvall
 */
public class XMLErrorHandler extends RuntimeException {

  // for serialization
  @Serial
  private static final long serialVersionUID = 1L;


  /**
   * Create an exception based on an issue in our code.
   */
  public XMLErrorHandler(String message, Object... values) {
    super(String.format(message, values));
  }

  /**
   * Create an exception based on a caught exception with a different message.
   */
  public XMLErrorHandler(Throwable cause, String message, Object... values) {
    super(String.format(message, values), cause);
  }

  /**
   * Create an exception based on a caught exception, with no additional message.
   */
  public XMLErrorHandler(Throwable cause) {
    super(cause);
  }
}