package cell_society.backend.simulation_stepper;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.wator.FishCell;
import cell_society.backend.automata.wator.SharkCell;
import cell_society.backend.automata.grid_styles.ToroidalGrid;

/**
 * Stepper used to implement the wator simulation
 *
 * @author George Hong
 */
public class WatorStepper extends SimulationStepper {

  private Grid simulationGrid;
  private int gridHeight;
  private int gridWidth;

  public WatorStepper() {

  }

  /**
   * Carries out all of the details to make a single step forward in the simulation
   */
  public void makeStep() {
    Grid nextGrid = new ToroidalGrid(simulationGrid);
    // Move Fish first
    selectiveLoop(nextGrid, new FishCell());
    //System.out.println("S1:");
    //nextGrid.printCurrentState();
    // Move Sharks
    selectiveLoop(nextGrid, new SharkCell());
    //System.out.println("S2:");
    //nextGrid.printCurrentState();

    // Reproduction
    applyReproduce(nextGrid);
  }

  // Reproduces animals, if necessary
  private void applyReproduce(Grid nextGrid) {
    for (int j = 0; j < gridHeight; j++) {
      for (int k = 0; k < gridWidth; k++) {
        Cell cell = nextGrid.getCell(j, k);
        if (cell == null) {
          continue;
        }
        cell.performSecondaryAction(null, nextGrid, null);
      }
    }
    simulationGrid = nextGrid;
  }

  /**
   * Passes through the entire grid, but only updates the chosen type of Cell
   *
   * @param nextGrid   Grid to apply updates
   * @param chosenCell Cell type that will not be filtered from calling its makeDecisions method.
   */
  private void selectiveLoop(Grid nextGrid, Cell chosenCell) {
    for (int j = 0; j < gridHeight; j++) {
      for (int k = 0; k < gridWidth; k++) {
        Cell cell = simulationGrid.getCell(j, k);
        if (cell == null || !(cell.getClass() == chosenCell.getClass())) {
          continue;
        }
        Neighbors neighbors = cell.getNeighbors(simulationGrid);
        cell.performPrimaryAction(neighbors, simulationGrid, nextGrid);
      }
    }
  }

  /**
   * Sets the grid to the specified grid
   *
   * @param grid The grid
   */
  public void setGrid(Grid grid) {
    simulationGrid = grid;
    gridHeight = grid.getGridHeight();
    gridWidth = grid.getGridWidth();
  }

  /**
   * Gets the current Grid
   *
   * @return the grid
   */
  public Grid getGrid() {
    return simulationGrid;
  }

}
