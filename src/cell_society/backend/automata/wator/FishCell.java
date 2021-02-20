package cell_society.backend.automata.wator;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.simulation_initializer.CellParameters;
import java.util.Collections;
import java.util.List;

public class FishCell extends Cell {

  private int breedTimeCounter;
  private int breedTimeThresh;

  public FishCell(int row, int col, int breedTimeCounter, int breedTimeThresh) {
    super(row, col);
    this.breedTimeCounter = breedTimeCounter;
    this.breedTimeThresh = breedTimeThresh;
  }

  public FishCell() {

  }

  public int getBreedTimeCounter() {
    return breedTimeCounter;
  }

  public int getBreedTimeThresh() {
    return breedTimeThresh;
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
   * @param currentGrid
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    breedTimeCounter++;
    int row = getRow();
    int col = getCol();
    for (Coordinate coords : getRandAdjacent(row, col, currentGrid)) {
      int shiftedRow = coords.getFirst();
      int shiftedCol = coords.getSecond();
      if (!currentGrid.inBoundaries(shiftedRow, shiftedCol)) continue;
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
   * The secondary action of the fish is to reproduce, if able.
   * <p>
   * Once the breed time is up a new fish spawns in a free neighboring cell and the parents breed
   * time is reset.
   *
   * @param neighbors
   * @param currentGrid
   * @param nextGrid
   */
  public void performSecondaryAction(Neighbors neighbors,
      Grid currentGrid, Grid nextGrid) {
    if (breedTimeCounter >= breedTimeThresh) {
      for (Coordinate coords : getRandAdjacent(getRow(), getCol(), currentGrid)) {
        int row = coords.getFirst();
        int col = coords.getSecond();
        if (!currentGrid.inBoundaries(row, col)) continue;
        if (currentGrid.isEmpty(row, col)) {
          FishCell offspring = new FishCell(row, col, 0, breedTimeThresh);
          breedTimeCounter = 0;
          currentGrid.placeCell(row, col, offspring);
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
   * @param grid
   * @return
   */
  private List<Coordinate> getRandAdjacent(int row, int col,
      Grid grid) {
    List<Coordinate> returnCoordinates = grid.getGridCellStructure()
        .getAllAdjacentCoordinates(row, col);
    Collections.shuffle(returnCoordinates);
    return returnCoordinates;
  }

  @Override
  public String toString() {
    return "FishCell";
  }

  @Override
  public String getGridRepresentation() {
    return "F";
  }
}
