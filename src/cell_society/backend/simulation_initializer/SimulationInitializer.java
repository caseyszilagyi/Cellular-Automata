package cell_society.backend.simulation_initializer;


import cell_society.backend.Simulation;
import cell_society.backend.automata.Grid;
import cell_society.backend.simulation_stepper.SimulationStepper;
import java.util.Map;

/**
 * This is where all of the logic to initialize a simulation is contained. The XML file will be read
 * in, cells will be initialized, their neighbors will be determined, and the grid will be made.
 *
 * @author Casey Szilagyi
 */
public class SimulationInitializer {

  private final String STEPPER_PATH = "cell_society.backend.simulation_stepper.";

  private XMLFileReader xmlFileReader;
  // All the general things about the simulation (title, author)
  private Map<String, String> simulationParameters;
  // What grid character corresponds to what cell
  private Map<String, String> cellCodes;
  // Converting the cell types back to codes (for passing to the display)
  private Map<String, String> cellDecoder;
  // Linkage between a specific code and its color
  private Map<Character, String> colorCodes;
  // the parameters that define a cell's behavior
  private CellParameters cellParameters;
  // creates the grid
  private GridCreator gridCreator;
  // can make a cell
  private CellCreator cellCreator;

  private String simulationType;
  private Grid simulationGrid;



  /**
   * Initializes the file reader.
   */
  public SimulationInitializer() {

    xmlFileReader = new XMLFileReader();
  }

  /**
   * Gets all of the parameters needed for the simulation that are contained in the XML file, and
   * makes hashmaps for them using the XMLFileReader class
   *
   * @param userSimulationType The type of simulation that we have, used to check if the file is
   *                           correct
   * @param fileName           The name of the file with the data for initialization
   */
  public void initializeSimulation(String userSimulationType, String fileName) {
    simulationType = userSimulationType;
    xmlFileReader.setSimulationType(simulationType);
    xmlFileReader.setFile(fileName);
    getMaps();
  }

  // Gets all of the maps that are used for various different purposes
  private void getMaps() {
    simulationParameters = xmlFileReader.getSimulationParameters();
    cellParameters = new CellParameters(xmlFileReader.getAttributeMap("parameters"));
    cellCodes = xmlFileReader.getAttributeMap("codes");
    cellDecoder = xmlFileReader.getReverseAttributeMap("codes");
    colorCodes = xmlFileReader.charMapConverter(xmlFileReader.getAttributeMap("colors"));
  }


  /**
   * Makes the grid that all the cells will be contained on
   *
   * @return The grid
   */
  public Grid makeGrid() {
    cellCreator = new CellCreator(simulationType, cellParameters);
    gridCreator = new GridCreator(Integer.parseInt(simulationParameters.get("rows")),
        Integer.parseInt(simulationParameters.get("columns")),
        simulationParameters.get("cellPackage"),
        simulationParameters.get("gridType"),
        cellCreator);
    gridCreator.populateGrid(simulationParameters.get("grid"), cellCodes);
    gridCreator.setColorCodes(colorCodes);
    gridCreator.setCellDecoder(cellDecoder);
    simulationGrid = gridCreator.getGrid();
    return simulationGrid;
  }


  /**
   * Initializes the stepper that loops through all the cells;
   */
  public SimulationStepper makeStepper() {
    String stepperType = simulationParameters.get("stepperType");

    Class classStepper = null;
    try {
      classStepper = Class.forName(STEPPER_PATH + stepperType);
    } catch (ClassNotFoundException e) {
      System.out
          .println("Error: Stepper class name does not exist or is placed in the wrong location");
    }

    //Casting the generic class to a stepper object
    SimulationStepper simulationStepper = null;
    try {
      simulationStepper = (SimulationStepper) classStepper.newInstance();
    } catch (Exception e) {
      System.out.println("Error: Stepper casting");
    }

    simulationStepper.setGrid(simulationGrid);

    return simulationStepper;
  }

  /**
   * Allows the Simulation class to get the color codes representing the cells
   *
   * @return The color coding map
   */
  public Map getColorCodes() {
    return colorCodes;
  }

  /**
   * Allows the Simulation class to get the stepper type in order to initialize the simulation
   * stepper
   *
   * @return
   */
  public String getStepperType() {
    return simulationParameters.get("stepperType");
  }

}

