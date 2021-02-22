package cell_society.backend;

import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_initializer.SimulationInitializer;
import cell_society.backend.simulation_stepper.SimulationStepper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class contains the game loop to run the simulation. It initializes the simulation, and
 * contains the loop that runs the simulation. This loop will make a step, check for errors, check
 * if the simulation is over (has reached an end state), and then pass the data to the front end
 * through a helper class
 *
 * @author Casey Szilagyi
 */
public class Simulation {

  private final String SIMULATION_TYPE;
  private final String FILE_NAME;
  private SimulationInitializer simulationInitializer;
  private SimulationStepper simulationStepper;
  private Grid simulationGrid;
  private Map<String, double[]> simulationParameters;


  public Simulation(String simulationType, String fileName) {
    SIMULATION_TYPE = simulationType;
    FILE_NAME = "data/config_files/" + simulationType + "/" + fileName;
    initializeSimulation();
  }

  /**
   * Gives the simulation initializer the game type and file to be parsed for the starting
   * configuration. Also makes the grid that will be used and gets it back to be used in this
   * class.
   */
  public void initializeSimulation() {
    simulationInitializer = new SimulationInitializer();
    simulationInitializer.initializeSimulation(SIMULATION_TYPE, FILE_NAME);
    simulationGrid = simulationInitializer.makeGrid();
    simulationStepper = simulationInitializer.makeStepper();
  }

  /**
   * Makes a step in the simulation
   */
  public void makeStep() {
    simulationStepper.makeStep();
    simulationGrid = simulationStepper.getGrid();
    getParameters();
  }

  /**
   * The int array that has the shape, height, width, and all the encodings for the cells to be
   * passed to the front end
   * @return The array of integers
   */
  public int[] getGridDisplay() {
    return simulationGrid.getIntDisplay();
  }

  /**
   * Gets the amount of each cell type, coded as an int
   * @return map of cell code to frequency
   */
  public Map<Integer, Integer> getCellDistribution() {
    return simulationGrid.getCellDistribution();
  }

  public void storeAsXMLFile(){

  }

  public Map getParameters(){
    return simulationInitializer.getFrontEndParameterSpecifications();
  }

  public void setParameters(HashMap<String, String> userValues){

  }

}
