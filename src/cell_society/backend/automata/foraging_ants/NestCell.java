package cell_society.backend.automata.foraging_ants;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_initializer.CellParameters;
import java.util.List;

/**
 * The NestCell is a Cell object responsible for spawning AntCells in the Foraging Ants simulation.
 *
 * @author George Hong
 */
public class NestCell extends Cell {

  public static final String SPAWN_RATE = "spawnrate";
  public static final String SPAWN_TIMER = "spawntimer";
  private int spawnRate;
  private int spawnTimer;

  /**
   * Creates an instance of the NestCell
   *
   * @param row       row index on the grid to place the NestCell
   * @param col       column index on the grid to place the NestCell
   * @param spawnRate number of steps before spawning more ants in the vicinity.
   */
  public NestCell(int row, int col, int spawnRate) {
    super(row, col);
    this.spawnTimer = 0;
    this.spawnRate = spawnRate;
  }

  /**
   * Parameter-less constructor to create an instance of the NestCell from the XML Reader.
   */
  public NestCell() {

  }

  @Override
  /**
   * Intended to be used with the parameter-less NestCell to set the internal state of the NestCell
   */
  public void initializeParams(CellParameters parameters) {
    spawnRate = parameters.getAsInt(SPAWN_RATE);
    spawnTimer = parameters.getAsInt(SPAWN_TIMER);
  }

  /**
   * The NestCell does not rely on its neighbors to make decisions.  Instead, it directly updates
   * other Cells residing in the grid with it.
   *
   * @param grid grid holding the current configuration of cells
   * @return Neighbors object containing all neighbors directly adjacent to this NestCell
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  /**
   * Spawn ants all around the radius if room is available in the next Grid and spawnRate steps have
   * occurred since the last spawning.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    if (spawnTimer % spawnRate == 0) {
      List<Coordinate> coordinateList = currentGrid.getGridCellStructure()
          .getAllNeighboringCoordinates(getRow(), getCol());
      for (Coordinate coordinate : coordinateList) {
        int row = coordinate.getFirst();
        int col = coordinate.getSecond();
        if (!nextGrid.inBoundaries(row, col)) {
          continue;
        }
        if (currentGrid.isEmpty(row, col) && nextGrid.isEmpty(row, col)) {
          AntCell antCell = new AntCell(row, col, nextGrid);
          nextGrid.placeCell(row, col, antCell);
        }
      }
    }
    NestCell nestCell = new NestCell(getRow(), getCol(), spawnRate);
    nestCell.spawnTimer = spawnTimer + 1;
    nextGrid.placeCell(getRow(), getCol(), nestCell);
  }

  /**
   * Allows ants with food to return home if they're directly neighboring this NestCell.
   *
   * @param neighbors   Cells that this cell uses to make its decisions
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   */
  @Override
  public void performSecondaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    List<Coordinate> coordinateList = currentGrid.getGridCellStructure()
        .getAllNeighboringCoordinates(getRow(), getCol());
    for (Coordinate coordinate : coordinateList) {
      int row = coordinate.getFirst();
      int col = coordinate.getSecond();
      if (!currentGrid.inBoundaries(row, col)) {
        continue;
      }
      Cell cell = currentGrid.getCell(row, col);
      // Check ant bringing back food.
      if (cell instanceof AntCell && cell.probeState(null, null, null)) {
        currentGrid.placeCell(row, col, null);
      }
    }
  }

  /**
   * @return String "N" representing nest
   * @Deprecated Returns a length 1 string representation of this Cell on a grid, for use in the
   * console
   */
  @Override
  public String getGridRepresentation() {
    return "N";
  }

  /**
   * Returns the String of this Class Name, for use with the Cell Decoder
   *
   * @return String "NestCell" to decode integer representations of cells for the frontend
   */
  @Override
  public String toString() {
    return "NestCell";
  }

}
