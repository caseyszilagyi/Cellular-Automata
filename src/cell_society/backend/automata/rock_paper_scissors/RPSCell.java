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

  public RPSCell() {

  }

  public RPSCell(int row, int col, int threshold) {
    super(row, col);
    this.threshold = threshold;
  }

  @Override
  public void initializeParams(CellParameters parameters) {
    threshold = parameters.getAsInt(THRESHOLD);
  }

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

  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{};
  }

}
