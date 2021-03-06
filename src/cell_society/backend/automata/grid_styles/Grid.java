package cell_society.backend.automata.grid_styles;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.CellStructure;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.Patch;
import cell_society.backend.simulation_initializer.CellParameters;
import cell_society.controller.ErrorHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Holds all Cells in a simulation.  By default, the Grid has square cells.
 */
public class Grid {

  private Cell[][] grid;
  private Patch[][] gridStates;
  private CellStructure gridCellStructure = new SquareStructure();

  private int gridHeight;
  private int gridWidth;
  private Map<Character, String> colorCodes;
  private Map<String, String> cellDecoder;

  /**
   * Constructs an instance of the Grid class
   *
   * @param gridHeight number of rows in the Grid
   * @param gridWidth  number of columns in the Grid
   */
  public Grid(int gridHeight, int gridWidth) {
    grid = new Cell[gridHeight][gridWidth];
    gridStates = new Patch[gridHeight][gridWidth];
    this.gridHeight = gridHeight;
    this.gridWidth = gridWidth;
  }

  /**
   * Copy constructor for the stepper to use to make a new grid with ease, transferring tools for
   * communication with frontend.
   *
   * @param grid The last grid
   */
  public Grid(Grid grid) {
    this(grid.gridHeight, grid.gridWidth);
    this.colorCodes = grid.colorCodes;
    this.cellDecoder = grid.cellDecoder;
    this.gridCellStructure = grid.gridCellStructure;
    //updateRemainingPatches(grid);
  }

  /**
   * Empty constructor for newInstance method use
   */
  public Grid() {
  }

  /**
   * Gets the current Grid Cell Structure.  The gridCellStructure has no instance variables to
   * modify and is provided as a utility to compute points and obtain directions.
   *
   * @return cell structure of this Grid
   */
  public CellStructure getGridCellStructure() {
    return gridCellStructure;
  }

  /**
   * Alter how neighbors are determined
   *
   * @param structure CellStructure to use (HexagonStructure, SquareStructure, TriangularStructure)
   */
  public void setGridCellStructure(CellStructure structure) {
    gridCellStructure = structure;
  }

  /**
   * Used to make the grid and establish width/height
   *
   * @param width  The grid width
   * @param height The grid height
   */
  public void makeGrid(int width, int height) {
    grid = new Cell[height][width];
    gridStates = new Patch[height][width];
    gridWidth = width;
    gridHeight = height;
  }

  /**
   * Get the number of rows in the grid
   *
   * @return number of rows in the grid
   */
  public int getGridHeight() {
    return gridHeight;
  }

  /**
   * Get the number of columns in the grid
   *
   * @return number of columns in the grid
   */
  public int getGridWidth() {
    return gridWidth;
  }

  /**
   * Sets the color code for the Cells in the grid
   *
   * @param userColorCodes maps each character representation of a Cell to a String of an integer,
   *                       representing a unique type
   */
  public void setColorCodes(Map<Character, String> userColorCodes) {
    colorCodes = userColorCodes;
  }

  /**
   * Sets the map of each Cell name to a corresponding int, saved as a String
   *
   * @param userCellDecoder Map to be used to decode Cells
   */
  public void setCellDecoder(Map<String, String> userCellDecoder) {
    cellDecoder = userCellDecoder;
  }

  /**
   * Returns All cells neighboring the selected position, within one block, on the Grid.  This
   * produces a maximum of 8 neighbors for a square cell structure.
   *
   * @param row row index of grid to identify surrounding neighbors
   * @param col column index of grid to identify surrounding neighbors
   * @return Neighbors object containing the neighboring objects
   */
  public Neighbors getDirectNeighbors(int row, int col) {
    Neighbors neighbors = new Neighbors();
    for (Coordinate coordinate : gridCellStructure.getAllNeighboringCoordinates(row, col)) {
      int newRow = coordinate.getFirst();
      int newCol = coordinate.getSecond();
      if (inBoundaries(newRow, newCol)) {
        //neighbors.add(grid[newRow][newCol]);
        neighbors.add(getCell(newRow, newCol));
      }
    }
    return neighbors;
  }

  /**
   * Returns all cells adjacent to the selected position, within one block, on the Grid.  This has
   * the potential to result in a cross search pattern, producing a maximum of 4 neighbors for a
   * square grid.
   *
   * @param row row index of grid to identify surrounding neighbors
   * @param col column index of grid to identify surrounding neighbors
   * @return Neighbors object containing the neighboring objects.
   */
  public Neighbors getAdjacentNeighbors(int row, int col) {
    Neighbors neighbors = new Neighbors();
    for (Coordinate coordinate : gridCellStructure.getAllAdjacentCoordinates(row, col)) {
      int newRow = coordinate.getFirst();
      int newCol = coordinate.getSecond();
      if (inBoundaries(newRow, newCol)) {
        //neighbors.add(grid[newRow][newCol]);
        neighbors.add(getCell(newRow, newCol));
      }
    }
    return neighbors;
  }

  /**
   * Gets a list of all spots in the grid, unoccupied by other cells.  This is useful for mass Cell
   * relocations.  The returned list can be mutated as desired.
   *
   * @return List of Coordinates currently unoccupied by other cells.
   */
  public List<Coordinate> getAllVacantSpots() {
    List<Coordinate> vacantCoordinates = new LinkedList<>();
    for (int j = 0; j < gridHeight; j++) {
      for (int k = 0; k < gridWidth; k++) {
        if (isEmpty(j, k)) {
          Coordinate coordinate = new Coordinate(j, k);
          vacantCoordinates.add(coordinate);
        }
      }
    }
    return vacantCoordinates;
  }

  /**
   * Determines whether the specified position contains cell
   *
   * @param row row index of the position on the grid to check
   * @param col column index of the position on the grid to check
   * @return boolean representing whether the chosen position is occupied.
   */
  public boolean isEmpty(int row, int col) {
    return grid[row][col] == null;
  }

  /**
   * Determines whether the specified coordinate contains a cell
   *
   * @param coord
   * @return
   */
  public boolean isEmpty(Coordinate coord) {
    return isEmpty(coord.getFirst(), coord.getSecond());
  }

  /**
   * This method provides a quick check that a provided coordinate is within the bounds of the
   * Grid.
   *
   * @param row row index of the coordinate to check
   * @param col column index of the coordinate to check
   * @return boolean representing whether the provided row and column indexes are within the bounds
   * of the grid.
   */
  public boolean inBoundaries(int row, int col) {
    return !(row >= gridHeight || row < 0 || col >= gridWidth || col < 0);
  }

  /**
   * Checks that the provided coordinate object is within the bounds of the Grid.
   *
   * @param coord coordinate object
   * @return boolean representing whether coordinate is within the bounds of the grid.
   */
  public boolean inBoundaries(Coordinate coord) {
    int row = coord.getFirst();
    int col = coord.getSecond();
    return inBoundaries(row, col);
  }

  /**
   * Retrieve the cell object contained at the specified position
   *
   * @param row desired row index of grid
   * @param col desired column index of grid
   * @return Cell or null object contained at the specified location.
   */
  public Cell getCell(int row, int col) {
    return grid[row][col];
  }

  /**
   * place a Cell into the desired coordinates.  Indices must be positive and within the bounds of
   * the grid.
   *
   * @param row  row index of where to place the cell
   * @param col  column index of where to place the cell
   * @param cell cell object to be placed.
   */
  public void placeCell(int row, int col, Cell cell) {
    grid[row][col] = cell;
  }

  /**
   * Returns the Patch object currently contained at the Grid.
   *
   * @param row row index of Patch to inspect
   * @param col column index of Patch to inspect
   * @return Patch object
   */
  public Patch getPatch(int row, int col) {
    return gridStates[row][col];
  }

  /**
   * place a Patch into the desired coordinates.  Indices must be positive and within the bounds of
   * the grid.
   *
   * @param row   row index of where to place the Patch
   * @param col   column index of where to place the Patch
   * @param patch Patch object to be placed
   */
  public void placePatch(int row, int col, Patch patch) {
    gridStates[row][col] = patch;
  }

  /**
   * List of coordinates containing cells that should be updated.
   *
   * @return List of coordinate objects
   */
  public List<Coordinate> getCoordinateUpdateList() {
    List<Coordinate> coordinateList = new ArrayList<>();
    for (int j = 0; j < gridHeight; j++) {
      for (int k = 0; k < gridWidth; k++) {
        coordinateList.add(new Coordinate(j, k));
      }
    }
    return coordinateList;
  }

  /**
   * List of coordinates containing patches that should be updated
   *
   * @return List of coordinate objects
   */
  protected List<Coordinate> getPatchUpdateList() {
    List<Coordinate> coordinateList = new ArrayList<>();
    for (int j = 0; j < gridHeight; j++) {
      for (int k = 0; k < gridWidth; k++) {
        coordinateList.add(new Coordinate(j, k));
      }
    }
    return coordinateList;
  }

  /**
   * This method lifts the patch states from an earlier state and makes them available to the
   * current Grid.
   *
   * @param otherGrid previous Grid object
   */
  public void updateRemainingPatches(Grid otherGrid) {
    List<Coordinate> coordinateList = otherGrid.getCoordinateUpdateList();
    for (Coordinate coord : coordinateList) {
      int row = coord.getFirst();
      int col = coord.getSecond();
      if (getPatch(row, col) == null && otherGrid.getPatch(row, col) != null) {
        placePatch(row, col, otherGrid.getPatch(row, col).copy());
      }
    }
  }

  /**
   * Gets the string code representation of the cells to pass to the display
   *
   * @return A 2D array of string codes.
   */
  public int[] getIntDisplay() {
    int[] display = new int[gridHeight * gridWidth + 3];
    display[0] = gridCellStructure.getCode();
    display[1] = getGridHeight();
    display[2] = getGridWidth();
    int i = 3;
    for (int j = 0; j < gridHeight; j++) {
      for (int k = 0; k < gridWidth; k++) {
        if (grid[j][k] != null) {
          int curr;
          try {
            curr = Integer.parseInt(cellDecoder.get(grid[j][k].toString()));
          } catch (Exception e) {
            throw new ErrorHandler("InvalidCellMapping");
          }
          display[i] = curr;
        } else {
          display[i] = 0;
        }
        i++;
      }
    }
    return display;
  }

  /**
   * Calculate the distribution of each type of cell.
   *
   * @return integer code representing each cell type and the frequency of those cells in this grid.
   */
  public Map<Integer, Integer> getCellDistribution() {
    Map<Integer, Integer> map = new HashMap<>();
    for (Coordinate coordinate : getCoordinateUpdateList()) {
      int row = coordinate.getFirst();
      int col = coordinate.getSecond();
      Cell cell = getCell(row, col);
      int curr = cell == null ? 0 : Integer.parseInt(cellDecoder.get(cell.toString()));
      map.putIfAbsent(curr, 0);
      map.put(curr, map.get(curr) + 1);
    }
    return map;
  }

  /**
   * Updates the cell parameters
   *
   * @param paramMap The map of the cell parameters
   */
  public void updateCellParameters(Map<String, String> paramMap) {
    CellParameters cellParameters = new CellParameters(paramMap);
    for (Coordinate c : getCoordinateUpdateList()) {
      Cell current = getCell(c.getFirst(), c.getSecond());
      if (current == null) {
        continue;
      }
      current.initializeParams(cellParameters);
    }
  }

}
