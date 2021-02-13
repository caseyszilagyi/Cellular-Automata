package cell_society.backend.simulation_initializer;


import cell_society.backend.automata.Grid;
import java.util.Map;

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
  // What grid character corresponds to what cell
  private Map<String, String> cellCodes;
  // the parameters that define a cell's behavior
  private CellParameters cellParameters;
  private GridCreator gridCreator;



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
   * @param simulationType The type of simulation that we have, used to check if the file is
   *                       correct
   * @param fileName       The name of the file with the data for initialization
   */
  public void initializeSimulation(String simulationType, String fileName) {
    xmlFileReader.setSimulationType(simulationType);
    simulationParameters = xmlFileReader.getSimulationParameters(fileName);
    cellParameters = new CellParameters(xmlFileReader.getCellBehavior(fileName));
    cellCodes = xmlFileReader.getCellCodes(fileName);
  }

  /**
   * Makes the grid that all the cells will be contained on
   *
   * @return The grid
   */
  public Grid makeGrid() {
    gridCreator = new GridCreator(Integer.parseInt(simulationParameters.get("rows")),
        Integer.parseInt(simulationParameters.get("columns")),
        simulationParameters.get("cellPackage"));
    gridCreator.setCellBehavior(cellParameters);
    gridCreator.populateGrid(simulationParameters.get("grid"), cellCodes);

    return gridCreator.getGrid();
  }

  //For testing
  public static void main(String[] args) {
    SimulationInitializer mySim = new SimulationInitializer();
    mySim.initializeSimulation("Game of Life", "data/config_files/testingFile.xml");
    Grid myGrid = mySim.makeGrid();
    myGrid.printCurrentState();
  }

}

