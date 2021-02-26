package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Coordinate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerates Triangular Directions with a 2D grid representation.  There exists two possible
 * configurations of directions, due to the alternating position of the triangles in order to
 * tesselate.  These directions are coded EVEN and ODD, with 12 possible direction for each
 * configuration.
 *
 * @author George Hong
 */
public enum TriangularDirection implements Direction {
  // Due to the number of possible coordinates, we define directions 1-12, and sorted between EVEN and ODD
  EVEN_1(-1, 0, false),
  EVEN_2(-1, 1, false),
  EVEN_3(-1, 2, false),
  EVEN_4(0, 2, false),
  EVEN_5(0, 1, false),
  EVEN_6(1, 1, false),
  EVEN_7(1, 0, false),
  EVEN_8(1, -1, false),
  EVEN_9(0, -1, false),
  EVEN_10(0, -2, false),
  EVEN_11(-1, -2, false),
  EVEN_12(-1, -1, false),

  ODD_1(-1, 0, true),
  ODD_2(-1, 1, true),
  ODD_3(0, 1, true),
  ODD_4(0, 2, true),
  ODD_5(1, 2, true),
  ODD_6(1, 1, true),
  ODD_7(1, 0, true),
  ODD_8(1, -1, true),
  ODD_9(1, -2, true),
  ODD_10(0, -2, true),
  ODD_11(0, -1, true),
  ODD_12(-1, -1, true);

  private final int rowDelta;
  private final int colDelta;
  private final boolean oddConfig;

  TriangularDirection(int rowDelta, int colDelta, boolean oddConfig) {
    this.rowDelta = rowDelta;
    this.colDelta = colDelta;
    this.oddConfig = oddConfig;
  }

  /**
   * Gets the complete List of odd configuration directions, freely modifiable to the user's needs.
   *
   * @return List of odd configuration directions.
   */
  public List<TriangularDirection> getOddConfigurationDirections() {
    List<TriangularDirection> oddConfigurationDirections = new ArrayList<>();
    for (TriangularDirection d : TriangularDirection.values()) {
      if (d.oddConfig == true) {
        oddConfigurationDirections.add(d);
      }
    }
    return oddConfigurationDirections;
  }

  /**
   * Gets the complete List of even configuration directions, freely modifiable to the user's
   * needs.
   *
   * @return List of even configuration directions.
   */
  public List<TriangularDirection> getEvenConfigurationDirections() {
    List<TriangularDirection> evenConfigurationDirections = new ArrayList<>();
    for (TriangularDirection d : TriangularDirection.values()) {
      if (d.oddConfig == false) {
        evenConfigurationDirections.add(d);
      }
    }
    return evenConfigurationDirections;
  }

  /**
   * Computes new coordinates by moving in this direction by one spot, while centered at (row, col)
   *
   * @param row row index where the neighborhood is centered
   * @param col column index where the neighborhood is centered
   * @return Coordinate object containing the new (row, col)
   */
  @Override
  public Coordinate getResultingCoordinate(int row, int col) {
    Coordinate coordinate = new Coordinate(row + rowDelta, col + colDelta);
    return coordinate;
  }

  /**
   * Assumes the adjacent neighbors of a triangle are the 3 in direct contact.
   *
   * @param row row index where the neighborhood is centered
   * @param col column index where the neighborhood is centered
   * @return List of Directions considered adjacent to the triangle.  Can be modified as desired by
   * the receiver.
   */
  @Override
  public List<Direction> getAdjacentDirections(int row, int col) {
    Direction[] adjacentDirectionsArray;
    if ((row + col) % 2 == 0) {
      adjacentDirectionsArray = new Direction[]{
          TriangularDirection.EVEN_1,
          TriangularDirection.EVEN_5,
          TriangularDirection.EVEN_9
      };
    } else {
      adjacentDirectionsArray = new Direction[]{
          TriangularDirection.ODD_3,
          TriangularDirection.ODD_7,
          TriangularDirection.ODD_11
      };
    }
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
    return null;
  }

  /**
   * Gets the Direction enum directly counterclockwise of this Direction.
   *
   * @return Direction enum
   */
  @Override
  public Direction rotateCCW() {
    return null;
  }
}
