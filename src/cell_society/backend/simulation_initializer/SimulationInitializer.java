package cell_society.backend.simulation_initializer;

import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_stepper.SimulationStepper;
import java.util.Map;
import java.util.Set;

/**
 * This is where all of the logic to initialize a simulation is contained. The XML file will be read
 * in, cells will be initialized, their neighbors will be determined, and the grid will be made.
 *
 * @author Casey Szilagyi
 */
public class SimulationInitializer {

  private XMLFileReader xmlFileReader;
  // All the general things about the simulation (title, author)
  private Map<String, String> simulationParameters;
  // Types of grid, stepper, etc to use
  private Map<String, String> coreSpecifications;
  // creates the grid
  private GridCreator gridCreator;

  private Map<String, double[]> parameterInformation;

  private String simulationType;
  private Grid simulationGrid;
  private SimulationClassLoader classLoader;

  private Set<GridOrPatchConfigurationSetup> patchDetails;

  private String defaultParametersPath;

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


  private Set<GridOrPatchConfigurationSetup> makePatchDetails(){
    Set<GridOrPatchConfigurationSetup> details = xmlFileReader.getPatchDetails();
    return details;
  }

  // Gets all of the maps that are used for various different purposes
  private void getMaps() {
    simulationParameters = xmlFileReader.getSimulationParameters();
    coreSpecifications = xmlFileReader.getAttributeMap("coreSpecifications");
    patchDetails = makePatchDetails();
  }

  /**
   * Makes the grid that all the cells will be contained on
   *
   * @return The grid
   */
  public Grid makeGrid() {
    GridOrPatchConfigurationSetup gridDetails = xmlFileReader.getGridDetails();
    gridCreator = new GridCreator(coreSpecifications.get("cellPackage"),
        new CellParameters(gridDetails.getParameters()), classLoader);
    gridCreator.makeGrid(gridDetails.getGridHeight(), gridDetails.getGridWidth(), coreSpecifications.get("gridType"));
    gridCreator.populateGrid(gridDetails.getGrid(), gridDetails.getCodes());
    gridCreator.setCellDecoder(gridDetails.getDecoder());
    gridCreator.setCellStructure(coreSpecifications.get("structureType"));
    if(patchDetails.size() != 0) {
      gridCreator.placePatches(patchDetails, coreSpecifications.get("patchType"));
    }
    simulationGrid = gridCreator.getGrid();
    parameterInformation = gridDetails.getFrontEndParameterSpecifications();
    return simulationGrid;
  }


  /**
   * Initializes the stepper that loops through all the cells;
   */
  public SimulationStepper makeStepper() {
    SimulationStepper myStepper = classLoader.makeStepper(coreSpecifications.get("stepperType"));
    myStepper.setGrid(simulationGrid);
    return myStepper;
  }

  public Map getFrontEndParameterSpecifications(){
    return parameterInformation;
  }

}

