package cell_society.backend.automata.foraging_ants;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.grid_styles.Direction;
import cell_society.backend.automata.grid_styles.Grid;

public class AntCell extends Cell {

  private Direction antDirection;
  private final int food;

  public AntCell(int row, int col, AntCell antCell) {
    super(row, col);
    antDirection = antCell.antDirection;
    food = antCell.food;
  }

  public AntCell(int row, int col, Grid grid) {
    super(row, col);
    food = 0;
    antDirection = grid.getGridCellStructure().getRandomDirection(row, col);
  }

  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    // Find food mode
    if (food == 0) {
      int row = getRow();
      int col = getCol();
      // Assess movement options


    } else {

    }
  }

  private boolean validDirection(int row, int col, Direction direction, Grid currentGrid, Grid nextGrid) {
    // A valid cell direction choice if a cell can move to it.
    Coordinate result = direction.getResultingCoordinate(row, col);
    return nextGrid.inBoundaries(result) && currentGrid.isEmpty(result) && nextGrid.isEmpty(result);
  }

  @Override
  public void performSecondaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    super.performSecondaryAction(neighbors, currentGrid, nextGrid);
  }

  @Override
  public boolean probeState(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    return food > 0;
  }
}
