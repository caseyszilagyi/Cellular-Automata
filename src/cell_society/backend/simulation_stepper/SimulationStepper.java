package cell_society.backend.simulation_stepper;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;

/**
 * Deals with each "step" of the simulation, which is done every time the simulation loop makes a
 * pass
 */
public class SimulationStepper {

  public SimulationStepper() {
  }

  /**
   * This will be the method that loops through the grid to update the cells
   */
  public Grid makeStep(Grid grid) {
    Grid nextGrid = new Grid(grid.getGridHeight(), grid.getGridWidth());
    for (int row = 0; row < grid.getGridHeight(); row++) {
      for (int col = 0; col < grid.getGridWidth(); col++) {
        Cell currCell = grid.getCell(row, col);
        currCell.makeDecisions(currCell.getNeighbors(grid), nextGrid, grid);
      }
    }

    return nextGrid;
  }

}
