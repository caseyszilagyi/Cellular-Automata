package cell_society.backend.automata.percolation;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

/**
 * Represents an open cell object from the Percolation Simulation.
 */
public class OpenCell extends Cell {

  public OpenCell() {

  }

  public OpenCell(int row, int col) {
    super(row, col);
  }

  /**
   * The OpenCell considers all neighboring blocks within a radius of 1 around its position on the
   * grid when making its state transitions.
   *
   * @param grid grid holding the current configuration of cells
   * @return
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  /**
   * If an open cell has at least one percolated neighbor, it will also become percolated.  If there
   * are no percolated neighbors, it will remain in the open state.  Therefore, update the nextGrid
   * to reflect this.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param nextGrid    grid to hold the next configuration of cells.
   * @param currentGrid
   */
  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    int numPercolatedNeighbors = neighbors.getTypeCount(new PercolatedCell(-1, -1));
    int r = getRow();
    int c = getCol();
    if (numPercolatedNeighbors > 0) {
      PercolatedCell percolatedCell = new PercolatedCell(r, c);
      nextGrid.placeCell(r, c, percolatedCell);
    } else {
      OpenCell openCell = new OpenCell(r, c);
      nextGrid.placeCell(r, c, openCell);
    }
  }

  @Override
  public String toString() {
    return "_";
  }

  @Override
  public String getGridRepresentation() {
    return "_";
  }
}
