package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Patch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The infinite grid allows cells to move to whatever coordinate they desire.  The grid initially
 * has a fixed size, but expands as cells move out of the initial boundaries.  To view the grid,
 * slices of the grid can be observed, with (0, 0) as the reference point.
 *
 * @author George Hong
 */
public class InfiniteGrid extends Grid {

  // These should override the variables of the base class
  private final Map<Coordinate, Cell> grid = new HashMap<>();
  private final Map<Coordinate, Patch> gridStates = new HashMap<>();

  /**
   * Constructs an instance of the Infinite Grid
   *
   * @param gridHeight initial height of the infinite grid
   * @param gridWidth  initial width of the infinite grid
   */
  public InfiniteGrid(int gridHeight, int gridWidth) {
    super(gridHeight, gridWidth);
  }

  /**
   * Copy constructor of the Infinite Grid intended for use with a stepper.
   *
   * @param grid previous grid state to copy
   */
  public InfiniteGrid(Grid grid) {
    super(grid);
  }

  /**
   * Parameter-less constructor intended for use with the XML file reader.
   */
  public InfiniteGrid() {
  }

  /**
   * Method intended for use with the parameter-less constructor.
   *
   * @param width  The grid width
   * @param height The grid height
   */
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

  /**
   * Checks that a (row, col) coordinate pair is within boundaries.  This is always true because the
   * Infinite Grid can accommodate any coordinate pair.
   *
   * @param row
   * @param col
   * @return
   */
  @Override
  public boolean inBoundaries(int row, int col) {
    return true;
  }

  @Override
  public boolean isEmpty(int row, int col) {
    return grid.get(new Coordinate(row, col)) == null;
  }

  /**
   * Returns the list of coordinates corresponding to Cells within the grid.  These are cells that
   * the stepper must update in the course of a step.  These coordinates are generated on call, and
   * can be mutated, as desired.
   *
   * @return List of coordinates.
   */
  @Override
  public List<Coordinate> getCoordinateUpdateList() {
    List<Coordinate> coordinateList = new ArrayList<>();
    for (Coordinate coordinate : grid.keySet()) {
      coordinateList.add(new Coordinate(coordinate));
    }
    return coordinateList;
  }

  /**
   * Lifts patches from the previous Grid into this grid state.  A typical use for this method is at
   * the beginning of the update step.
   *
   * @param otherGrid previous Grid object
   */
  @Override
  public void updateRemainingPatches(Grid otherGrid) {
    //List<Coordinate> coordinateList = getCoordinateUpdateList();
    List<Coordinate> coordinateList = otherGrid.getPatchUpdateList();
    for (Coordinate coord : coordinateList) {
      if (gridStates.get(coord) == null) {
        gridStates.put(coord, otherGrid.getPatch(coord.getFirst(), coord.getSecond()));
      }
    }
  }

  protected List<Coordinate> getPatchUpdateList() {
    List<Coordinate> coordinateList = new ArrayList<>();
    for (Coordinate coordinate : gridStates.keySet()) {
      coordinateList.add(new Coordinate(coordinate));
    }
    return coordinateList;
  }
}
