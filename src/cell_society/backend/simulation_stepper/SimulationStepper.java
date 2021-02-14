package cell_society.backend.simulation_stepper;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;

/**
 * Deals with each "step" of the simulation, which is done every time the simulation loop makes a
 * pass
 */
public class SimulationStepper {

  Grid simulationGrid;

  public SimulationStepper(Grid userGrid) {
    simulationGrid = userGrid;
  }



  /**
   * This will be the method that loops through the grid to update the cells
   */
  public void makeStep() {
    Grid nextGrid = new Grid(simulationGrid.getGridHeight(), simulationGrid.getGridWidth());
    for (int row = 0; row < simulationGrid.getGridHeight(); row++) {
      for (int col = 0; col < simulationGrid.getGridWidth(); col++) {
        Cell currCell = simulationGrid.getCell(row, col);
        currCell.makeDecisions(currCell.getNeighbors(simulationGrid), nextGrid, simulationGrid);
      }
    }
    simulationGrid = nextGrid;
  }

  public Grid getGrid(){
    return simulationGrid;
  }

}
