package cell_society.backend.automata.game_of_life;

import backend.AutomataModels.Cell;
import backend.AutomataModels.Grid;
import backend.AutomataModels.Neighbors;

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
  public Neighbors getNeighbors() {
    return super.getNeighbors();
  }

  @Override
  public void makeDecisions(Neighbors neighbors, Grid grid) {
    super.makeDecisions(neighbors, grid);
  }
}
