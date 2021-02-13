package cell_society.spike;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.game_of_life.AliveCell;
import cell_society.backend.automata.game_of_life.DeadCell;
import cell_society.backend.automata.percolation.BlockedCell;
import cell_society.backend.automata.percolation.OpenCell;
import cell_society.backend.automata.percolation.PercolatedCell;

public class PercolationSample {
  public static void main(String[] args) {
    // 0: Blocked Cells, 1: Percolated Cells, 2: Open Cells
    int[][] initial = new int[][]{
        {0, 0, 2, 1},
        {0, 0, 0, 2},
        {0, 2, 2, 2},
        {0, 0, 2, 0}
    };
    int h = initial.length;
    int w = initial[0].length;
    int iteration = 0;
    Grid myGrid = initGrid(initial, h, w);

    // This is a crude substitute for the frontend's play feature
    while (true) {
      System.out.println("Iteration: " + iteration);
      myGrid.printCurrentState();
      myGrid = step(myGrid, h, w);
      waitForEnter();
      iteration++;
    }
  }


  /**
   * Reads the initial position into a grid.  This is a crude substitute for the Simulation or
   * Simulation initializer, depending on the design.
   *
   * @param configuration
   * @param height
   * @param width
   * @return
   */
  public static Grid initGrid(int[][] configuration, int height, int width) {
    // Initialize myGrid
    Grid grid = new Grid(height, width);
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        switch(configuration[j][k]){
          case 0:
            grid.placeCell(j, k, new BlockedCell(j, k));
            break;
          case 1:
            grid.placeCell(j, k, new PercolatedCell(j, k));
            break;
          case 2:
            grid.placeCell(j, k, new OpenCell(j, k));
            break;
        }
      }
    }
    return grid;
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
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        Cell cell = grid.getCell(j, k);
        Neighbors neighbors = cell.getNeighbors(grid);
        cell.makeDecisions(neighbors, nextGrid, null);
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
}
