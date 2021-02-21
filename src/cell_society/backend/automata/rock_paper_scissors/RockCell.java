package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.grid_styles.Grid;

public class RockCell extends RPSCell {

  public RockCell() {

  }

  public RockCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new RockCell(row, col, getThreshold()));
  }

  @Override
  protected Cell[] defineWeaknesses() {
    return new Cell[]{new SpockCell(), new PaperCell()};
  }

  @Override
  public String getGridRepresentation() {
    return "R";
  }
}
