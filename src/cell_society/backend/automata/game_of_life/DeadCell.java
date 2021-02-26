package cell_society.backend.automata.game_of_life;


import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;
import java.util.List;

/**
 * The DeadCell represents the dead cell in Conway's Game of Life subject to the following rule:
 * <p>
 * 1. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 *
 * @author George Hong
 */
public class DeadCell extends Cell {

  /**
   * Constructs an instance of the DeadCell
   *
   * @param row row index of the DeadCell
   * @param col column index of the DeadCell
   */
  public DeadCell(int row, int col) {
    super(row, col);
  }

  /**
   * Parameter-less constructor for the DeadCell, intended for use with the XML Reader.
   */
  public DeadCell() {

  }

  /**
   * The DeadCell considers all direct neighbors when making its update decisions
   *
   * @param grid grid holding the current configuration of cells
   * @return Neighbors object containing all direct neighbors.
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = this.getRow();
    int col = this.getCol();
    return grid.getDirectNeighbors(row, col);
  }

  /**
   * Updates the next Grid state according to the rules obeyed by DeadCells: Any dead cell with
   * exactly three live neighbours becomes a live cell, as if by reproduction.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid Current state of the grid
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    int numLiveNeighbors = neighbors.getTypeCount(new AliveCell(-1, -1));
    int row = getRow();
    int col = getCol();
    if (numLiveNeighbors == 3) {
      AliveCell aliveCell = new AliveCell(row, col);
      nextGrid.placeCell(row, col, aliveCell);
    } else {
      DeadCell deadCell = new DeadCell(row, col);
      nextGrid.placeCell(row, col, deadCell);
    }

    padDeadCells(currentGrid, nextGrid, numLiveNeighbors, row, col);
  }

  private void padDeadCells(Grid currentGrid, Grid nextGrid, int numLiveNeighbors, int row,
      int col) {
    if (numLiveNeighbors > 0) {
      List<Coordinate> coordinates = currentGrid.getGridCellStructure()
          .getAllNeighboringCoordinates(row, col);
      for (Coordinate coordinate : coordinates) {
        int coordRow = coordinate.getFirst();
        int coordCol = coordinate.getSecond();
        if (nextGrid.inBoundaries(coordRow, coordCol) && nextGrid.isEmpty(coordRow, coordCol)) {
          nextGrid.placeCell(coordRow, coordCol, new DeadCell(coordRow, coordCol));
        }
      }
    }
  }


  /**
   * Returns string representation of this object, for use with the Cell Decoder
   *
   * @return returns String "DeadCell"
   */
  @Override
  public String toString() {
    return "DeadCell";
  }

  /**
   * @return String "_" representing an alive cell, representing an empty state for easier
   * view-ability.
   * @Deprecated Returns the length-1 String representation of this Cell for debugging purposes
   * through the console
   */
  @Override
  public String getGridRepresentation() {
    return "_";
  }
}
