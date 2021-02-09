package cellsociety.backend.SimulationInitializer;


/**
 * This is where all of the logic to initialize a simulation is contained. The XML file will be read
 * in, cells will be initialized, their neighbors will be determined, and the grid will be made.
 */
public class SimulationInitializer {

  private XMLFileReader xmlFileReader;

  public SimulationInitializer(){
  }

  public void readXMLFile(String userFile){
    xmlFileReader = new XMLFileReader(userFile);
  }


}
