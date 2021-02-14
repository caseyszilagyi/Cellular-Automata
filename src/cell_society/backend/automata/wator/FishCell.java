package cell_society.backend.automata.wator;

import cell_society.backend.simulation_initializer.CellParameters;
import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Direction;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FishCell extends Cell {

  private int breedTimeCounter;
  private int breedTimeThresh;

  public int getBreedTimeCounter() {
    return breedTimeCounter;
  }

  public int getBreedTimeThresh() {
    return breedTimeThresh;
  }

  public FishCell(int row, int col, int breedTimeCounter, int breedTimeThresh) {
    super(row, col);
    this.breedTimeCounter = breedTimeCounter;
    this.breedTimeThresh = breedTimeThresh;
  }

  public FishCell() {

  }

  @Override
  public void initializeParams(CellParameters parameters) {
    super.initializeParams(parameters);
    breedTimeCounter = parameters.getAsInt("breedtimecounter");
    breedTimeThresh = parameters.getAsInt("breedtimethresh");
  }

  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  /**
   * Prey fish move randomly to free neighboring cells.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param nextGrid    grid to hold the next configuration of cells.
   * @param currentGrid
   */
  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    breedTimeCounter++;
    int row = getRow();
    int col = getCol();
    for (Coordinate coords : getRandAdjacent(row, col)) {
      int shiftedRow = coords.getFirst();
      int shiftedCol = coords.getSecond();
      // Both the current and target grid the fish wants to move to must be empty
      if (currentGrid.isEmpty(shiftedRow, shiftedCol) && nextGrid.isEmpty(shiftedRow, shiftedCol)) {
        FishCell fishCell = new FishCell(shiftedRow, shiftedCol, breedTimeCounter, breedTimeThresh);
        nextGrid.placeCell(shiftedRow, shiftedCol, fishCell);
        return;
      }
    }
    // No movable spots
    FishCell fishCell = new FishCell(row, col, breedTimeCounter, breedTimeThresh);
    nextGrid.placeCell(row, col, fishCell);
  }

  /**
   * Once the breed time is up a new fish spawns in a free neighboring cell and the parents breed
   * time is reset.
   * @param grid
   */
  public void reproduce(Grid grid) {
    if (breedTimeCounter >= breedTimeThresh) {
      for (Coordinate coords : getRandAdjacent(getRow(), getCol())) {
        int row = coords.getFirst();
        int col = coords.getSecond();
        if (grid.isEmpty(row, col)) {
          FishCell offspring = new FishCell(row, col, 0, breedTimeThresh);
          breedTimeCounter = 0;
          grid.placeCell(row, col, offspring);
          return;
        }
      }
    }
  }

  /**
   * Similar to identifying neighbors, this method aquires all possible adjacent corners as
   * Coordinates and returns it to the Cell as potential movement actions.  The list of coordinates
   * is shuffled, encapsulating the randomized behavior.
   *
   * @param row
   * @param col
   * @return
   */
  private List<Coordinate> getRandAdjacent(int row, int col) {
    List<Coordinate> returnCoordinates = new ArrayList<>();
    Direction[] direction = new Direction[]{Direction.TOP, Direction.LEFT, Direction.RIGHT,
        Direction.BOTTOM};
    List<Direction> directionList = Arrays.asList(direction);
    Collections.shuffle(directionList);
    for (Direction dir : directionList) {
      int shiftedRow = dir.applyToRow(row);
      int shiftedCol = dir.applyToCol(col);
      returnCoordinates.add(new Coordinate(shiftedRow, shiftedCol));
    }
    return returnCoordinates;
  }

  @Override
  public String toString() {
    return "F";
  }

  @Override
  public String getGridRepresentation() {
    return "F";
  }
}
