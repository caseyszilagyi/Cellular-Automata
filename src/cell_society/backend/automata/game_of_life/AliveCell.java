package cell_society.backend.automata.game_of_life;


import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

/**
 * The AliveCell represents the live cell in Conway's Game of Life subject to the following rules:
 * <p>
 * 1. Any live cell with fewer than two live neighbours dies, as if by underpopulation. 2. Any live
 * cell with two or three live neighbours lives on to the next generation. 3. Any live cell with
 * more than three live neighbours dies, as if by overpopulation.
 */
public class AliveCell extends Cell {

  public AliveCell(int row, int col) {
    super(row, col);
  }


  /**
   * The AliveCell considers all cells within one block of it to be a neighbor.
   *
   * @param grid grid where AliveCell is residing
   * @return Neighbors object containing all of the cells adjacent to this Cell.
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = getRow();
    int col = getCol();
    return grid.getDirectNeighbors(row, col);
  }

  @Override
  public void makeDecisions(Neighbors neighbors, Grid grid) {
    int numLiveNeighbors = neighbors.getTypeCount(this);
    int row = getRow();
    int col = getCol();
    if (numLiveNeighbors < 2 || numLiveNeighbors > 3) {
      DeadCell deadCell = new DeadCell(row, col);
      grid.placeCell(row, col, deadCell);
    } else {
      AliveCell aliveCell = new AliveCell(row, col);
      grid.placeCell(row, col, aliveCell);
    }
  }
}
