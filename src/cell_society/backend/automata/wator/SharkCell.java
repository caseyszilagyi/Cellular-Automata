package cell_society.backend.automata.wator;

import cell_society.backend.simulation_initializer.CellParameters;
import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Direction;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Neighbors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

  /**
   * This method allows a SharkCell to carry out its movement behavior, given by: Sharks move
   * randomly to neighboring cells that are free or occupied by (non shark) fish. If the cell to
   * which the shark is moving is occupied by other fish it is consumed. The energy of the shark
   * increases by 1.  The shark dies if its energy level drops to zero, and sharks lose 1 energy
   * during every time step.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param nextGrid    grid to hold the next configuration of cells.
   * @param currentGrid
   */
  @Override
  public void makeDecisions(Neighbors neighbors, Grid nextGrid, Grid currentGrid) {
    if (energy == 0) {
      return;
    }
    energy--;
    int row = getRow();
    int col = getCol();
    for (Coordinate coords : getRandAdjacent(row, col)) {
      int shiftedRow = coords.getFirst();
      int shiftedCol = coords.getSecond();
      // Next state either has empty space, or has a fish that can be consumed.
      if (currentGrid.isEmpty(shiftedRow, shiftedCol) && notShark(shiftedRow, shiftedCol,
          nextGrid)) {
        Cell cell = nextGrid.getCell(shiftedRow, shiftedCol);
        if (cell instanceof FishCell) {
          energy++;
        }
        SharkCell sharkCell = new SharkCell(shiftedRow, shiftedCol, energy, reproduceThresh);
        nextGrid.placeCell(shiftedRow, shiftedCol, sharkCell);
        return;
      }
    }
    SharkCell sharkCell = new SharkCell(row, col, energy, reproduceThresh);
    nextGrid.placeCell(row, col, sharkCell);
  }

  /**
   * If the shark has enough energy it spawns offspring in a free neighboring cell. The energy is
   * split evenly between the parent and the child.
   *
   * @param grid
   */
  public void reproduce(Grid grid) {
    if (energy >= reproduceThresh) {
      for (Coordinate coords : getRandAdjacent(getRow(), getCol())) {
        int row = coords.getFirst();
        int col = coords.getSecond();
        if (grid.isEmpty(row, col)) {
          SharkCell offspring = new SharkCell(row, col, energy / 2, reproduceThresh);
          energy /= 2;
          grid.placeCell(row, col, offspring);
          return;
        }
      }
    }
  }

  private boolean notShark(int row, int col, Grid grid) {
    return grid.isEmpty(row, col) || grid.getCell(row, col) instanceof SharkCell;
  }

  private List<Coordinate> getRandAdjacent(int row, int col) {
    List<Coordinate> returnCoordinates = new ArrayList<>();
    Direction[] direction = new Direction[]{Direction.TOP, Direction.LEFT, Direction.RIGHT,
        Direction.BOTTOM};
    List<Direction> directionList = Arrays.asList(direction);
    Collections.shuffle(directionList);
    for (Direction dir : directionList) {
      int shiftedRow = dir.applyToRow(row);
      int shiftedCol = dir.applyToCol(col);
      returnCoordinates.add(new Coordinate(shiftedRow, shiftedCol));
    }
    return returnCoordinates;
  }

  @Override
  public String toString() {
    return "S";
  }
}
