package cell_society.backend;

import cell_society.backend.simulation_initializer.SimulationInitializer;
import cell_society.backend.automata.Grid;
import cell_society.backend.simulation_stepper.SimulationStepper;
import java.util.Map;

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
  private Map colorMappings;

  private final String SIMULATION_TYPE;
  private final String FILE_NAME;
  private String STEPPER_PATH;


  public Simulation(String simulationType, String fileName) {
    SIMULATION_TYPE = simulationType;
    FILE_NAME = fileName;
    initializeSimulation();
    STEPPER_PATH = "cell_society.backend.simulation_stepper." + simulationInitializer.getStepperType();
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
    colorMappings = simulationInitializer.getColorCodes();
  }

  /**
   * Initializes the stepper that loops through all the cells;
   */
  public void initializeStepper() {
    String stepperType = simulationInitializer.getStepperType();

    Class classStepper = null;
    try {
      classStepper = Class.forName(STEPPER_PATH);
    } catch (ClassNotFoundException e) {
      System.out
          .println("Error: Stepper class name does not exist or is placed in the wrong location");
    }

    //Casting the generic class to a stepper object
    simulationStepper = null;
    try {
      simulationStepper = (SimulationStepper) classStepper.newInstance();
    } catch (Exception e) {
      System.out.println("Error: Stepper casting");
    }

    simulationStepper.setGrid(simulationGrid);

  }

  public void makeStep() {
    simulationStepper.makeStep();
    simulationGrid = simulationStepper.getGrid();
  }


  public char[][] getGrid(){
    return simulationGrid.getDisplay();
  }

  //only for testing
  public Grid getRealGrid(){
    return simulationGrid;
  }

  public int getGridWidth(){
    return simulationGrid.getGridWidth();
  }

  public Map getColorMapping(){
    return colorMappings;
  }

  public int getGridHeight(){
    return simulationGrid.getGridHeight();
  }


  //For testing

  public static void main(String[] args) {
    Simulation mySim = new Simulation("game_of_life", "data/config_files/game_of_life/gameOfLife1.xml");

    mySim.initializeSimulation();
    Grid currGrid = mySim.getRealGrid();
    currGrid.printCurrentState();
    mySim.makeStep();
    mySim.getRealGrid().printCurrentState();
    mySim.makeStep();
    mySim.getRealGrid().printCurrentState();
    mySim.makeStep();
    mySim.getRealGrid().printCurrentState();
    char[][] griddy = mySim.getGrid();
    int i = 0;
  }


}
