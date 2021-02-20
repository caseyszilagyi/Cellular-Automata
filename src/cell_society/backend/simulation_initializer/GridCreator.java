package cell_society.backend.simulation_initializer;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.controller.ErrorHandler;
import java.util.Map;

/**
 * Makes and populates the grid that is used to hold all of the cells present in the simulation.
 *
 * @author Casey Szilagyi
 */
public class GridCreator {

  private Grid simulationGrid;
  private final String PACKAGE_LOCATION = "cell_society.backend.automata.";
  private final String SIMULATION_TYPE;
  private final CellParameters CELL_PARAMETERS;
  private final SimulationClassLoader CLASS_LOADER;

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
  public GridCreator(int row, int col, String simulationType, String gridType, CellParameters cellParameters, SimulationClassLoader classLoader) {
    SIMULATION_TYPE = simulationType;
    CLASS_LOADER = classLoader;
    simulationGrid = makeGrid(row, col, gridType);
    CELL_PARAMETERS = cellParameters;
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
    Grid newGrid = CLASS_LOADER.makeGrid(gridType);
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
    grid = parseGrid(grid);
    if(grid.length() != simulationGrid.getGridHeight() * simulationGrid.getGridWidth()){
      throw new ErrorHandler("IncorrectGridSpecification");
    }

    int i = 0;
    for (int r = 0; r < simulationGrid.getGridHeight(); r++) {
      for (int c = 0; c < simulationGrid.getGridWidth(); c++) {
        Cell newCell = CLASS_LOADER.makeCell(cellCodes.get(Character.toString(grid.charAt(i))), CELL_PARAMETERS);
        if(newCell != null) {
          newCell.setPosition(r, c);
          simulationGrid.placeCell(r, c, newCell);
        }
        i++;
      }
    }
  }

  //Parses the grid to get a string that only has the necessary characters
  private String parseGrid(String grid) {
    return grid.replace("\n", "").replace(" ", "");
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
