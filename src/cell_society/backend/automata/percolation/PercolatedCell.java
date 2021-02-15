package cell_society.backend.automata.percolation;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

/**
 * Represents a percolated cell object from the Percolation Simulation
 */
public class PercolatedCell extends Cell {

  public PercolatedCell() {

  }

  public PercolatedCell(int row, int col) {
    super(row, col);
  }

  /**
   * The Percolated Cell does not need to identify any of its neighbors due to its static state, but
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
   * If a cell is percolated, it will always be percolated.  Therefore, update the nextGrid by
   * placing an identical, percolated cell.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param nextGrid    grid to hold the next configuration of cells.
   * @param currentGrid
   */
  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    int r = getRow();
    int c = getCol();
    PercolatedCell percolatedCell = new PercolatedCell(r, c);
    nextGrid.placeCell(r, c, percolatedCell);
  }

  @Override
  public String toString() {
    return "PercolatedCell";
  }

  @Override
  public String getGridRepresentation() {
    return "O";
  }
}
