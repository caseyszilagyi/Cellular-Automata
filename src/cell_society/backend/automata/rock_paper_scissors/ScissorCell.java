package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;

/**
 * Defines a Scissor play in extended rock-paper-scissors
 *
 * @author George Hong
 */
public class ScissorCell extends RPSCell {

  /**
   * Parameter-less constructor to initialize a ScissorCell with the XML Grid initializer
   */
  public ScissorCell() {

  }

  /**
   * Initializes an instance of Scissor Cell
   *
   * @param row       row index resided by this cell
   * @param col       column index resided by this cell
   * @param threshold minimum count of Rock Cells or Spock Cells for this ScissorCell to change
   *                  type.
   */
  public ScissorCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  /**
   * Checks the neighbors of this ScissorCell and sees whether a type change is required.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    super.performPrimaryAction(neighbors, currentGrid, nextGrid);
  }

  /**
   * Relocates this Scissor Cell to a new position or grid.
   *
   * @param row  new row index to place cell
   * @param col  new column index to place cell
   * @param grid grid to accept the cell object.
   */
  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new ScissorCell(row, col, getThreshold()));
  }

  @Override
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{new RockCell(), new SpockCell()};
  }

  /**
   * @return "X" due to the similar appearance to scissors
   * @deprecated returns the length-1 string representation of a Scissor Cell on a grid
   */
  @Override
  public String getGridRepresentation() {
    return "X";
  }

  /**
   * Returns the string representation of ScissorCell for use with the CellDecoder
   *
   * @return String "ScissorCell"
   */
  public String toString() {
    return "ScissorCell";
  }
}
