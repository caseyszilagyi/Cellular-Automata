package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.grid_styles.Grid;

/**
 * Defines a Spock play in extended rock-paper-scissors
 *
 * @author George Hong
 */
public class SpockCell extends RPSCell {

  /**
   * Parameter-less constructor to initialize a SpockCell with the XML Grid initializer
   */
  public SpockCell() {

  }

  /**
   * Initializes an instance of Spock Cell
   *
   * @param row       row index resided by this cell
   * @param col       column index resided by this cell
   * @param threshold minimum count of Paper Cells or Lizard Cells for this SpockCell to change
   *                  type.
   */
  public SpockCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  @Override
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{new PaperCell(), new LizardCell()};
  }

  /**
   * Relocates this Spock Cell to a new position
   *
   * @param row  new row index to place cell
   * @param col  new column index to place cell
   * @param grid grid to accept the cell object.
   */
  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new SpockCell(row, col, getThreshold()));
  }

  /**
   * @return String "S" to represent Spock
   * @deprecated returns the length-1 string representation of a Spock cell on a grid
   */
  @Override
  public String getGridRepresentation() {
    return "S";
  }

  /**
   * Returns the string representation of SpockCell for use with the CellDecoder
   *
   * @return String "SpockCell"
   */
  public String toString() {
    return "SpockCell";
  }
}
