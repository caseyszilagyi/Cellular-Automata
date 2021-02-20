package cell_society.backend.automata.percolation;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.automata.Neighbors;

/**
 * Represents a blocked cell in the percolation simulation.
 */
public class BlockedCell extends Cell {

  public BlockedCell() {

  }

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

  @Override
  public String toString() {
    return "BlockedCell";
  }

  @Override
  public String getGridRepresentation() {
    return "X";
  }
}
