package cell_society.backend.automata.foraging_ants;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.grid_styles.Direction;
import cell_society.backend.automata.grid_styles.Grid;
import java.util.ArrayList;
import java.util.List;

public class AntCell extends Cell {

  private final Direction antDirection;
  private final int food;

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
    if (food == 0) {
      int row = getRow();
      int col = getCol();
      List<Coordinate> availableDirections = new ArrayList<>();
      Coordinate directionCW = antDirection.rotateCW().getResultingCoordinate(row, col);
      Patch patchCW = currentGrid.inBoundaries(row, col) ? null : currentGrid.getPatch(
          directionCW.getFirst(), directionCW.getSecond());
      Coordinate directionCCW = antDirection.rotateCCW().getResultingCoordinate(row, col);
      Patch patchCCW = currentGrid.inBoundaries(row, col) ? null : currentGrid.getPatch(
          directionCCW.getFirst(), directionCCW.getSecond());
      Coordinate chosenCoordinate;
      if (patchCW == null && patchCCW == null) {
        // TODO: Choose new coordinate and leave

      } else if (patchCW == null) {
        chosenCoordinate = directionCCW;
      } else if (patchCCW == null) {
        chosenCoordinate = directionCW;
      } else {
        // TODO: Use randomized coordinate choosing
      }


    } else {

    }
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
