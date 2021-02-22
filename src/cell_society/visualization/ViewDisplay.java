package cell_society.visualization;

import java.util.ResourceBundle;

/**
 * The ViewDisplay class is the superclass of both display view classes,
 * and contains methods that are shared between the grid & graph views
 *
 * @author Donghan Park
 */
public class ViewDisplay {

  private final int HORIZONTAL_BORDER_LENGTH = 40;
  private final int VERTICAL_BORDER_LENGTH = 125;

  private String currentSimType;
  private boolean isMinimized;

  public ViewDisplay(){
    currentSimType = null;
    isMinimized = true;
  }

  public void setCurrentSimType(String currentSimType){
    this.currentSimType = currentSimType;
  }

  public ResourceBundle getColorSheetResourceBundle(){
    String simColorCodesPackage = "cell_society/visualization/resources/properties/simulationColorCodes";
    return ResourceBundle.getBundle(String.format("%s/%s", simColorCodesPackage, currentSimType));
  }

  public void setIsMinimized(boolean isMinimized){
    this.isMinimized = isMinimized;
  }

  public boolean getIsMinimized() { return isMinimized; }

  public int getHorizontalBorderLength(){
    return HORIZONTAL_BORDER_LENGTH;
  }

  public int getVerticalBorderLength(){
    return VERTICAL_BORDER_LENGTH;
  }
}
