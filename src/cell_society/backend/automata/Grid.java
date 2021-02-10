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

  /**
   * Returns All cells neighboring the selected position, within one block, on the Grid.  This
   * produces a maximum of 8 neighbors.
   *
   * @param row row index of grid to identify surrounding neighbors
   * @param col column index of grid to identify surrounding neighbors
   * @return Neighbors object containing the neighboring objects
   */
  public Neighbors getDirectNeighbors(int row, int col) {
    Neighbors neighbors = new Neighbors();
    for (Direction d : Direction.values()){
      int newRow = d.applyToRow(row);
      int newCol = d.applyToCol(col);
      if (inBoundaries(newRow, newCol)){
        neighbors.add(grid[newRow][newCol]);
      }
    }
    return neighbors;
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
