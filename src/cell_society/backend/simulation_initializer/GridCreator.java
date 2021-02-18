package cell_society.backend.simulation_initializer;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import cell_society.controller.ErrorHandler;
import java.util.Map;

/**
 * Makes and populates the grid that is used to hold all of the cells present in the simulation.
 *
 * @author Casey Szilagyi
 */
public class GridCreator {

  private Grid simulationGrid;
  private CellParameters cellBehavior;
  private String PACKAGE_LOCATION = "cell_society.backend.automata.";
  private String SIMULATION_TYPE;

  /**
   * Calls a method to make the grid, and sets up the simulation type so that the cells/grid can be
   * found in the correct package
   *
   * @param row            Number of rows
   * @param col            Number of columns
   * @param simulationType A string representing the type of simulation so that the package can be
   *                       found
   * @param gridType       A string representing the grid used to make the game
   */
  public GridCreator(int row, int col, String simulationType, String gridType) {
    SIMULATION_TYPE = simulationType + ".";
    simulationGrid = makeGrid(row, col, gridType);
  }

  /**
   * Method to make the grid based on the given grid type.
   *
   * @param row      The number of rows that the grid will have
   * @param col      The number of columns that the grid will have
   * @param gridType the string representing the class name of the grid
   * @return The initialized grid, completely empty
   */
  public Grid makeGrid(int row, int col, String gridType) {
    // Basic grid
    if (gridType.equals("Grid")) {
      return new Grid(row, col);
    }

    String gridFileLocation = PACKAGE_LOCATION + SIMULATION_TYPE + gridType;
    Class classGrid = null;
    try {
      classGrid = Class.forName(gridFileLocation);
    } catch (ClassNotFoundException e) {
      System.out
          .println("Error: Grid class name does not exist or is placed in the wrong location");
    }

    //Casting the generic class to a grid object
    Grid newGrid = null;
    try {
      newGrid = (Grid) classGrid.newInstance();
    } catch (Exception e) {
      System.out.println("Error: Grid casting");
    }

    newGrid.makeGrid(col, row);

    return newGrid;
  }


  /**
   * Populates the grid with cell objects
   *
   * @param grid      A string of characters that defines which cells will be made
   * @param cellCodes A map that links each character to a cell type
   */
  public void populateGrid(String grid, Map<String, String> cellCodes) {
    if(grid.length() != simulationGrid.getGridHeight() * simulationGrid.getGridWidth()){
      throw new ErrorHandler("IncorrectGridSpecification");
    }

    grid = parseGrid(grid);
    int i = 0;
    for (int r = 0; r < simulationGrid.getGridHeight(); r++) {
      for (int c = 0; c < simulationGrid.getGridWidth(); c++) {
        Cell newCell = makeCell(cellCodes.get(Character.toString(grid.charAt(i))));
        if(newCell != null) {
          newCell.setPosition(r, c);
          simulationGrid.placeCell(r, c, newCell);
        }
        i++;
      }
    }
  }

  /**
   * Makes a single cell of the given type
   *
   * @param cellType The type of cell that we want to make
   * @return The initialized cell
   */
  public Cell makeCell(String cellType) {
    //e is an empty spot on the grid
    if (cellType.equals("Empty")) {
      return null;
    }
    String cellFileLocation = PACKAGE_LOCATION + SIMULATION_TYPE + cellType;
    Class classCell = null;
    try {
      classCell = Class.forName(cellFileLocation);
    } catch (ClassNotFoundException e) {
      System.out
          .println("Error: Cell class name does not exist or is placed in the wrong location");
    }

    //Casting the generic class to a cell object
    Cell newCell = null;
    try {
      newCell = (Cell) classCell.newInstance();
    } catch (Exception e) {
      System.out.println("Error: Cell casting");
    }

    newCell.initializeParams(cellBehavior);

    return newCell;
  }

  //Parses the grid to get a string that only has the necessary characters
  private String parseGrid(String grid) {
    return grid.replace("\n", "").replace(" ", "");
  }

  /**
   * Sets the behavior that each cell will be initialized with
   *
   * @param cellParameters The parameters that define the cell
   */
  public void setCellBehavior(CellParameters cellParameters) {
    cellBehavior = cellParameters;
  }

  /**
   * Gets the grid that has been initialized
   *
   * @return The grid
   */
  public Grid getGrid() {
    return simulationGrid;
  }

  public void setColorCodes(Map<Character, String> colorCodes){
    simulationGrid.setColorCodes(colorCodes);
  }

  public void setCellDecoder(Map<String, String> cellDecoder){
    simulationGrid.setCellDecoder(cellDecoder);
  }

}
