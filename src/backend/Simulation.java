package backend;

/**
 * This class contains the game loop to run the simulation. It initializes the simulation, and
 * contains the loop that runs the simulation. This loop will make a step, check for errors, check
 * if the simulation is over (has reached an end state), and then pass the data to the front end
 * through a helper class
 *
 * @author Casey Szilagyi
 */
public class Simulation {

  SimulationInitializer simulationInitializer = new SimulationInitializer();

  public Simulation(){
    initializeSimulation();
    makeSimulationLoop();
  }

  public void initializeSimulation(){
    simulationInitializer.readXMLFile();
  }

  public void makeSimulationLoop(){

  }


}
