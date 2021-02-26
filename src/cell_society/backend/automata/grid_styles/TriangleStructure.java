package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.CellStructure;
import cell_society.backend.automata.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class exists for the Grid to easily reference triangular properties of its cells.
 *
 * @author George Hong
 */
public class TriangleStructure extends CellStructure {

  /**
   * Provides the Moore Neighborhood equivalent for Triangular Cells.  This results in a list of 12
   * possible positions, with reference to (row, col)
   *
   * @param row row index of the desired cell at the center of each neighborhood
   * @param col column index of the desired cell at the center of each neighborhood
   * @return List of Coordinates of each neighbor of the coordinate (row, col).  This list is freely
   * mutable.
   */
  @Override
  public List<Coordinate> getAllNeighboringCoordinates(int row, int col) {
    List<TriangularDirection> directions;
    List<Coordinate> allNeighboringCoordinates = new ArrayList<>();
    if ((row + col) % 2 == 0) {
      directions = TriangularDirection.EVEN_1.getEvenConfigurationDirections();
    } else {
      directions = TriangularDirection.EVEN_1.getOddConfigurationDirections();
    }
    for (TriangularDirection d : directions) {
      allNeighboringCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allNeighboringCoordinates;
  }

  /**
   * Provides all adjacent neighbors of this cell.  Due to the Triangular Structure, only 3
   * positions are considered directly adjacent.
   *
   * @param row row index of the desired cell at the center of each neighborhood
   * @param col column index of the desired cell at the center of each neighborhood
   * @return List of Coordinates corresponding to all Adjacent positions of (row, col)
   */
  @Override
  public List<Coordinate> getAllAdjacentCoordinates(int row, int col) {
    List<Coordinate> allAdjacentCoordinates = new ArrayList<>();
    for (Direction d : TriangularDirection.ODD_1.getAdjacentDirections(row, col)) {
      allAdjacentCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allAdjacentCoordinates;
  }

  /**
   * Randomly returns 1 out of the 12 valid directions for Hexagonal Cells.
   *
   * @param row row index to help distinguish valid directions
   * @param col column index to help distinguish valid directions
   * @return random valid Triangular Direction.
   */
  @Override
  public Direction getRandomDirection(int row, int col) {
    TriangularDirection[] directions = TriangularDirection.values();
    int length = directions.length;
    return directions[ThreadLocalRandom.current().nextInt(0, length)];
  }

  /**
   * Returns int encoding for this cell structure for frontend use
   *
   * @return 1, corresponding to this triangular cell structure.
   */
  public int getCode() {
    return 1;
  }
}
