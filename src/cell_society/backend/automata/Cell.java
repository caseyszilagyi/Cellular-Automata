package cell_society.backend.automata;

import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_initializer.CellParameters;

/**
 * Cell object that populates the Grid.  Cells can represent Grid States or can be implemented to
 * act as Agents that move around the grid, with individual properties.
 *
 * @author George Hong
 */
public abstract class Cell {

  private int row;
  private int col;

  public Cell() {

  }

  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Initialize the Cell with the parameters object, read from the configuration file.
   *
   * @param parameters parameters object containing Agent properties
   */
  public void initializeParams(CellParameters parameters) {

  }

  /**
   * Allows the cell to track its position on the grid, required to carry out certain actions.
   *
   * @param row
   * @param col
   */
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
   * diagonal neighbors, adjacent neighbors, or all neighbors.  This method can also be used to
   * filter out null neighbors.
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

  /**
   * Method that can be implemented to reveal cell state.
   *
   * @param neighbors   Cells that this cell uses to make its decisions
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   * @return
   */
  public boolean probeState(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    return false;
  }

  /**
   * Helper method to graphically display this cell in the console
   * @return question mark, representing a cell with no behavior.
   */
  public String getGridRepresentation() {
    return "?";
  }
}
