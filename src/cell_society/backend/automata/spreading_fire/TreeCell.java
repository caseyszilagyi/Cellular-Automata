package cell_society.backend.automata.spreading_fire;

import cell_society.backend.simulation_initializer.CellParameters;
import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

/**
 * The TreeCell represents a Tree in the spreading of fire simulation, with a probCatch probability
 * of catching on fire.
 */
public class TreeCell extends Cell {

  private double probCatch;

  public TreeCell() {

  }

  public TreeCell(int row, int col, double probCatch) {
    super(row, col);
    this.probCatch = probCatch;
  }

  @Override
  public void initializeParams(CellParameters parameters) {
    probCatch = parameters.getAsDouble("probcatch");
  }

  /**
   * The TreeCell only considers neighbors to the North, East, South, and West.
   *
   * @param grid grid holding the current configuration of cells
   * @return
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = getRow();
    int col = getCol();
    Neighbors neighbors = grid.getAdjacentNeighbors(row, col);
    return neighbors;
  }

  /**
   * If a TreeCell has a North, East, South, or West neighbor that is burning, there exists a
   * probCatch probability that the TreeCell will transform into a burning, Fire Cell.  Otherwise,
   * the Tree Cell remains intact, reflected in the next Grid state.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param nextGrid    grid to hold the next configuration of cells.
   * @param currentGrid
   */
  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    int burningNeighbors = neighbors.getTypeCount(new FireCell(-1, -1));
    double treeResult = Math.random();
    int row = getRow();
    int col = getCol();
    if (burningNeighbors > 0 && treeResult < probCatch) {
      FireCell fireCell = new FireCell(row, col);
      nextGrid.placeCell(row, col, fireCell);
    } else {
      TreeCell treeCell = new TreeCell(row, col, probCatch);
      nextGrid.placeCell(row, col, treeCell);
    }
  }
  @Override
  public String toString() {
    return "O";
  }
}