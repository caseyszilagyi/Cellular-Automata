package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Coordinate;

public interface Direction {
  public Coordinate getResultingCoordinate(int row, int col);
  public Direction rotateLeft(Direction direction);
  public Direction rotateRight(Direction direction);
}
