package cell_society.backend.automata.foraging_ants;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_initializer.CellParameters;
import java.util.List;

/**
 * @author George Hong
 */
public class NestCell extends Cell {

  public static final String SPAWN_RATE = "spawnrate";
  public static final String SPAWN_TIMER = "spawntimer";
  private int spawnRate;
  private int spawnTimer;

  public NestCell(int row, int col, int spawnRate) {
    super(row, col);
    this.spawnTimer = 0;
    this.spawnRate = spawnRate;
  }

  public NestCell() {

  }

  @Override
  public void initializeParams(CellParameters parameters) {
    spawnRate = parameters.getAsInt(SPAWN_RATE);
    spawnTimer = parameters.getAsInt(SPAWN_TIMER);
  }

  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  /**
   * Spawn ants all around the radius if room is available in the next Grid
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
   * Allows ants with food to return home.  Delete qualifying ants from the grid.
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

  @Override
  public String getGridRepresentation() {
    return "N";
  }

  @Override
  public String toString() {
    return "NestCell";
  }

}
