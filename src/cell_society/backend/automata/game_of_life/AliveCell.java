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

  @Override
  public Neighbors getNeighbors() {
    //return super.getNeighbors();
    int row = getRow();
    int col = getCol();

    return null;
  }

  @Override
  public void makeDecisions(Neighbors neighbors, Grid grid) {
    super.makeDecisions(neighbors, grid);
  }
}
