package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Grid;
import cell_society.backend.automata.Patch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfiniteGrid extends Grid {

  // These should override the variables of the base class
  private final Map<Coordinate, Cell> grid = new HashMap<>();
  private final Map<Coordinate, Patch> gridStates = new HashMap<>();

  public InfiniteGrid(int gridHeight, int gridWidth) {
    super(gridHeight, gridWidth);
  }

  public InfiniteGrid(Grid grid) {
    super(grid);
  }

  public InfiniteGrid() {
  }

  @Override
  public void makeGrid(int width, int height) {
    super.makeGrid(width, height);
  }

  @Override
  public Cell getCell(int row, int col) {
    return grid.get(new Coordinate(row, col));
  }

  @Override
  public void placeCell(int row, int col, Cell cell) {
    grid.put(new Coordinate(row, col), cell);
  }

  @Override
  public Patch getPatch(int row, int col) {
    return gridStates.get(new Coordinate(row, col));
  }

  @Override
  public void placePatch(int row, int col, Patch patch) {
    gridStates.put(new Coordinate(row, col), patch);
  }

  @Override
  public boolean inBoundaries(int row, int col) {
    return true;
  }

  @Override
  public boolean isEmpty(int row, int col) {
    return grid.get(new Coordinate(row, col)) == null;
  }

  @Override
  public List<Coordinate> getCoordinateUpdateList() {
    List<Coordinate> coordinateList = new ArrayList<>();
    for (Coordinate coordinate : grid.keySet()) {
      coordinateList.add(new Coordinate(coordinate));
    }
    return coordinateList;
  }

  @Override
  public void updateRemainingPatches(Grid otherGrid) {
    List<Coordinate> coordinateList = getCoordinateUpdateList();
    for (Coordinate coord : coordinateList) {
      if (gridStates.get(coord) == null) {
        gridStates.put(coord, otherGrid.getPatch(coord.getFirst(), coord.getSecond()));
      }
    }
  }
}
