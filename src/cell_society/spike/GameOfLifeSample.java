package cell_society.spike;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.game_of_life.AliveCell;
import cell_society.backend.automata.game_of_life.DeadCell;

/**
 * This is purely an example file to run, made to demonstrate how the various classes of the
 * automata module interact.
 * <p>
 * To move through the simulation faster, hold down [ENTER].  Please give it a try if you like :)
 */
public class GameOfLifeSample {

  public static void main(String[] args) {
    // This is an oscillator.  Comment out the next method to see this one in action.
    // This is a crude substitute for the XML initial configuration
    int[][] initial = new int[][]{
        {0, 0, 0, 0, 0, 0},
        {0, 1, 1, 0, 0, 0},
        {0, 1, 1, 0, 0, 0},
        {0, 0, 0, 1, 1, 0},
        {0, 0, 0, 1, 1, 0},
        {0, 0, 0, 0, 0, 0}
    };
    initial = getGosperGunConfig();
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
        if (configuration[j][k] == 0) {
          grid.placeCell(j, k, new DeadCell(j, k));
        } else {
          grid.placeCell(j, k, new AliveCell(j, k));
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

  /**
   * Get a pretty cool cell configuration.
   * @return
   */
  public static int[][] getGosperGunConfig() {
    // I really wanted to see this in action.  I have a vim plugin, so it was not quite as bad as it looks.
    // Unfolding might be a pain, but this should hopefully not require any edits.
    int[][] initial = new int[][]{
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 1, 0},
        {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0}
    };
    return initial;
  }

}
