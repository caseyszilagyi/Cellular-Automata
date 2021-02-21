package cell_society.backend.automata.foraging_ants;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;

public class AntCell extends Cell {

  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    super.performPrimaryAction(neighbors, currentGrid, nextGrid);
  }

  @Override
  public void performSecondaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    super.performSecondaryAction(neighbors, currentGrid, nextGrid);
  }
}
