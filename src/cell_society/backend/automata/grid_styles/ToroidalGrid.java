package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Patch;

/**
 * The toroidal grid is connected from sides, following a slightly different set of rules.  Cells
 * can leave any side of the Grid, and appear on the opposite side.  The grid supports the use of
 * coordinates (row, col) that are larger than the height and width of the grid.
 *
 * @author George Hong
 */
public class ToroidalGrid extends Grid {

  /**
   * Makes a grid with the given width/height
   *
   * @param gridHeight initial height of the Toroidal Grid
   * @param gridWidth  initial width of the Toroidal Grid
   */
  public ToroidalGrid(int gridHeight, int gridWidth) {
    super(gridHeight, gridWidth);
  }

  /**
   * Empty constructor for newInstance method usage
   */
  public ToroidalGrid() {
  }

  public ToroidalGrid(Grid grid) {
    super(grid);
  }

  /**
   * Used to make the grid and establish width/height
   *
   * @param width  The grid width
   * @param height The grid height
   */
  public void makeGrid(int width, int height) {
    super.makeGrid(width, height);
  }


  /**
   * Always in boundaries due to the toroid-warp property.
   *
   * @param row row index to check boundaries
   * @param col column index to check boundaries
   * @return boolean representing whether (row, col) is in boundaries of the toroidal grid
   */
  @Override
  public boolean inBoundaries(int row, int col) {
    return true;
  }

  /**
   * Place a cell into the desired coordinates.  This method provides support for wrapping-around
   * indices.
   *
   * @param row  row index of where to place the cell
   * @param col  column index of where to place the cell
   * @param cell cell object to be placed.
   */
  @Override
  public void placeCell(int row, int col, Cell cell) {
    Coordinate newCoordinates = getModifiedCoordinates(row, col);
    int modifiedRow = newCoordinates.getFirst();
    int modifiedCol = newCoordinates.getSecond();
    super.placeCell(modifiedRow, modifiedCol, cell);
  }

  /**
   * Place a patch into the desired coordinates of this grid.  This method supports wrapping-around,
   * so specified row and column indices can be greater or smaller than the Grid dimensions.
   *
   * @param row   row index of where to place the Patch
   * @param col   column index of where to place the Patch
   * @param patch Patch object to be placed
   */
  public void placePatch(int row, int col, Patch patch) {
    Coordinate newCoordinates = getModifiedCoordinates(row, col);
    int modifiedRow = newCoordinates.getFirst();
    int modifiedCol = newCoordinates.getSecond();
    super.placePatch(modifiedRow, modifiedCol, patch);
  }

  /**
   * Get the cell specified by (row, col).  This method supports the wrap-around behavior of the
   * Toroidal Grid.
   *
   * @param row desired row index of grid
   * @param col desired column index of grid
   * @return Cell cell object in location (row, col)
   */
  public Cell getCell(int row, int col) {
    Coordinate newCoordinates = getModifiedCoordinates(row, col);
    int modifiedRow = newCoordinates.getFirst();
    int modifiedCol = newCoordinates.getSecond();
    return super.getCell(modifiedRow, modifiedCol);
  }

  /**
   * Get the patch specified by (row, col).  This method supports the wrap-around behavior of the
   * Toroidal Grid.
   *
   * @param row row index of Patch to inspect
   * @param col column index of Patch to inspect
   * @return Patch object located at the given coordinates
   */
  public Patch getPatch(int row, int col) {
    Coordinate newCoordinates = getModifiedCoordinates(row, col);
    int modifiedRow = newCoordinates.getFirst();
    int modifiedCol = newCoordinates.getSecond();
    return super.getPatch(modifiedRow, modifiedCol);
  }

  /**
   * Determines whether the specified position is occupied, now with support for negative indices
   * that wrap around.
   *
   * @param row row index of the position on the grid to check
   * @param col column index of the position on the grid to check
   * @return boolean representing whether (row, col) is occupied by a cell.
   */
  @Override
  public boolean isEmpty(int row, int col) {
    Coordinate newCoordinates = getModifiedCoordinates(row, col);
    int modifiedRow = newCoordinates.getFirst();
    int modifiedCol = newCoordinates.getSecond();
    return super.isEmpty(modifiedRow, modifiedCol);
  }

  /**
   * Helper method for translating negative rows and columns to wrap around to the other side of the
   * grid, like a torus.
   *
   * @param row row index
   * @param col col index
   * @return Coordinate object containing the new coordinates
   */
  private Coordinate getModifiedCoordinates(int row, int col) {
    int modifiedRow = row % getGridHeight();
    int modifiedCol = col % getGridWidth();
    if (modifiedRow < 0) {
      modifiedRow = getGridHeight() + modifiedRow;
    }
    if (modifiedCol < 0) {
      modifiedCol = getGridWidth() + modifiedCol;
    }
    return new Coordinate(modifiedRow, modifiedCol);
  }
}
