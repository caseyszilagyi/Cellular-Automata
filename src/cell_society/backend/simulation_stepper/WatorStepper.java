package cell_society.backend.simulation_stepper;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.wator.FishCell;
import cell_society.backend.automata.wator.SharkCell;
import cell_society.backend.automata.wator.ToroidalGrid;

public class WatorStepper extends SimulationStepper {

  private Grid simulationGrid;
  private int gridHeight;
  private int gridWidth;

  public WatorStepper() {

  }

  public void makeStep() {
    Grid nextGrid = new ToroidalGrid(simulationGrid);
    // Move Fish first
    selectiveLoop(nextGrid, new FishCell());
    // Move Sharks
    selectiveLoop(nextGrid, new SharkCell());

    // Reproduction
    applyReproduce(nextGrid);
  }

  private void applyReproduce(Grid nextGrid) {
    for (int j = 0; j < gridHeight; j++) {
      for (int k = 0; k < gridHeight; k++) {
        Cell cell = simulationGrid.getCell(j, k);
        //if (cell == null) continue;
        if (cell instanceof SharkCell) {
          SharkCell shark = (SharkCell) cell;
          shark.reproduce(nextGrid);
        } else if (cell instanceof FishCell) {
          FishCell fish = (FishCell) cell;
          fish.reproduce(nextGrid);
        }
      }
    }
    simulationGrid = nextGrid;
  }

  /**
   * Passes through the entire grid, but only updates the chosen type of Cell
   *
   * @param nextGrid   Grid to apply updates
   * @param chosenCell Cell type that will not be filtered from calling its makeDecisions method.
   */
  private void selectiveLoop(Grid nextGrid, Cell chosenCell) {
    for (int j = 0; j < gridHeight; j++) {
      for (int k = 0; k < gridHeight; k++) {
        Cell cell = simulationGrid.getCell(j, k);
        if (cell == null || !(cell.getClass() == chosenCell.getClass())) {
          continue;
        }
        Neighbors neighbors = cell.getNeighbors(simulationGrid);
        cell.makeDecisions(neighbors, nextGrid, simulationGrid);
      }
    }
  }

  public void setGrid(Grid grid) {
    simulationGrid = grid;
    gridHeight = grid.getGridHeight();
    gridWidth = grid.getGridWidth();
  }

  public Grid getGrid(){
    return simulationGrid;
  }

}
