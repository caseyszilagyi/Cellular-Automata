package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.grid_styles.Grid;

public class LizardCell extends RPSCell {

  public LizardCell() {

  }

  public LizardCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new LizardCell(row, col, getThreshold()));
  }

  @Override
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{new RockCell(), new ScissorCell()};
  }

  @Override
  public String getGridRepresentation() {
    return "L";
  }

  public String toString() { return "LizardCell"; }
}
