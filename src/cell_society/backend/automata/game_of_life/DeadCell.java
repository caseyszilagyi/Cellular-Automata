package cell_society.backend.automata.game_of_life;


import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

/**
 * The DeadCell represents the dead cell in Conway's Game of Life subject to the following rule:
 * <p>
 * 1. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 */
public class DeadCell extends Cell {

  public DeadCell(int row, int col) {
    super(row, col);
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
   * @param neighbors Cells that this cell uses to make its decision
   * @param grid      grid to hold the next configuration of cells.
   */
  @Override
  public void makeDecisions(Neighbors neighbors, Grid grid) {
    int numLiveNeighbors = neighbors.getTypeCount(new AliveCell(-1, -1));
    int row = getRow();
    int col = getCol();
    if (numLiveNeighbors == 3) {
      AliveCell aliveCell = new AliveCell(row, col);
      grid.placeCell(row, col, aliveCell);
    } else {
      DeadCell deadCell = new DeadCell(row, col);
      grid.placeCell(row, col, deadCell);
    }
  }

  @Override
  public String toString() {
    return "_";
  }
}
