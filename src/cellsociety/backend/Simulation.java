package cellsociety.backend;

import cellsociety.backend.SimulationInitializer.SimulationInitializer;

/**
 * This class contains the game loop to run the simulation. It initializes the simulation, and
 * contains the loop that runs the simulation. This loop will make a step, check for errors, check
 * if the simulation is over (has reached an end state), and then pass the data to the front end
 * through a helper class
 *
 * @author Casey Szilagyi
 */
public class Simulation {

  private SimulationInitializer simulationInitializer;
  private String FILE_NAME = "testingFile.xml";


  public Simulation(){
    initializeSimulation();
    makeSimulationLoop();
  }

  public void initializeSimulation(){
    simulationInitializer = new SimulationInitializer();
    simulationInitializer.readXMLFile(FILE_NAME);
  }

  public void makeSimulationLoop(){

  }


}
