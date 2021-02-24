package cell_society.backend.automata.foraging_ants;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.grid_styles.Direction;
import cell_society.backend.automata.grid_styles.Grid;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Simulates an ant in the Foraging Ants simulation.  This class assumes that only one Ant can
 * occupy a spot on the grid at a time.
 *
 * @author George Hong
 */
public class AntCell extends Cell {

  public static final String FOOD = "food";
  private int food;
  private Direction antDirection;

  /**
   * Constructs an instance of the ant object, simulating the transition of a previous ant cell to
   * the new spot.
   *
   * @param row     row index occupied by the ant cell
   * @param col     column index occupied by the ant cell
   * @param antCell other ant whose food and direction properties are intended to be copied over
   */
  public AntCell(int row, int col, AntCell antCell) {
    super(row, col);
    antDirection = antCell.antDirection;
    food = antCell.food;
  }

  /**
   * Constructs a new isntance of the ant object, initializing it with a random direction from the
   * grid.
   *
   * @param row  row index to be occupied by the ant cell
   * @param col  column index to be occupied by the ant cell
   * @param grid grid which the ant cell will reside in.  Used to determine the initial direction
   *             that the ant is facing.
   */
  public AntCell(int row, int col, Grid grid) {
    super(row, col);
    food = 0;
    antDirection = grid.getGridCellStructure().getRandomDirection(row, col);
  }

  private static boolean weightedRandom(int weight1, int weight2) {
    // Returns true for option1, false for option2
    int sum = weight1 + weight2;
    int randInt = ThreadLocalRandom.current().nextInt(0, sum + 1);
    return randInt <= weight1;
  }

  /**
   * Allows the ant to retrieve its neighbors.  This method is not used because ants purely depend
   * on the patch states to determine movement.
   *
   * @param grid grid holding the current configuration of cells
   * @return
   */

  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  /**
   * The primary action of the ant is to move in one of two possible directions that it is facing.
   * If both positions are available, the position with the highest pheromone concentration is moved
   * to.  Otherwise, the ant remains in the same position and rotates to a new direction to try
   * during the next round.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    // Find food mode
    int row = getRow();
    int col = getCol();
    // Assess movement options
    Patch patchCW = null;
    Coordinate coordCW = null;
    Patch patchCCW = null;
    Coordinate coordCCW = null;

    // Check valid direction to move to.
    if (validDirection(row, col, antDirection.rotateCW(), currentGrid, nextGrid)) {
      coordCW = antDirection.rotateCW().getResultingCoordinate(row, col);
      patchCW = nextGrid.getPatch(coordCW.getFirst(), coordCW.getSecond());
    }
    if (validDirection(row, col, antDirection.rotateCCW(), currentGrid, nextGrid)) {
      coordCCW = antDirection.rotateCCW().getResultingCoordinate(row, col);
      patchCCW = nextGrid.getPatch(coordCCW.getFirst(), coordCCW.getSecond());
    }
    int newRow;
    int newCol;
    if (coordCW == null && coordCCW == null) {
      // Ant stays in place, rotates slightly
      AntCell antCell = new AntCell(row, col, this);
      antCell.antDirection = antDirection.rotateCW();
      nextGrid.placeCell(row, col, antCell);
      return;
    }
    if (coordCCW == null ^ coordCW == null) {
      newRow = coordCCW == null ? coordCW.getFirst() : coordCCW.getFirst();
      newCol = coordCCW == null ? coordCW.getSecond() : coordCCW.getSecond();
      AntCell antCell = new AntCell(newRow, newCol, this);
      nextGrid.placeCell(newRow, newCol, antCell);
      return;
    }
    // Decide on coordinate to move to if both are available
    int foodPheromoneCW = patchCW.getState(AntPatch.FOOD_PHEROMONE_LEVEL);
    int foodPheromoneCCW = patchCCW.getState(AntPatch.FOOD_PHEROMONE_LEVEL);
    newRow = weightedRandom(foodPheromoneCW, foodPheromoneCCW) ? coordCW.getFirst()
        : coordCCW.getFirst();
    newCol = weightedRandom(foodPheromoneCW, foodPheromoneCCW) ? coordCW.getSecond()
        : coordCCW.getSecond();

    AntCell antCell = new AntCell(newRow, newCol, this);
    nextGrid.placeCell(newRow, newCol, antCell);
  }

  private boolean validDirection(int row, int col, Direction direction, Grid currentGrid,
      Grid nextGrid) {
    // A valid cell direction choice if a cell can move to it.
    Coordinate result = direction.getResultingCoordinate(row, col);
    return nextGrid.inBoundaries(result) && currentGrid.isEmpty(result) && nextGrid.isEmpty(result);
  }

  /**
   * Once the ant is on a new patch, the new patch has the ability to influence the ant's behavior.
   * By calling this method, the ant attempts to acquire food from the patch.  Afterwards, the ant
   * has the ability to influence the pheromone levels of the patch it's standing over.  An ant
   * leaves 8x the pheromones on a patch if it's carrying food.
   *
   * @param neighbors   Cells that this cell uses to make its decisions
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   */
  @Override
  public void performSecondaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    Patch patch = currentGrid.getPatch(getRow(), getCol());
    // Acquire food?
    if (patch.getState(AntPatch.FOOD_LEVEL) > 0 && food == 0) {
      food = 1;
      // Ant rapidly adjusts location in an attempt to go back
      antDirection = currentGrid.getGridCellStructure().getRandomDirection(getRow(), getCol());
    }

    // Pheromone adjustments
    int currentLevel = patch.getState(AntPatch.FOOD_PHEROMONE_LEVEL);
    if (food > 0) {
      // Ants searching leave home pheromones
      patch.setState(AntPatch.FOOD_PHEROMONE_LEVEL, currentLevel + 8);
    } else {
      // Ants going home leave food pheromones
      patch.setState(AntPatch.FOOD_PHEROMONE_LEVEL, currentLevel + 1);
    }
  }

  /**
   * Determines whether the ant is currently carrying food
   *
   * @param neighbors   Cells that this cell uses to make its decisions
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   * @return boolean representing whether the ant is carrying food.
   */
  @Override
  public boolean probeState(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    return food > 0;
  }

  /**
   * @Deprecated
   * Returns a length 1 string that is compact to aid in debugging in the console
   * @return String representing an ant, ("A")
   */
  @Override
  public String getGridRepresentation() {
    return "A";
  }

  /**
   * Helper method to aid in the decoding of cells to the frontend.
   * @return String containing this AntCell's class name.
   */
  @Override
  public String toString() {
    return "AntCell";
  }
}
