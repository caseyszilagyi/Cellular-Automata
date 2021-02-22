package cell_society.backend.automata.rock_paper_scissors;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;

public class ScissorCell extends RPSCell {

  public ScissorCell() {

  }

  public ScissorCell(int row, int col, int threshold) {
    super(row, col, threshold);
  }

  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    super.performPrimaryAction(neighbors, currentGrid, nextGrid);
  }

  @Override
  public void relocate(int row, int col, Grid grid) {
    grid.placeCell(row, col, new ScissorCell(row, col, getThreshold()));
  }

  @Override
  protected RPSCell[] defineWeaknesses() {
    return new RPSCell[]{new RockCell(), new SpockCell()};
  }

  @Override
  public String getGridRepresentation() {
    return "X";
  }

  public String toString(){ return "ScissorCell"; }
}
