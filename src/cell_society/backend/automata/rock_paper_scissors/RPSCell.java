package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;
import java.util.TreeMap;

public class RPSCell extends Cell {

  private int threshold;

  public RPSCell() {

  }

  public RPSCell(int row, int col, int threshold) {
    super(row, col);
    this.threshold = threshold;
  }

  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    TreeMap<Integer, Cell> map = new TreeMap<>();
    for (Cell cell : defineWeaknesses()) {
      map.put(neighbors.getTypeCount(cell), cell);
    }
    if (map.lastKey() >= threshold) {
      Cell cell = map.get(map.lastKey());
      cell.relocate(getRow(), getCol(), nextGrid);
    } else {
      this.relocate(getRow(), getCol(), nextGrid);
    }
  }

  protected int getThreshold() {
    return threshold;
  }

  protected Cell[] defineWeaknesses() {
    return new Cell[]{};
  }

}
