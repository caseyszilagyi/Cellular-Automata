package cell_society.spike.console_runs;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.automata.grid_styles.ToroidalGrid;
import cell_society.backend.automata.sugar_scape.SugarAgentCell;
import cell_society.backend.automata.sugar_scape.SugarPatch;
import java.util.List;

public class SugarScapeSample {

  public static void main(String[] args) {
    int[][] cellConfig = new int[][]{
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0},
        {12, 12, 0, 0, 0, 0},
        {12, 12, 0, 0, 0, 0}
    };
    int[][] patchConfig = new int[][]{
        {2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2},
        {2, 2, 2, 2, 2, 2}
    };
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
    Grid nextGrid = new ToroidalGrid(height, width);
    // Transfer Patch States
    nextGrid.updateRemainingPatches(grid);
    // Uncomment Following for standard procedures
//    for (int j = 0; j < height; j++) {
//      for (int k = 0; k < width; k++) {
//        Cell cell = grid.getCell(j, k);
//        Patch patch = nextGrid.getPatch(j, k);
//        patch.applyUpdateRule();
//        if (cell != null){
//          cell.performPrimaryAction(null, grid, nextGrid);
//        }
//      }
//    }
    List<Coordinate> coordinateList = grid.getCoordinateUpdateList();
    for (Coordinate coordinate : coordinateList) {
      int j = coordinate.getFirst();
      int k = coordinate.getSecond();
      Cell cell = grid.getCell(j, k);
      Patch patch = nextGrid.getPatch(j, k);
      if (patch != null) {
        patch.applyUpdateRule();
      }
      if (cell != null) {
        cell.performPrimaryAction(null, grid, nextGrid);
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
    Grid grid = new ToroidalGrid(height, width);
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        if (!(cellConfig[j][k] == 0)) {
          grid.placeCell(j, k, new SugarAgentCell(j, k, 2, 8, 1));
        }
        grid.placePatch(j, k, new SugarPatch(patchConfig[j][k], 2, 2));
      }
    }
    return grid;
  }
}
