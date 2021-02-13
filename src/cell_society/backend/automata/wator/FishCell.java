package cell_society.backend.automata.wator;

import cell_society.backend.SimulationInitializer.CellParameters;
import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;

public class FishCell extends Cell {

  private int breedTimeCounter;
  private int breedTimeThresh;

  public FishCell(int row, int col, int breedTimeCounter, int breedTimeThresh) {
    super(row, col);
    this.breedTimeCounter = breedTimeCounter;
    this.breedTimeThresh = breedTimeThresh;
  }

  public FishCell() {

  }

  @Override
  public void initializeParams(CellParameters parameters) {
    super.initializeParams(parameters);
    breedTimeCounter = parameters.getAsInt("breedtimecounter");
    breedTimeThresh = parameters.getAsInt("breedtimethresh");
  }

  @Override
  public Neighbors getNeighbors(Grid grid) {
    return super.getNeighbors(grid);
  }

  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    super.makeDecisions(neighbors, nextGrid, currentGrid);
  }
}
