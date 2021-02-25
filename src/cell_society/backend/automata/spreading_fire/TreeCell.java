package cell_society.backend.automata.spreading_fire;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_initializer.CellParameters;

/**
 * The TreeCell represents a Tree in the spreading of fire simulation, with a probCatch probability
 * of catching on fire.
 *
 * @author George Hong
 */
public class TreeCell extends Cell {

  public static final String PROB_CATCH = "probcatch";
  private double probCatch;

  /**
   * Parameter-less constructor for initialization with the XML grid initializer.
   */
  public TreeCell() {

  }

  /**
   * Constructs an instance of TreeCell
   *
   * @param row       row index of this TreeCell
   * @param col       column index of this TreeCell
   * @param probCatch probability this TreeCell will catch fire, assuming at least one neighbor is
   *                  on fire.
   */
  public TreeCell(int row, int col, double probCatch) {
    super(row, col);
    this.probCatch = probCatch;
  }

  /**
   * Initializes TreeCell with the necessary parameters.  Intended for use with the parameter-less
   * constructor.
   *
   * @param parameters parameters object containing Agent properties
   */
  @Override
  public void initializeParams(CellParameters parameters) {
    probCatch = parameters.getAsDouble(PROB_CATCH);
  }

  /**
   * The TreeCell only considers neighbors to the North, East, South, and West, and other such
   * adjacent neighbors.
   *
   * @param grid grid holding the current configuration of cells
   * @return Neighbors objects containing all valid neighbors on the grid to this TreeCell.
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = getRow();
    int col = getCol();
    Neighbors neighbors = grid.getAdjacentNeighbors(row, col);
    return neighbors;
  }

  /**
   * If a TreeCell has a North, East, South, or West neighbor that is burning, there exists a
   * probCatch probability that the TreeCell will transform into a burning, Fire Cell.  Otherwise,
   * the Tree Cell remains intact, reflected in the next Grid state.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid current configuration of cells
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    int burningNeighbors = neighbors.getTypeCount(new FireCell(-1, -1));
    double treeResult = Math.random();
    int row = getRow();
    int col = getCol();
    if (burningNeighbors > 0 && treeResult < probCatch) {
      FireCell fireCell = new FireCell(row, col);
      nextGrid.placeCell(row, col, fireCell);
    } else {
      TreeCell treeCell = new TreeCell(row, col, probCatch);
      nextGrid.placeCell(row, col, treeCell);
    }
  }

  /**
   * Returns the String representation of this object for use with the Cell Decoder
   *
   * @return String "TreeCell"
   */
  @Override
  public String toString() {
    return "TreeCell";
  }

  /**
   * @return String "0", representing TreeCell.
   * @Deprecated returns a length-1 string to be printed in the grid through the console to show
   * occupation by a cell
   */
  @Override
  public String getGridRepresentation() {
    return "0";
  }
}
