package cell_society.backend.automata.percolation;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.automata.Neighbors;

/**
 * Represents a blocked cell in the percolation simulation.
 * @author George Hong
 */
public class BlockedCell extends Cell {

  /**
   * Parameter-less constructor for initialization of the Cell with the XML reader.
   */
  public BlockedCell() {

  }

  /**
   * Initializes an instance of a Blocked Cell.
   * @param row row index of where this BlockedCell resides
   * @param col column index of where this BlockedCell resides
   */
  public BlockedCell(int row, int col) {
    super(row, col);
  }

  /**
   * The Blocked Cell does not need to identify any of its neighbors due to its static state, but
   * this can be called on for uniformity.
   *
   * @param grid grid holding the current configuration of cells
   * @return
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  /**
   * If a cell is blocked, it will always be blocked.  Therefore, update the nextGrid by placing an
   * identical, blocked cell.
   *  @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    int r = getRow();
    int c = getCol();
    BlockedCell blockedCell = new BlockedCell(r, c);
    nextGrid.placeCell(r, c, blockedCell);
  }

  /**
   * Used by the CellDecoder to generate an int representation of this Cell for the frontend
   * @return String "BlockedCell"
   */
  @Override
  public String toString() {
    return "BlockedCell";
  }

  /**
   * @Deprecated
   * Used with the early spike code console stepper for debugging
   * @return length 1 string to identify BlockedCells in the Grid.
   */
  @Override
  public String getGridRepresentation() {
    return "X";
  }
}
