package cell_society.backend.simulation_stepper;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.grid.Grid;

/**
 * Deals with each "step" of the simulation, which is done every time the simulation loop makes a
 * pass. This is the most basic form, that just loops through each cell once
 */
public class SimulationStepper {

  private Grid simulationGrid;

  public SimulationStepper(){
  }

  /**
   * Sets the grid for the stepper to use
   * @param grid The grid
   */
  public void setGrid(Grid grid){
    simulationGrid = grid;
  }


  /**
   * This will be the method that loops through the grid to update the cells
   */
  public void makeStep() {
    Grid nextGrid = new Grid(simulationGrid);
    for (int row = 0; row < simulationGrid.getGridHeight(); row++) {
      for (int col = 0; col < simulationGrid.getGridWidth(); col++) {
        Cell currCell = simulationGrid.getCell(row, col);
        if(currCell != null) {
          currCell.makeDecisions(currCell.getNeighbors(simulationGrid), nextGrid, simulationGrid);
        }
      }
    }
    simulationGrid = nextGrid;
  }

  public Grid getGrid(){
    return simulationGrid;
  }

}
