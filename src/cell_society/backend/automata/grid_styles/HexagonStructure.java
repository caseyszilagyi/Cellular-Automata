package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.CellStructure;
import cell_society.backend.automata.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class exists for the Grid to easily reference hexagonal properties of its cells.
 *
 * @author George Hong
 */

public class HexagonStructure extends CellStructure {

  /**
   * Provides the Moore Neighborhood equivalent for Hexagonal Cells, resulting in a list of ? 6
   * coordinates.
   *
   * @param row row index of the desired cell at the center of each neighborhood
   * @param col column index of the desired cell at the center of each neighborhood
   * @return List of Coordinates of each neighbor of the coordinate (row, col).  This list is freely
   * mutable.
   */
  @Override
  public List<Coordinate> getAllNeighboringCoordinates(int row, int col) {
    List<Coordinate> allNeighboringCoordinates = new ArrayList<>();
    for (Direction d : HexagonalDirection.values()) {
      allNeighboringCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allNeighboringCoordinates;
  }

  /**
   * Provides all adjacent neighbors of a given type of cell.  Due to the Hexagonal Structure, all 6
   * Moore neighbors are considered directly Adjacent.
   *
   * @param row row index of the desired cell at the center of each neighborhood
   * @param col column index of the desired cell at the center of each neighborhood
   * @return List of Coordinates corresponding to all Adjacent positions of (row, col)
   */
  @Override
  public List<Coordinate> getAllAdjacentCoordinates(int row, int col) {
    List<Coordinate> allAdjacentCoordinates = new ArrayList<>();
    for (Direction d : HexagonalDirection.TOP_LEFT.getAdjacentDirections(row, col)) {
      allAdjacentCoordinates.add(d.getResultingCoordinate(row, col));
    }
    return allAdjacentCoordinates;
  }

  /**
   * Randomly returns 1 out of the 6 valid directions for Hexagonal cells.
   *
   * @param row row index to help distinguish valid directions
   * @param col column index to help distinguish valid directions
   * @return random valid Hexagonal Direction.
   */
  @Override
  public Direction getRandomDirection(int row, int col) {
    HexagonalDirection[] directions = HexagonalDirection.values();
    int length = directions.length;
    return directions[ThreadLocalRandom.current().nextInt(0, length)];
  }

  /**
   * Returns int encoding for this cell structure for frontend use
   *
   * @return 2, corresponding to this hexagonal cell structure
   */
  public int getCode() {
    return 2;
  }
}
