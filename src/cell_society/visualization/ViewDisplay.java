package cell_society.visualization;

import cell_society.backend.Simulation;
import java.util.ResourceBundle;

/**
 * @author Donghan Park
 */
public class ViewDisplay {

  private Simulation currentSim;
  private String currentSimType;

  private final int HORIZONTAL_BORDER_LENGTH = 50;
  private final int VERTICAL_BORDER_LENGTH = 90;

  public ViewDisplay(){
    currentSim = null;
    currentSimType = null;
  }

  public void setCurrentSimInfo(Simulation currentSim, String currentSimType){
    this.currentSim = currentSim;
    this.currentSimType = currentSimType;
  }

  public Simulation getCurrentSim(){
    return currentSim;
  }

  public ResourceBundle getColorSheetResourceBundle(){
    String simColorCodesPackage = "cell_society/visualization/resources/properties/simulationColorCodes";
    return ResourceBundle.getBundle(String.format("%s/%s", simColorCodesPackage, currentSimType));
  }

  public int getHorizontalBorderLength(){
    return HORIZONTAL_BORDER_LENGTH;
  }

  public int getVERTICAL_BORDER_LENGTH(){
    return VERTICAL_BORDER_LENGTH;
  }
}
