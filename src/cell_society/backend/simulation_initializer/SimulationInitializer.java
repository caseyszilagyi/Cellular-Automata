package cell_society.backend.simulation_initializer;

import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_stepper.SimulationStepper;
import java.util.Map;

/**
 * This is where all of the logic to initialize a simulation is contained. The XML file will be read
 * in, cells will be initialized, their neighbors will be determined, and the grid will be made.
 *
 * @author Casey Szilagyi
 */
public class SimulationInitializer {

  private String STEPPER_LOCATION = "cell_society.backend.simulation_stepper.";
  private String GRID_LOCATION = "cell_society.backend.automata.grid.";
  private String CELL_LOCATION;
  private String PACKAGE_LOCATION = "cell_society.backend.automata.";

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
  private SimulationClassLoader classLoader;

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
    classLoader = new SimulationClassLoader(userSimulationType);
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
        cellParameters, classLoader);
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
    SimulationStepper myStepper = classLoader.makeStepper(simulationParameters.get("stepperType"));
    myStepper.setGrid(simulationGrid);
    return myStepper;
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

