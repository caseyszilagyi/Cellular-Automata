package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;

/**
 * Defines a Rock play in extended rock-paper-scissors
 *
 * @author George Hong
 */
public class RockCell extends RPSCell {

  /**
   * Parameter-less constructor to initialize a RockCell with the XML Grid initializer
   */
  public RockCell() {

  }

  /**
   * Initializes an instance of Rock Cell
   *
   * @param row       row index resided by this cell
   * @param col       column index resided by this cell
   * @param threshold minimum count of Spock Cell or Paper Cells to change this type.
   */
  public RockCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  /**
   * Checks the neighbors of this RockCell and sees whether a type change is required
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
   * Relocates this Rock Cell to a new position or grid.
   *
   * @param row  new row index to place cell
   * @param col  new column index to place cell
   * @param grid grid to accept the cell object.
   */
  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new RockCell(row, col, getThreshold()));
  }

  @Override
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{new SpockCell(), new PaperCell()};
  }

  /**
   * @return String "R" for rock
   * @deprecated returns the length-1 string representation of a Rock Cell on a grid
   */
  @Override
  public String getGridRepresentation() {
    return "R";
  }

  /**
   * Returns the string representation of RockCell for use with the CellDecoder
   *
   * @return String "RockCell"
   */
  public String toString() {
    return "RockCell";
  }
}
