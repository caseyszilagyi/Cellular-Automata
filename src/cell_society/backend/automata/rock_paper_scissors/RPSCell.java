package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_initializer.CellParameters;
import java.util.TreeMap;

/**
 * Encodes the basic structure of the Rock-Paper-Scissors Cell.  Cells define their own threshold
 * and weaknesses, but the PrimaryAction encoding the update is the same between all of them.
 *
 * @author George Hong
 */
public class RPSCell extends Cell {

  public static final String THRESHOLD = "threshold";
  private int threshold;

  /**
   * Parameter-less constructor in the style required by the XML Grid creator
   */
  public RPSCell() {

  }

  /**
   * Constructs an instance of a RPSCell
   *
   * @param row       row index where this RPSCell is located, for personal reference
   * @param col       column index where this RPSCell is located, for personal reference
   * @param threshold minimum amount of neighbors this Cell is weak to, to change this Cell into a
   *                  RPSCell-type that has an advantage over it.
   */
  public RPSCell(int row, int col, int threshold) {
    super(row, col);
    this.threshold = threshold;
  }

  /**
   * Initializes the threshold of this RPSCell, intended for use with the Parameter-less
   * constructor.
   *
   * @param parameters parameters object containing Agent properties
   */
  @Override
  public void initializeParams(CellParameters parameters) {
    threshold = parameters.getAsInt(THRESHOLD);
  }

  /**
   * The RPSCell inspects all cells in its Moore Neighborhood.  For any Cell type that is the
   * weakness of this Cell, if the count of those cell is greater than or equal to this cell's
   * threshold, this Cell will be converted into that type of Cell in the next phase.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    TreeMap<Integer, RPSCell> map = new TreeMap<>();
    for (RPSCell cell : defineWeaknesses()) {
      map.put(neighbors.getTypeCount(cell), cell);
    }
    if (map.lastKey() >= threshold) {
      RPSCell cell = map.get(map.lastKey());
      cell.setThreshold(threshold);
      cell.relocate(getRow(), getCol(), nextGrid);
    } else {
      this.relocate(getRow(), getCol(), nextGrid);
    }
  }

  protected int getThreshold() {
    return threshold;
  }

  protected void setThreshold(int threshold) {
    this.threshold = threshold;
  }

  /**
   * Defines the weaknesses of this RPSCell
   * @return array of RPSCells that this Cell considers its weaknesses
   */
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{};
  }

}
