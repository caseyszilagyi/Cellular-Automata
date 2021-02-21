package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.grid_styles.Grid;

public class SpockCell extends RPSCell {


  public SpockCell() {

  }

  public SpockCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  @Override
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{new PaperCell(), new LizardCell()};
  }

  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new SpockCell(row, col, getThreshold()));
  }

  @Override
  public String getGridRepresentation() {
    return "S";
  }
}
