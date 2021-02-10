package cell_society.backend.automata;

/**
 * This class provides a quick way to compute new coordinates in a desired direction.  New positions
 * will be one block away.
 */
public enum Direction {
  TOP(-1, 0),
  BOTTOM(1, 0),
  LEFT(0, -1),
  RIGHT(0, 1),
  TOP_LEFT(-1, -1),
  TOP_RIGHT(-1, 1),
  BOTTOM_LEFT(1, -1),
  BOTTOM_RIGHT(1, 1);

  private final int rowDelta;
  private final int colDelta;

  Direction(int rowDelta, int colDelta) {
    this.rowDelta = rowDelta;
    this.colDelta = colDelta;
  }

  /**
   * This method computes the new row index in the direction of this enum class
   *
   * @param row starting row index where direction is determined
   * @return new row index, in this desired direction, at a distance of one away
   */
  public int applyToRow(int row) {
    return row + rowDelta;
  }

  /**
   * This method computes the new column index in the direction of this enum class
   *
   * @param col starting column index where direction is determined
   * @return new column index, in this desired direction, at a distance of one away
   */
  public int applyToCol(int col) {
    return col + colDelta;
  }

}
