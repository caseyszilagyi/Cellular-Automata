package cell_society.backend.simulation_stepper;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.grid_styles.Grid;
import java.util.List;

/**
 * Stepper used to implement the foraging ants simulation
 * @author George Hong
 */
public class ForagingStepper extends SimulationStepper {

  private Grid simulationGrid;
  private int gridHeight;
  private int gridWidth;

  /**
   * Carries out all of the details to make a single step forward in the simulation
   */
  public void makeStep() {
    Grid nextGrid = new Grid(simulationGrid);
    nextGrid.updateRemainingPatches(simulationGrid);

    List<Coordinate> coordinateList = simulationGrid.getCoordinateUpdateList();
    doublePass(nextGrid, coordinateList, simulationGrid);

    simulationGrid = nextGrid;
  }

  /**
   * Passes through the grid twice, applying the logic to each cell
   * @param nextGrid The next grid that will be used
   * @param coordinateList The list of coordinates to check
   * @param simulationGrid The current grid
   */
  public static void doublePass(Grid nextGrid, List<Coordinate> coordinateList,
      Grid simulationGrid) {
    for (Coordinate coordinate : coordinateList) {
      int j = coordinate.getFirst();
      int k = coordinate.getSecond();
      Cell cell = simulationGrid.getCell(j, k);
      if (cell != null) {
        cell.performPrimaryAction(null, simulationGrid, nextGrid);
      }
    }

    for (Coordinate coordinate : coordinateList) {
      int j = coordinate.getFirst();
      int k = coordinate.getSecond();
      // Adjusts Ant and Nest Cells
      Cell cell = nextGrid.getCell(j, k);
      if (cell != null) {
        cell.performSecondaryAction(null, nextGrid, null);
      }
    }
  }


  /**
   * Gets the current Grid
   * @return the grid
   */
  public Grid getGrid() {
    return simulationGrid;
  }

  /**
   * Sets the grid to the specified grid
   * @param grid The grid
   */
  public void setGrid(Grid grid) {
    simulationGrid = grid;
    gridHeight = grid.getGridHeight();
    gridWidth = grid.getGridWidth();
  }
}
