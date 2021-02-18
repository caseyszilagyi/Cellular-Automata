package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Coordinate;

public enum HexagonalDirection implements Direction {

  // rows with odd indices are shifted to the right and have alternative indices to apply
  LEFT(0, -1, 0, -1),
  RIGHT(0, 1, 0, 1),
  TOP_LEFT(-1, -1, -1, 0),
  TOP_RIGHT(-1, 0, -1, 1),
  BOTTOM_LEFT(1, -1, 1, 0),
  BOTTOM_RIGHT(1, 0, 1, 1);

  private final int rowDelta;
  private final int colDelta;
  private final int rowDeltaShift;
  private final int colDeltaShift;

  HexagonalDirection(int rowDelta, int colDelta, int rowDeltaShift, int colDeltaShift) {
    this.rowDelta = rowDelta;
    this.colDelta = colDelta;
    this.rowDeltaShift = rowDeltaShift;
    this.colDeltaShift = colDeltaShift;
  }

  @Override
  public Coordinate getResultingCoordinate(int row, int col) {
    int rowChange = row % 2 == 0 ? rowDelta : rowDeltaShift;
    int colChange = col % 2 == 0 ? colDelta : colDeltaShift;
    Coordinate coordinate = new Coordinate(row + rowChange, col + colChange);
    return coordinate;
  }

  @Override
  public Direction rotateLeft(Direction direction) {
    return null;
  }

  @Override
  public Direction rotateRight(Direction direction) {
    return null;
  }
}
