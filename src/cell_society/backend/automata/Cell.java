package cell_society.backend.automata;

import cell_society.backend.simulation_initializer.CellParameters;

public class Cell {

  private String cellID;
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
   * @param currentGrid
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
  }

  public void performSecondaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
  }

  public void relocate(int row, int col, Grid grid) {
  }

  public boolean probeState(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    return true;
  }


  public String getGridRepresentation() {
    return "?";
  }
}
