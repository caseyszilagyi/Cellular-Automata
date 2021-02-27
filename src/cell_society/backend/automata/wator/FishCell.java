package cell_society.backend.automata.wator;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.simulation_initializer.CellParameters;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Fish in the WaTor simulation
 *
 * @author George Hong
 */
public class FishCell extends Cell {

  public static final String BREED_TIME_COUNTER = "breedtimecounter";
  public static final String BREED_TIME_THRESH = "breedtimethresh";
  private int breedTimeCounter;
  private int breedTimeThresh;

  /**
   * Creates an instance of FishCell
   *
   * @param row              row index resided by this FishCell
   * @param col              column index resided by this FishCell
   * @param breedTimeCounter current amount of time this FishCell has survived for
   * @param breedTimeThresh  amount of time needed for this FishCell to reproduce.
   */
  public FishCell(int row, int col, int breedTimeCounter, int breedTimeThresh) {
    super(row, col);
    this.breedTimeCounter = breedTimeCounter;
    this.breedTimeThresh = breedTimeThresh;
  }

  /**
   * Empty constructor for use with the XML loader
   */
  public FishCell() {

  }

  /**
   * Gets the current BreedTimeCounter
   *
   * @return int representing how long this FishCell has survived for
   */
  public int getBreedTimeCounter() {
    return breedTimeCounter;
  }

  /**
   * Gets the current BreedTimeThresh
   *
   * @return int representing how many time intervals before this Fish reproduces
   */
  public int getBreedTimeThresh() {
    return breedTimeThresh;
  }

  /**
   * Initializes the FishCell with the desired properties, intended for use with the empty
   * constructor
   *
   * @param parameters parameters object containing Agent properties
   */
  @Override
  public void initializeParams(CellParameters parameters) {
    super.initializeParams(parameters);
    breedTimeCounter = parameters.getAsInt(BREED_TIME_COUNTER);
    breedTimeThresh = parameters.getAsInt(BREED_TIME_THRESH);
  }

  /**
   * The FishCell considers all direct neighbors, but does not need this class to make decisions
   *
   * @param grid grid holding the current configuration of cells
   * @return neighbors object containing all neighbors of this FishCell
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  /**
   * Prey fish move randomly to free neighboring cells.  Increments the breed time counter.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid Current configuration of cells
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
      if (!currentGrid.inBoundaries(shiftedRow, shiftedCol)) {
        continue;
      }
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
   * @param neighbors   neighbors object containing all neighbors of this FishCell
   * @param currentGrid currentGrid resided in by this Cell, used to directly spawn fish and to
   *                    perform boundary checks
   * @param nextGrid    unused by this method
   */
  public void performSecondaryAction(Neighbors neighbors,
      Grid currentGrid, Grid nextGrid) {
    if (breedTimeCounter >= breedTimeThresh) {
      for (Coordinate coords : getRandAdjacent(getRow(), getCol(), currentGrid)) {
        int row = coords.getFirst();
        int col = coords.getSecond();
        if (!currentGrid.inBoundaries(row, col)) {
          continue;
        }
        if (currentGrid.isEmpty(row, col)) {
          FishCell offspring = new FishCell(row, col, 0, breedTimeThresh);
          breedTimeCounter = 0;
          currentGrid.placeCell(row, col, offspring);
          return;
        }
      }
    }
  }

  /*
   * Similar to identifying neighbors, this method acquires all possible adjacent corners as
   * Coordinates and returns it to the Cell as potential movement actions.  The list of coordinates
   * is shuffled, encapsulating the randomized behavior.
   */
  private List<Coordinate> getRandAdjacent(int row, int col,
      Grid grid) {
    List<Coordinate> returnCoordinates = grid.getGridCellStructure()
        .getAllAdjacentCoordinates(row, col);
    Collections.shuffle(returnCoordinates);
    return returnCoordinates;
  }

  /**
   * Returns the String name of this FishCell, for use with decoding
   *
   * @return String FishCell
   */
  @Override
  public String toString() {
    return "FishCell";
  }

  /**
   * @return "F" for FishCell
   * @deprecated Returns the length-1 string for displaying this cell in the console
   */
  @Override
  public String getGridRepresentation() {
    return "F";
  }
}
