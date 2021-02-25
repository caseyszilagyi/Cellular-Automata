package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.grid_styles.Grid;

/**
 * Defines a Lizard play in extended rock-paper-scissors
 *
 * @author George Hong
 */
public class LizardCell extends RPSCell {

  /**
   * Parameter-less constructor to initialize a LizardCell with the XML Grid initializer
   */
  public LizardCell() {

  }

  /**
   * Initializes an instance of Lizard Cell
   *
   * @param row       row index resided by this cell
   * @param col       column index resided by this cell
   * @param threshold minimum count of Rocks or Scissor Cells required to change this Cell's type.
   */
  public LizardCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  /**
   * Checks the neighbors of this LizardCell and sees whether a type flip is required.
   *
   * @param row  new row index to place cell
   * @param col  new column index to place cell
   * @param grid grid to accept the cell object.
   */
  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new LizardCell(row, col, getThreshold()));
  }

  @Override
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{new RockCell(), new ScissorCell()};
  }

  /**
   * @return String "L" for Lizard
   * @deprecated returns the length-1 string representation of a Lizard Cell on a grid
   */
  @Override
  public String getGridRepresentation() {
    return "L";
  }

  /**
   * Returns the string representation of a LizardCell for use with the CellDecoder
   *
   * @return String "LizardCell"
   */
  public String toString() {
    return "LizardCell";
  }
}
