package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;

/**
 * Defines a Paper play in extended rock-paper-scissors
 *
 * @author George Hong
 */
public class PaperCell extends RPSCell {

  /**
   * Parameter-less constructor to initialize a PaperCell with the XML Grid initializer
   */
  public PaperCell() {

  }

  /**
   * Initializes an instance of PaperCell
   *
   * @param row       row index resided by this cell
   * @param col       column index resided by this cell
   * @param threshold minimum count of ScissorCells or LizardCells to change this type
   */
  public PaperCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  /**
   * Checks the neighbors of this PaperCell and sees whether a type change is required
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
   * Relocates this Paper Cell to a new position or grid.
   *
   * @param row  new row index to place cell
   * @param col  new column index to place cell
   * @param grid grid to accept the cell object.
   */
  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new PaperCell(row, col, getThreshold()));
  }

  @Override
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{new ScissorCell(), new LizardCell()};
  }

  /**
   * @return "P" for paper
   * @deprecated returns the length-1 string representation of a Paper Cell on a grid
   */
  @Override
  public String getGridRepresentation() {
    return "P";
  }

  /**
   * Returns the string representation of PaperCell for use with the CellDecoder
   *
   * @return String "PaperCell"
   */
  public String toString() {
    return "PaperCell";
  }
}
