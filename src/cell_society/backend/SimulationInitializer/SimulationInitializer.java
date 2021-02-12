package cell_society.backend.SimulationInitializer;



import cell_society.backend.automata.Grid;
import java.util.Map;

/**
 * This is where all of the logic to initialize a simulation is contained. The XML file will be read
 * in, cells will be initialized, their neighbors will be determined, and the grid will be made.
 */
public class SimulationInitializer {

  private XMLFileReader xmlFileReader;
  private Map<String,String> simulationParameters;
  private Map<String,String> cellBehavior;
  private Map<String,String> cellCodes;
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
   * @param simulationType The type of simulation that we have, used to check if the file is correct
   * @param fileName The name of the file with the data for initialization
   */
  public void initializeSimulation(String simulationType, String fileName) {
    xmlFileReader.setSimulationType(simulationType);
    simulationParameters = xmlFileReader.getSimulationParameters(fileName);
    cellBehavior = xmlFileReader.getCellBehavior(fileName);
    cellCodes = xmlFileReader.getCellCodes(fileName);
  }

  /**
   * Makes the grid that all the cells will be contained on
   * @return The grid
   */
  public Grid makeGrid(){
    gridCreator = new GridCreator(Integer.parseInt(simulationParameters.get("rows")),
        Integer.parseInt(simulationParameters.get("columns")));
    gridCreator.setCellBehavior(cellBehavior);
    gridCreator.populateGrid(simulationParameters.get("grid"), cellCodes);

    return gridCreator.getGrid();
  }

  //For testinig
  public static void main(String[] args){
    SimulationInitializer mySim = new SimulationInitializer();
    mySim.initializeSimulation("gameOfLife", "data/config_files/testingFile.xml");
    mySim.makeGrid();
  }

}

