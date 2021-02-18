package cell_society.controller;

public class ErrorHandler extends RuntimeException{

  public ErrorHandler(String errorMessage){
    super(errorMessage);
  }

}
