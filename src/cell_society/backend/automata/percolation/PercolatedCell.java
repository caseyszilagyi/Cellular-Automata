package cell_society.backend.automata.percolation;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;

/**
 * Represents a percolated cell object from the Percolation Simulation
 *
 * @author George Hong
 */
public class PercolatedCell extends Cell {

  /**
   * Parameter-less constructor for use with the XML Reader to place initial configuration of
   * Cells.
   */
  public PercolatedCell() {

  }

  /**
   * Creates an instance of a Percolated Cell
   * @param row row index where Percolated Cell resides
   * @param col column index where Percolated Cell resides
   */
  public PercolatedCell(int row, int col) {
    super(row, col);
  }

  /**
   * The Percolated Cell does not need to identify any of its neighbors due to its static state, but
   * this can be called on for uniformity.
   *
   * @param grid grid holding the current configuration of cells
   * @return Neighbors object of all directly neighboring cells.
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  /**
   * If a cell is percolated, it will always be percolated.  Therefore, update the nextGrid by
   * placing an identical, percolated cell.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    int r = getRow();
    int c = getCol();
    PercolatedCell percolatedCell = new PercolatedCell(r, c);
    nextGrid.placeCell(r, c, percolatedCell);
  }

  /**
   * Returns the String representation of this Cell's name, used by the Cell Decoder
   * @return String "PercolatedCell"
   */
  @Override
  public String toString() {
    return "PercolatedCell";
  }

  /**
   * @Deprecated
   * Used in conjunction with the now defunct Grid console-debugger.
   * @return length-1 String representation of a percolated cell
   */
  @Override
  public String getGridRepresentation() {
    return "O";
  }
}
