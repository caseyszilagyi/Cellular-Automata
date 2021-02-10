package cell_society.backend.SimulationInitializer;



import java.util.Map;

/**
 * This is where all of the logic to initialize a simulation is contained. The XML file will be read
 * in, cells will be initialized, their neighbors will be determined, and the grid will be made.
 */
public class SimulationInitializer {

  private XMLFileReader xmlFileReader;
  private Map<String,String> simulationParameters;
  private Map<String,String> cellBehavior;

  public SimulationInitializer() {
    xmlFileReader = new XMLFileReader();
  }

  public void initializeSimulation(String simulationType, String fileName) {
    xmlFileReader.setSimulationType(simulationType);
     simulationParameters = xmlFileReader.getSimulationParameters(fileName);
     cellBehavior = xmlFileReader.getCellBehavior(fileName);
  }

  public static void main(String[] args){
    SimulationInitializer mySim = new SimulationInitializer();
    mySim.initializeSimulation("gameOfLife", "data/config_files/testingFile.xml");
  }

}

