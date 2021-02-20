package cell_society.backend.automata.grid;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.grid.Grid;

/**
 * The toroidal grid is connected from sides, following a slightly different set of rules.
 */
public class ToroidalGrid extends Grid {

  /**
   * Makes a grid with the given width/height
   * @param gridHeight
   * @param gridWidth
   */
  public ToroidalGrid(int gridHeight, int gridWidth) {
    super(gridHeight, gridWidth);
  }

  /**
   * Empty constructor for newInstance method usage
   */
  public ToroidalGrid(){
  }

  public ToroidalGrid(Grid grid){
    super(grid);
  }
  /**
   * Used to make the grid and establish width/height
   * @param width The grid width
   * @param height The grid height
   */
  public void makeGrid(int width, int height){
    super.makeGrid(width, height);
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

  public Cell getCell(int row, int col) {
    Coordinate newCoordinates = getModifiedCoordinates(row, col);
    int modifiedRow = newCoordinates.getFirst();
    int modifiedCol = newCoordinates.getSecond();
    return super.getCell(modifiedRow, modifiedCol);
  }

  /**
   * Determines whether the specified position is occupied, now with support for negative indices
   * that wrap around.
   *
   * @param row row index of the position on the grid to check
   * @param col column index of the position on the grid to check
   * @return
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
