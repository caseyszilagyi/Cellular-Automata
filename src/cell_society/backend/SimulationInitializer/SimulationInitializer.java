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
  private GridCreator gridCreator;

  public SimulationInitializer() {

    xmlFileReader = new XMLFileReader();
  }

  public void initializeSimulation(String simulationType, String fileName) {
    xmlFileReader.setSimulationType(simulationType);
    simulationParameters = xmlFileReader.getSimulationParameters(fileName);
    cellBehavior = xmlFileReader.getCellBehavior(fileName);
    makeGrid();
  }

  public Grid makeGrid(){
    gridCreator = new GridCreator(Integer.parseInt(simulationParameters.get("rows")),
        Integer.parseInt(simulationParameters.get("columns")),
        simulationParameters.get("grid"),
        xmlFileReader.getSimulationType());

    return gridCreator.getGrid();
  }

  public static void main(String[] args){
    SimulationInitializer mySim = new SimulationInitializer();
    mySim.initializeSimulation("gameOfLife", "data/config_files/testingFile.xml");

  }

}

