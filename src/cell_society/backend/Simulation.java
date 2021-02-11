package cell_society.backend;

import cell_society.backend.SimulationInitializer.SimulationInitializer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import cell_society.backend.automata.Grid;

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


  private final int FPS = 120;
  private final double SECOND_DELAY = 1.0 / FPS;
  private final String STAGE_TITLE = "Cell Society Simulation";
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
    simulationStepper = new SimulationStepper();
  }


  public Grid makeStep() {
    //simulationGrid = simulationStepper.makeStep(simulationGrid);
    return simulationGrid;
  }


}
