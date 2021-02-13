package cell_society.spike;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.percolation.BlockedCell;
import cell_society.backend.automata.percolation.OpenCell;
import cell_society.backend.automata.percolation.PercolatedCell;
import cell_society.backend.automata.wator.FishCell;
import cell_society.backend.automata.wator.SharkCell;
import cell_society.backend.automata.wator.ToroidalGrid;

public class WatorSample {
  public static void main(String[] args) {
    // 0: Blocked Cells, 1: Percolated Cells, 2: Open Cells
    int[][] initial = new int[][]{
        {0, 0, 0, 0, 0, 1},
        {2, 0, 0, 0, 1, 1},
        {2, 2, 0, 0, 1, 1},
        {2, 0, 0, 0, 0, 0}
    };
//    int[][] initial = new int[][]{
//        {1, 1, 1, 1, 1, 1},
//        {1, 1, 1, 1, 1, 1},
//        {1, 1, 1, 1, 2, 1},
//        {1, 1, 1, 1, 1, 1}
//    };
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
   * Demonstrates walking through a single step and the calls to each cell.
   *
   * @param grid
   * @param height
   * @param width
   */
  public static Grid step(Grid grid, int height, int width) {
    Grid nextGrid = new ToroidalGrid(height, width);
    // Move Fish first
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        Cell cell = grid.getCell(j, k);
        if (cell == null || !(cell instanceof FishCell)) continue;
        Neighbors neighbors = cell.getNeighbors(grid);
        cell.makeDecisions(neighbors, nextGrid, grid);
      }
    }
//    System.out.println("FISH MOVED:");
//    nextGrid.printCurrentState();

    // Move Sharks
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        Cell cell = grid.getCell(j, k);
        if (cell == null || !(cell instanceof SharkCell)) continue;
        Neighbors neighbors = cell.getNeighbors(grid);
        cell.makeDecisions(neighbors, nextGrid, grid);
      }
    }
//    System.out.println("SHARKS MOVED:");
//    nextGrid.printCurrentState();

    // Reproduction?
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        Cell cell = grid.getCell(j, k);
        //if (cell == null) continue;
        if (cell instanceof SharkCell){
          SharkCell shark = (SharkCell) cell;
          shark.reproduce(nextGrid);
        }
        else if (cell instanceof FishCell){
          FishCell fish = (FishCell) cell;
          fish.reproduce(nextGrid);
        }
      }
    }
    return nextGrid;
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
    Grid grid = new ToroidalGrid(height, width);
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        switch(configuration[j][k]){
          case 0:
            break;
          case 1:
            grid.placeCell(j, k, new FishCell(j, k, 0, 3));
            break;
          case 2:
            grid.placeCell(j, k, new SharkCell(j, k, 100, 5));
            break;
        }
      }
    }
    return grid;
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
