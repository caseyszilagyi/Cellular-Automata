package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Coordinate;
import java.util.List;

/**
 * This interface allows the programmer to define the discrete set of directions relevant in each
 * type of cell grid.  It provides many helper methods to the CellStructure class and centralizes
 * definitions for adjacent directions and neighborhoods.
 */
public interface Direction {

  /**
   * Convenience method to quickly compute the coordinate of the point pointed to by a given
   * direction, in the neighborhood centered at (row, col)
   *
   * @param row row index where the neighborhood is centered
   * @param col column index where the neighborhood is centered
   * @return Coordinate of the point pointed to by a given direction
   */
  Coordinate getResultingCoordinate(int row, int col);

  /**
   * Allows the implementing class to quickly obtain all cardinal directions.  For a square grid,
   * this would include spots to the North, East, South, and West.
   *
   * @param row row index where the neighborhood is centered
   * @param col column index where the neighborhood is centered
   * @return Coordinate of the point pointed to by a given direction.
   */
  List<Direction> getAdjacentDirections(int row, int col);

  Direction rotateLeft(Direction direction);

  Direction rotateRight(Direction direction);
}
