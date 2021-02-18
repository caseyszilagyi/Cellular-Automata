package cell_society.backend.simulation_initializer;

import cell_society.backend.automata.Cell;

/**
 * Makes cells based on the cell parameters and simulation type provided
 *
 * @author Casey Szilagyi
 */
public class CellCreator {

  private final String SIMULATIONS_LOCATION = "cell_society.backend.automata.";
  private final String PACKAGE_LOCATION;
  private final CellParameters CELL_PARAMETERS;

  public CellCreator(String simulationType, CellParameters cellParameters) {
    PACKAGE_LOCATION = SIMULATIONS_LOCATION + simulationType + ".";
    CELL_PARAMETERS = cellParameters;
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
    String cellFileLocation = PACKAGE_LOCATION + cellType;
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

    newCell.initializeParams(CELL_PARAMETERS);

    return newCell;
  }

}
