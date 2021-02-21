package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Coordinate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  public List<TriangularDirection> getOddConfigurationDirections() {
    List<TriangularDirection> oddConfigurationDirections = new ArrayList<>();
    for (TriangularDirection d : TriangularDirection.values()) {
      if (d.oddConfig == true) {
        oddConfigurationDirections.add(d);
      }
    }
    return oddConfigurationDirections;
  }

  public List<TriangularDirection> getEvenConfigurationDirections() {
    List<TriangularDirection> evenConfigurationDirections = new ArrayList<>();
    for (TriangularDirection d : TriangularDirection.values()) {
      if (d.oddConfig == false) {
        evenConfigurationDirections.add(d);
      }
    }
    return evenConfigurationDirections;
  }

  @Override
  public Coordinate getResultingCoordinate(int row, int col) {
    Coordinate coordinate = new Coordinate(row + rowDelta, col + colDelta);
    return coordinate;
  }

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

  @Override
  public Direction rotateCW() {
    return null;
  }

  @Override
  public Direction rotateCCW() {
    return null;
  }
}
