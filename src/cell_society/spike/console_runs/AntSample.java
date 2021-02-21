package cell_society.spike.console_runs;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.foraging_ants.AntPatch;
import cell_society.backend.automata.foraging_ants.NestCell;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.automata.grid_styles.ToroidalGrid;
import cell_society.backend.automata.sugar_scape.SugarAgentCell;
import cell_society.backend.automata.sugar_scape.SugarPatch;
import java.util.List;

public class AntSample {

  public static void main(String[] args) {
    int[][] cellConfig = new int[][]{
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    };
    int[][] patchConfig = new int[][]{
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
//    int[][] cellConfig = new int[][]{
//        {0, 0},
//        {0, 1},
//    };
//    int[][] patchConfig = new int[][]{
//        {0, 0},
//        {0, 0},
//    };
    int h = cellConfig.length;
    int w = cellConfig[0].length;
    int iteration = 0;
    Grid myGrid = initGrid(cellConfig, patchConfig, h, w);

    // This is a crude substitute for the frontend's play feature
    while (true) {
      System.out.println("Iteration: " + iteration);
      //myGrid.printCurrentState();
      myGrid.printCurrentState(0, 0, h, w);
      myGrid.printCurrentPatchState(0, 0, h, w);
      myGrid = step(myGrid, h, w);
      waitForEnter();
      iteration++;
    }
  }

  /**
   * Demonstrates walking through a single step and the calls to each cell.
   *
   * @param grid
   * @param height
   * @param width
   */
  public static Grid step(Grid grid, int height, int width) {
    Grid nextGrid = new Grid(height, width);
    nextGrid.updateRemainingPatches(grid);

    List<Coordinate> coordinateList = grid.getCoordinateUpdateList();
    for (Coordinate coordinate : coordinateList) {
      int j = coordinate.getFirst();
      int k = coordinate.getSecond();
      Cell cell = grid.getCell(j, k);
      if (cell != null) {
        cell.performPrimaryAction(null, grid, nextGrid);
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
    return nextGrid;
  }

  /**
   * Press [ENTER] to continue stepping through simulation.  To step faster, hold [ENTER]
   */
  public static void waitForEnter() {
    try {
      System.in.read();
    } catch (Exception e) {
    }
  }

  /**
   * Reads the initial position into a grid.  This is a crude substitute for the Simulation or
   * Simulation initializer, depending on the design.
   *
   * @param cellConfig
   * @param height
   * @param width
   * @return
   */
  public static Grid initGrid(int[][] cellConfig, int[][] patchConfig, int height, int width) {
    // Initialize myGrid
    Grid grid = new Grid(height, width);
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        if (cellConfig[j][k] == 1) {
          grid.placeCell(j, k, new NestCell(j, k, 5));
        }
        grid.placePatch(j, k, new AntPatch(patchConfig[j][k], 0));
      }
    }
    return grid;
  }
}
