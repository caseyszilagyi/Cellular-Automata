package cell_society.backend.automata.spreading_fire;

import cell_society.backend.automata.Cell;

/**
 * The FireCell represents fire in the spreading of fire simulation.  It is subject to the
 * assumption that the tree always burns down in one time step.  It does not update the next Grid,
 * leaving the spot null, and reflecting the completely burnt behavior.
 *
 * @author George Hong
 */
public class FireCell extends Cell {

  /**
   * Parameter-less constructor for initializing an instance of FireCell with the XML grid
   * initializer.
   */
  public FireCell() {

  }

  /**
   * Constructs an instance of FireCell
   *
   * @param row row index to place FireCell
   * @param col column index to place FireCell
   */
  public FireCell(int row, int col) {
    super(row, col);
  }

  /**
   * Returns the String name of this Class for use with the CellDecoder
   *
   * @return String "FireCell"
   */
  @Override
  public String toString() {
    return "FireCell";
  }

  /**
   * @return String "X", representing a fire.
   * @Deprecated Returns a length-1 string to be printed in the grid to show occupation by this cell
   * type.
   */
  @Override
  public String getGridRepresentation() {
    return "X";
  }
}
