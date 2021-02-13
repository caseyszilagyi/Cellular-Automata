package cell_society.spike;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.segregation.AgentCell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SegregationSample {

  public static void main(String[] args) {
    // This is an oscillator.  Comment out the next method to see this one in action.
    // This is a crude substitute for the XML initial configuration
//    int[][] initial = new int[][]{
//        {0, 0, 0, 2, 0, 0},
//        {0, 1, 1, 0, 0, 0},
//        {0, 1, 1, 0, 0, 0},
//        {0, 0, 0, 1, 1, 0},
//        {0, 0, 2, 1, 1, 0},
//        {0, 0, 0, 0, 0, 0}
//    };
    int[][] initial = new int[][]{
        {1, 2, 1, 2},
        {2, 1, 2, 1},
        {1, 2, 1, 2},
        {2, 1, 2, 1}
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
   * Demonstrates walking through a single step and the calls to each cell. During the first pass of
   * the grid, satisfied neighbors are placed into the next location.
   *
   * @param grid
   * @param height
   * @param width
   */
  public static Grid step(Grid grid, int height, int width) {
    Grid nextGrid = new Grid(height, width);
    Queue<Cell> dissatisfiedQueue = new LinkedList<>();
    List<Coordinate> vacantCoordinates = grid.getAllVacantSpots();

    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        Cell cell = grid.getCell(j, k);
        // Cell is already empty, already factored through grid.getAllVacantSpots method
        if (cell == null) {
          continue;
        }
        Neighbors neighbors = cell.getNeighbors(grid);
        cell.makeDecisions(neighbors, nextGrid, null);
        // Alright, there's the potential that the agent is unhappy.  In that case, queue up for relocation
        AgentCell agentCell = (AgentCell) cell;
        if (!agentCell.isSatisfied(neighbors)){
          dissatisfiedQueue.add(agentCell);
          vacantCoordinates.add(new Coordinate(j, k));
        }
      }
    }
    // redistribute dissatisfied individuals.  Note, there's the potential here that a Cell will be placed in its current position.  Within the
    while (!dissatisfiedQueue.isEmpty()){
      AgentCell cell = (AgentCell) dissatisfiedQueue.poll();
      Collections.shuffle(vacantCoordinates);
      Coordinate newSpot = vacantCoordinates.get(0);
      vacantCoordinates.remove(0);
      int r = newSpot.getFirst();
      int c = newSpot.getSecond();
      nextGrid.placeCell(r, c, new AgentCell(r, c, cell.getSatisfactionProp(), cell.getAgentType()));
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
    Grid grid = new Grid(height, width);
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        switch (configuration[j][k]) {
          case 0:
            break;
          case 1:
            grid.placeCell(j, k, new AgentCell(j, k, 0.5, "X"));
            break;
          case 2:
            grid.placeCell(j, k, new AgentCell(j, k, 0.5, "O"));
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + configuration[j][k]);
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
