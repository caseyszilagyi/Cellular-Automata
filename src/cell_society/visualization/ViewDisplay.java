package cell_society.visualization;

import java.util.ResourceBundle;

/**
 * The ViewDisplay class is the superclass of both display view classes
 * and contains methods that are shared between the grid & graph views.
 *
 * @author Donghan Park
 */
public class ViewDisplay {

  private final int HORIZONTAL_BORDER_LENGTH = 40;
  private final int VERTICAL_BORDER_LENGTH = 125;
  private final String SIM_COLOR_CODE_PACKAGE = "cell_society/visualization/resources/properties/simulationColorCodes";

  private String currentSimType;
  private boolean isMinimized;

  /**
   * Constructor that creates an instance of the ViewDisplay object
   */
  public ViewDisplay(){
    currentSimType = null;
    isMinimized = true;
  }

  /**
   * Sets the simulation type of the current simulation being run
   * @param currentSimType Name of simulation type of the current simulation being run
   */
  public void setCurrentSimType(String currentSimType){
    this.currentSimType = currentSimType;
  }

  /**
   * Returns the property file containing the color information for the current simulation being run
   * @return The property file containing the color information for the current simulation being run
   */
  public ResourceBundle getColorSheetResourceBundle(){
    return ResourceBundle.getBundle(String.format("%s/%s", SIM_COLOR_CODE_PACKAGE, currentSimType));
  }

  /**
   * Sets the boolean of whether or not the display views should be minimized
   * @param isMinimized Boolean of whether or not the display views should be minimized
   */
  public void setIsMinimized(boolean isMinimized){
    this.isMinimized = isMinimized;
  }

  /**
   * Returns a boolean of whether or not the display views should be minimized
   * @return Boolean of whether or not the display views should be minimized
   */
  public boolean getIsMinimized() { return isMinimized; }

  /**
   * Returns the horizontal border length to center the display views horizontally
   * @return The horizontal border length
   */
  public int getHorizontalBorderLength(){
    return HORIZONTAL_BORDER_LENGTH;
  }

  /**
   * Returns the vertical border length to center the display views vertically
   * @return The vertical border length
   */
  public int getVerticalBorderLength(){
    return VERTICAL_BORDER_LENGTH;
  }
}
