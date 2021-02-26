package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.CellStructure;
import cell_society.backend.automata.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class exists for the Grid to easily reference square properties of its cells
 *
 * @author George Hong
 */
public class SquareStructure extends CellStructure {

  /**
   * Provides the Moore Neighborhood, resulting in a list of 8 coordinates.
   *
   * @param row row index of the desired cell at the center of each neighborhood
   * @param col column index of the desired cell at the center of each neighborhood
   * @return List of Coordinates of each neighbor of the coordinate (row, col).  This list is freely
   * mutable.
   */
  @Override
  public List<Coordinate> getAllNeighboringCoordinates(int row, int col) {
    List<Coordinate> allNeighboringCoordinates = new ArrayList<>();
    for (Direction d : SquareDirection.values()) {
      allNeighboringCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allNeighboringCoordinates;
  }

  /**
   * Provides all adjacent neighbors of a given type of cell.  There are four such coordinates,
   * corresponding to North, East, South, and West.
   *
   * @param row row index of the desired cell at the center of each neighborhood
   * @param col column index of the desired cell at the center of each neighborhood
   * @return List of Coordinates corresponding to all adjacent positions of (row, col)
   */
  @Override
  public List<Coordinate> getAllAdjacentCoordinates(int row, int col) {
    List<Coordinate> allAdjacentCoordinates = new ArrayList<>();
    for (Direction d : SquareDirection.TOP.getAdjacentDirections(row, col)) {
      allAdjacentCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allAdjacentCoordinates;
  }

  /**
   * Randomly returns 1 out of the 8 valid directions for Square cells.
   *
   * @param row row index to help distinguish valid directions
   * @param col column index to help distinguish valid directions
   * @return random valid Square Direction.
   */
  @Override
  public Direction getRandomDirection(int row, int col) {
    SquareDirection[] directions = SquareDirection.values();
    int length = directions.length;
    return directions[ThreadLocalRandom.current().nextInt(0, length)];
  }

  /**
   * Returns int encoding for this cell structure for frontend use
   *
   * @return 0, corresponding to the square cell structure
   */
  public int getCode() {
    return 0;
  }

}
