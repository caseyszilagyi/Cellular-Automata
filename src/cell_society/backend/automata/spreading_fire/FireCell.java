package cell_society.backend.automata.spreading_fire;

import cell_society.backend.automata.Cell;

/**
 * The FireCell represents fire in the spreading of fire simulation.  It is subject to the
 * assumption that the tree always burns down in one time step.  It does not update the next Grid,
 * leaving the spot null, and reflecting the completely burnt behavior.
 * @author George Hong
 */
public class FireCell extends Cell {

  public FireCell() {

  }

  public FireCell(int row, int col) {
    super(row, col);
  }

  @Override
  public String toString() {
    return "FireCell";
  }

  @Override
  public String getGridRepresentation() {
    return "X";
  }
}
