package cell_society.backend.automata.foraging_ants;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.grid_styles.Direction;
import cell_society.backend.automata.grid_styles.Grid;
import java.util.concurrent.ThreadLocalRandom;

public class AntCell extends Cell {

  private int food;
  private Direction antDirection;

  public AntCell(int row, int col, AntCell antCell) {
    super(row, col);
    antDirection = antCell.antDirection;
    food = antCell.food;
  }

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

  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  /**
   * The primary action of the ant is to move in one of two possible directions that it is facing.
   * If both positions are avilable, the position with the highest pheromone concentration is moved
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

  @Override
  public boolean probeState(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    return food > 0;
  }

  @Override
  public String getGridRepresentation() {
    return "A";
  }
}
