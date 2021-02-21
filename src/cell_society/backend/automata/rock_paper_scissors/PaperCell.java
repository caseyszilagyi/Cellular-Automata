package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;

public class PaperCell extends RPSCell {

  public PaperCell() {

  }

  public PaperCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    super.performPrimaryAction(neighbors, currentGrid, nextGrid);
  }

  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new PaperCell(row, col, getThreshold()));
  }

  @Override
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{new ScissorCell(), new LizardCell()};
  }

  @Override
  public String getGridRepresentation() {
    return "P";
  }
}
