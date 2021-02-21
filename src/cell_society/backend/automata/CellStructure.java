package cell_society.backend.automata;

import cell_society.backend.automata.grid_styles.Direction;
import java.util.List;

/**
 * This class exists for a Grid to easily reference the cell neighborhood properties.  For each Cell
 * Structure class, a corresponding enum that implements Direction must be created to encode the
 * directions.  This class is designed to be used in conjunction with the bounds-checking
 * functionality of the Grid class.
 */
public abstract class CellStructure {

  /**
   * Provides the standard neighborhood of a given type of cell.  The list can be freely modified by
   * the caller because coordinates are generated at each call.
   *
   * @param row row index of the desired cell at the center of each neighborhood
   * @param col column index of the desired cell at the center of each neighborhood
   * @return List of coordinates that contain each Coordinate of neighbors
   */
  public abstract List<Coordinate> getAllNeighboringCoordinates(int row, int col);

  /**
   * Provides all adjacent neighbors of a given type of cell.  This List will be a subset of all
   * neighbors.  The returned list can be freely modified by the caller because coordinates are
   * generated at each call.
   *
   * @param row row index of the desired cell at the center of each neighborhood
   * @param col column index of the desired cell at the center of each neighborhood
   * @return List of coordinates that contain each Coordinate of neighbors
   */
  public abstract List<Coordinate> getAllAdjacentCoordinates(int row, int col);

  public abstract Direction getRandomDirection(int row, int col);


  /**
   * This method is used in order to get a code to pass to the front end so that it knows what shape
   * it is dealing with
   *
   * @return The int representing the cell structure
   */
  public abstract int getCode();




}