package cell_society.backend.automata;

import cell_society.backend.simulation_initializer.CellParameters;

public class Cell {

  private int row;
  private int col;

  public Cell() {

  }

  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public void initializeParams(CellParameters parameters) {

  }

  public void setPosition(int row, int col) {
    this.row = row;
    this.col = col;
  }

  protected int getRow() {
    return row;
  }

  protected int getCol() {
    return col;
  }


  /**
   * Filters the natural neighbors of the cell as desired.  Cells may choose to consider only
   * diagonal neighbors, adjacent neighbors, or all neighbors.
   *
   * @param grid grid holding the current configuration of cells
   * @return Neighbor object of Cells relevant to deciding the cell's next state.
   */
  public Neighbors getNeighbors(Grid grid) {
    return grid.getDirectNeighbors(row, col);
  }

  /**
   * Updates the next Grid state according to the rules obeyed by the Cell.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   */
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
  }

  /**
   * Allows for the inclusion of additional functionality that cannot be wrapped into the Primary
   * action.
   *
   * @param neighbors   Cells that this cell uses to make its decisions
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   */
  public void performSecondaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
  }

  /**
   * Method to allow for placing a nearly identical cell (with the exception of contained row and
   * coordinates) to a new location.
   *
   * @param row  new row index to place cell
   * @param col  new column index to place cell
   * @param grid grid to accept the cell object.
   */
  public void relocate(int row, int col, Grid grid) {
  }

  public boolean probeState(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    return true;
  }


  public String getGridRepresentation() {
    return "?";
  }
}
