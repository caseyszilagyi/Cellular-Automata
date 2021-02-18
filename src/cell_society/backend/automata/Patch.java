package cell_society.backend.automata;

import cell_society.backend.simulation_initializer.CellParameters;

/**
 * Represents properties or rules of a position on the grid.  Patches are initialized in parallel,
 * and thus can be initialized the same way a cell is.
 */
public class Patch {

  public Patch() {

  }

  public void initializeParams(CellParameters parameters) {

  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    Patch patch = new Patch();
    return patch;
  }
}
