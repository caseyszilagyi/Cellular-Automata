package cell_society.backend.simulation_initializer;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;
import java.util.Map;

/**
 * Makes and populates the grid that is used to hold all of the cells present in the simulation.
 *
 * @author Casey Szilagyi
 */
public class GridCreator {

  private Grid gameGrid;
  private CellParameters cellBehavior;
  private String CELL_LOCATION = "cell_society.backend.automata.";
  private String SIMULATION_TYPE;

  /**
   * Makes the grid
   *
   * @param row Number of rows
   * @param col Number of columns
   */
  public GridCreator(int row, int col, String simulationType) {
    gameGrid = new Grid(row, col);
    SIMULATION_TYPE = simulationType + ".";
  }

  /**
   * Populates the grid with cell objects
   *
   * @param grid      A string of characters that defines which cells will be made
   * @param cellCodes Links each character to a cell type
   */
  public void populateGrid(String grid, Map<String, String> cellCodes) {
    grid = parseGrid(grid);
    int i = 0;
    for (int r = 0; r < gameGrid.getGridHeight(); r++) {
      for (int c = 0; c < gameGrid.getGridWidth(); c++) {
        Cell newCell = makeCell(cellCodes.get(Character.toString(grid.charAt(i))));
        newCell.setPosition(r,c);
        gameGrid.placeCell(r, c, newCell);
        i++;
      }
    }
  }

  /**
   * Makes a single cell with
   *
   * @param cellType The type of cell that we want to make
   * @return The initialized cell
   */
  public Cell makeCell(String cellType) {
    //e is an empty spot on the grid
    if (cellType.equals("e")) {
      return null;
    }
    String curr = CELL_LOCATION + SIMULATION_TYPE + cellType;
    Class classCell = null;
    try {
      classCell = Class.forName(curr);
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
    return gameGrid;
  }

}
