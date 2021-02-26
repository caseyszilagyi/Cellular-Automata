package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Coordinate;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerates Square Directions with a 2D grid representation.  The directions in clockwise order
 * are given by: TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT
 *
 * @author George Hong
 */
public enum SquareDirection implements Direction {
  TOP(-1, 0),
  TOP_RIGHT(-1, 1),
  RIGHT(0, 1),
  BOTTOM_RIGHT(1, 1),
  BOTTOM(1, 0),
  BOTTOM_LEFT(1, -1),
  LEFT(0, -1),
  TOP_LEFT(-1, -1);

  private final int rowDelta;
  private final int colDelta;

  SquareDirection(int rowDelta, int colDelta) {
    this.rowDelta = rowDelta;
    this.colDelta = colDelta;
  }

  /**
   * Used to test for presence of bugs of rotation methods
   *
   * @param args additional arguments, not used
   * @Deprecated
   */
  public static void main(String[] args) {
    Direction d = SquareDirection.BOTTOM;
    for (int k = 0; k < 20; k++) {
      d = d.rotateCCW();
      System.out.println(d);
    }
    System.out.println();
    for (int k = 0; k < 20; k++) {
      d = d.rotateCW();
      System.out.println(d);
    }
  }

  /**
   * Computes new coordinates by moving in this direction by one spot, while centered at (row, col)
   *
   * @param row row index where the neighborhood is centered
   * @param col column index where the neighborhood is centered
   * @return Coordinate object containing the new row, column index
   */
  @Override
  public Coordinate getResultingCoordinate(int row, int col) {
    Coordinate coordinate = new Coordinate(row + rowDelta, col + colDelta);
    return coordinate;
  }

  /**
   * Adjacent directions are considered North, East, South, West
   *
   * @param row row index where the neighborhood is centered
   * @param col column index where the neighborhood is centered
   * @return List of Directions considered adjacent to the hexagon.  Can be modified as desired by
   * the receiver.
   */
  @Override
  public List<Direction> getAdjacentDirections(int row, int col) {
    Direction[] adjacentDirectionsArray = {
        SquareDirection.TOP,
        SquareDirection.LEFT,
        SquareDirection.RIGHT,
        SquareDirection.BOTTOM
    };
    List<Direction> adjacentDirections = Arrays.asList(adjacentDirectionsArray);
    return adjacentDirections;
  }

  /**
   * Gets the Direction enum directly clockwise of this Direction
   *
   * @return Direction enum
   */
  @Override
  public Direction rotateCW() {
    List<SquareDirection> directions = Arrays.asList(SquareDirection.values());
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
    List<SquareDirection> directions = Arrays.asList(SquareDirection.values());
    int size = directions.size();
    int dex = (directions.indexOf(this) - 1) % size;
    if (dex < 0) {
      dex = size + dex;
    }
    return directions.get(dex);
  }
}
