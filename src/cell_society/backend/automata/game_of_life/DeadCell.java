package cell_society.backend.automata.game_of_life;


import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import java.util.List;

/**
 * The DeadCell represents the dead cell in Conway's Game of Life subject to the following rule:
 * <p>
 * 1. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 */
public class DeadCell extends Cell {

  public DeadCell(int row, int col) {
    super(row, col);
  }

  public DeadCell() {

  }

  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = this.getRow();
    int col = this.getCol();
    return grid.getDirectNeighbors(row, col);
  }

  /**
   * Updates the next Grid state according to the rules obeyed by DeadCells: Any dead cell with
   * exactly three live neighbours becomes a live cell, as if by reproduction.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    int numLiveNeighbors = neighbors.getTypeCount(new AliveCell(-1, -1));
    int row = getRow();
    int col = getCol();
    if (numLiveNeighbors == 3) {
      AliveCell aliveCell = new AliveCell(row, col);
      nextGrid.placeCell(row, col, aliveCell);
    } else {
      DeadCell deadCell = new DeadCell(row, col);
      nextGrid.placeCell(row, col, deadCell);
    }

    padDeadCells(currentGrid, nextGrid, numLiveNeighbors, row, col);
  }

  private void padDeadCells(Grid currentGrid, Grid nextGrid, int numLiveNeighbors, int row,
      int col) {
    if (numLiveNeighbors > 0) {
      List<Coordinate> coordinates = currentGrid.getGridCellStructure()
          .getAllNeighboringCoordinates(row, col);
      for (Coordinate coordinate : coordinates) {
        int coordRow = coordinate.getFirst();
        int coordCol = coordinate.getSecond();
        if (nextGrid.inBoundaries(coordRow, coordCol) && nextGrid.isEmpty(coordRow, coordCol)) {
          nextGrid.placeCell(coordRow, coordCol, new DeadCell(coordRow, coordCol));
        }
      }
    }
  }


  @Override
  public String toString() {
    return "DeadCell";
  }

  @Override
  public String getGridRepresentation() {
    return "_";
  }
}
