package cell_society.backend.automata;

public class Grid {

  private final Cell[][] grid;
  private final int gridHeight;
  private final int gridWidth;

  public Grid(int gridHeight, int gridWidth) {
    grid = new Cell[gridHeight][gridWidth];
    this.gridHeight = gridHeight;
    this.gridWidth = gridWidth;
  }

  public boolean isEmpty(int row, int col) {
    return grid[row][col] == null;
  }

  /**
   * This method provides a quick check that a provided coordinate is within the bounds of the
   * Grid.
   *
   * @param row
   * @param col
   * @return
   */
  public boolean inBoundaries(int row, int col) {
    return !(row >= gridHeight || row < 0 || col >= gridWidth || col < 0);
  }

  public void placeCell(int row, int col, Cell cell) {
    grid[row][col] = cell;
  }
}
