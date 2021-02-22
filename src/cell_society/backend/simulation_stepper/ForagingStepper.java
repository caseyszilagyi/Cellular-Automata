package cell_society.backend.simulation_stepper;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.grid_styles.Grid;
import java.util.List;

public class ForagingStepper extends SimulationStepper {

  private Grid simulationGrid;
  private int gridHeight;
  private int gridWidth;

  public void makeStep() {
    Grid nextGrid = new Grid(simulationGrid);
    nextGrid.updateRemainingPatches(simulationGrid);

    List<Coordinate> coordinateList = simulationGrid.getCoordinateUpdateList();
    doublePass(nextGrid, coordinateList, simulationGrid);

    simulationGrid = nextGrid;
  }

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

  public Grid getGrid() {
    return simulationGrid;
  }

  public void setGrid(Grid grid) {
    simulationGrid = grid;
    gridHeight = grid.getGridHeight();
    gridWidth = grid.getGridWidth();
  }
}
