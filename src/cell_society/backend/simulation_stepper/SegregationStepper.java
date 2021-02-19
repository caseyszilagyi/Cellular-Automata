package cell_society.backend.simulation_stepper;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.segregation.AgentCell;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SegregationStepper extends SimulationStepper {

  private Grid simulationGrid;
  private int gridHeight;
  private int gridWidth;


  public SegregationStepper() {
  }

  public Grid getGrid() {
    return simulationGrid;
  }

  public void setGrid(Grid grid) {
    simulationGrid = grid;
    gridHeight = grid.getGridHeight();
    gridWidth = grid.getGridWidth();
  }

  @Override
  public void makeStep() {
    Grid nextGrid = new Grid(simulationGrid);
    Queue<Cell> dissatisfiedQueue = new LinkedList<>();
    List<Coordinate> vacantCoordinates = simulationGrid.getAllVacantSpots();

    findDissatisfiedNeighbors(nextGrid, dissatisfiedQueue, vacantCoordinates);
    redistributeDissatisfiedIndividuals(nextGrid, dissatisfiedQueue, vacantCoordinates);
    simulationGrid = nextGrid;
  }

  //Redistributes the individuals that aren't satisfied
  private void redistributeDissatisfiedIndividuals(Grid nextGrid, Queue<Cell> dissatisfiedQueue,
      List<Coordinate> vacantCoordinates) {
    Collections.shuffle(vacantCoordinates);
    // redistribute dissatisfied individuals.  Note, there's the potential here that a Cell will be placed in its current position.  Within the
    while (!dissatisfiedQueue.isEmpty()) {
      //AgentCell cell = (AgentCell) dissatisfiedQueue.poll();
      Cell cell = dissatisfiedQueue.poll();
      Coordinate newSpot = vacantCoordinates.get(0);
      vacantCoordinates.remove(0);
      int r = newSpot.getFirst();
      int c = newSpot.getSecond();
      //nextGrid.placeCell(r, c, new AgentCell(r, c, cell.getSatisfactionProp()));
      cell.relocate(r, c, nextGrid);
    }
  }

  //Loops through the grid to find dissatisfied neighbors
  private void findDissatisfiedNeighbors(Grid nextGrid,
      Queue<Cell> dissatisfiedQueue, List<Coordinate> vacantCoordinates) {
    for (int j = 0; j < gridHeight; j++) {
      for (int k = 0; k < gridWidth; k++) {
        checkIfSatisfied(nextGrid, dissatisfiedQueue, vacantCoordinates, j, k);
      }
    }


  }

  //Checks if a cell is satisfied. If not, add it to the queue
  private void checkIfSatisfied(Grid nextGrid, Queue<Cell> dissatisfiedQueue,
      List<Coordinate> vacantCoordinates, int j, int k) {
    Cell cell = simulationGrid.getCell(j, k);
    // Cell is already empty, already factored through grid.getAllVacantSpots method
    if (cell == null) {
      return;
    }
    Neighbors neighbors = cell.getNeighbors(simulationGrid);
    cell.performPrimaryAction(neighbors, null, nextGrid);
    // Alright, there's the potential that the agent is unhappy.  In that case, queue up for relocation

    if (!cell.probeState(neighbors, null, null)) {
      dissatisfiedQueue.add(cell);
      vacantCoordinates.add(new Coordinate(j, k));
    }
  }
}
