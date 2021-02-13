package cell_society.backend.automata.wator;

import cell_society.backend.SimulationInitializer.CellParameters;
import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

public class SharkCell extends Cell {

  private int energy;
  private int reproduceThresh;

  public SharkCell(int row, int col, int energy, int reproduceThresh) {
    super(row, col);
    this.energy = energy;
    this.reproduceThresh = reproduceThresh;
  }

  public SharkCell() {

  }

  @Override
  public void initializeParams(CellParameters parameters) {
    super.initializeParams(parameters);
    energy = parameters.getAsInt("energy");
    reproduceThresh = parameters.getAsInt("reproduceThresh");
  }

  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    super.makeDecisions(neighbors, nextGrid, currentGrid);
  }

  public void reproduce(){

  }
}
