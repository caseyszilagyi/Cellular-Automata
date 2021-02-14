package cell_society.backend;

import cell_society.backend.simulation_initializer.SimulationInitializer;
import cell_society.backend.automata.Grid;
import cell_society.backend.simulation_stepper.SimulationStepper;

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
  private SimulationStepper simulationStepper;
  private Grid simulationGrid;

  private final String SIMULATION_TYPE;
  private final String FILE_NAME;


  public Simulation(String simulationType, String fileName) {
    SIMULATION_TYPE = simulationType;
    FILE_NAME = fileName;
    initializeSimulation();
    initializeStepper();
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
  }

  /**
   * Initializes the stepper that loops through all the cells;
   */
  public void initializeStepper() {
    simulationStepper = new SimulationStepper(simulationGrid);
  }


  public void makeStep() {
    simulationStepper.makeStep();
    simulationGrid = simulationStepper.getGrid();
  }

  public Grid getGrid(){
    return simulationGrid;
  }

  //For testing
  /*
  public static void main(String[] args) {
    Simulation mySim = new Simulation("Game of Life", "data/config_files/game_of_life/gameOfLife1.xml");
    mySim.initializeSimulation();
    Grid currGrid = mySim.getGrid();
    currGrid.printCurrentState();
    mySim.makeStep();
    mySim.getGrid().printCurrentState();
  }
   */

}
