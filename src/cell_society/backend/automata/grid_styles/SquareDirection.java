package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Coordinate;
import java.util.Arrays;
import java.util.List;

public enum SquareDirection implements Direction {
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

  SquareDirection(int rowDelta, int colDelta) {
    this.rowDelta = rowDelta;
    this.colDelta = colDelta;
  }

  @Override
  public Coordinate getResultingCoordinate(int row, int col) {
    Coordinate coordinate = new Coordinate(row + rowDelta, col + colDelta);
    return coordinate;
  }

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

  @Override
  public Direction rotateLeft(Direction direction) {
    return null;
  }

  @Override
  public Direction rotateRight(Direction direction) {
    return null;
  }
}
