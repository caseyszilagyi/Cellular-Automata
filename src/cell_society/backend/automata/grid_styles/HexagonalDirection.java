package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Coordinate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerates Hexagonal Directions with a 2D grid representation. The directions, in clockwise
 * order, are given by: TOP_LEFT, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT, LEFT
 *
 * @author George Hong
 */
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

  /**
   * Computes new coordinates by moving in this direction by one spot, while centered at (row, col)
   *
   * @param row row index where the neighborhood is centered
   * @param col column index where the neighborhood is centered
   * @return Coordinate object containing the new row, col
   */
  @Override
  public Coordinate getResultingCoordinate(int row, int col) {
    int rowChange = row % 2 == 0 ? rowDelta : rowDeltaShift;
    int colChange = row % 2 == 0 ? colDelta : colDeltaShift;
    Coordinate coordinate = new Coordinate(row + rowChange, col + colChange);
    return coordinate;
  }

  /**
   * Assume all neighbors bordering a hexagon are adjacent.
   *
   * @param row row index where the neighborhood is centered
   * @param col column index where the neighborhood is centered
   * @return List of Directions considered adjacent to the hexagon.  Can be modified as desired by
   * the receiver.
   */
  @Override
  public List<Direction> getAdjacentDirections(int row, int col) {
    List<Direction> allAdjacentDirections = new ArrayList<>();
    for (HexagonalDirection d : HexagonalDirection.values()) {
      allAdjacentDirections.add(d);
    }
    return allAdjacentDirections;
  }

  /**
   * Gets the Direction enum directly clockwise of this Direction
   *
   * @return Direction enum
   */
  @Override
  public Direction rotateCW() {
    List<HexagonalDirection> directions = Arrays.asList(HexagonalDirection.values());
    int size = directions.size();
    return directions.get((directions.indexOf(this) + 1) % size);
  }

  /**
   * Gets the Direction enum directly counterclockwise of this Direction
   *
   * @return Direction enum
   */
  @Override
  public Direction rotateCCW() {
    List<HexagonalDirection> directions = Arrays.asList(HexagonalDirection.values());
    int size = directions.size();
    int dex = (directions.indexOf(this) - 1) % size;
    if (dex < 0) {
      dex = size + dex;
    }
    return directions.get(dex);
  }
}
