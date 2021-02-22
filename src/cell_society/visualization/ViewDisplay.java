package cell_society.visualization;

import cell_society.backend.Simulation;
import java.util.ResourceBundle;

/**
 * @author Donghan Park
 */
public class ViewDisplay {

  private String currentSimType;
  private boolean isMinimized;

  private final int HORIZONTAL_BORDER_LENGTH = 50;
  private final int VERTICAL_BORDER_LENGTH = 90;

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
