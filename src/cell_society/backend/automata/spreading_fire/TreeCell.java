package cell_society.backend.automata.spreading_fire;

import cell_society.backend.SimulationInitializer.CellParameters;
import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import com.sun.source.tree.Tree;

public class TreeCell extends Cell {
  private double probCatch;
  public TreeCell() {

  }
  public TreeCell(int row, int col, double probCatch){
    super(row, col);
    this.probCatch = probCatch;
  }

  @Override
  public void initializeParams(CellParameters parameters) {
    probCatch = parameters.getAsDouble("probcatch");
  }

  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = getRow();
    int col = getCol();
    Neighbors neighbors = grid.getDirectNeighbors(row, col);
    return null;
  }

  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    super.makeDecisions(neighbors, nextGrid, currentGrid);
  }
}
