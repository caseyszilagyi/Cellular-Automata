package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Coordinate;
import java.util.ArrayList;
import java.util.List;

public enum HexagonalDirection implements Direction {

  // rows with odd indices are shifted to the right and have alternative indices to apply
  TOP_LEFT(-1, -1, -1, 0),
  TOP_RIGHT(-1, 0, -1, 1),
  RIGHT(0, 1, 0, 1),
  BOTTOM_RIGHT(1, 0, 1, 1),
  BOTTOM_LEFT(1, -1, 1, 0),
  LEFT(0, -1, 0, -1);

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
    int colChange = row % 2 == 0 ? colDelta : colDeltaShift;
    Coordinate coordinate = new Coordinate(row + rowChange, col + colChange);
    return coordinate;
  }

  /**
   * Assume all neighbors bordering a hexagon are adjacent.
   * @param row row index where the neighborhood is centered
   * @param col column index where the neighborhood is centered
   * @return
   */
  @Override
  public List<Direction> getAdjacentDirections(int row, int col) {
    List<Direction> allAdjacentDirections = new ArrayList<>();
    for (HexagonalDirection d : HexagonalDirection.values()) {
      allAdjacentDirections.add(d);
    }
    return allAdjacentDirections;
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
