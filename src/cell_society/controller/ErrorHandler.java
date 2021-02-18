package cell_society.controller;

/**
 * Simple error handler used to set the message that the display will show
 */
public class ErrorHandler extends RuntimeException{

  /**
   * Sets a message that getMessage can get in the display clasas
   * @param errorMessage The messagae
   */
  public ErrorHandler(String errorMessage){
    super(errorMessage);
  }

}
