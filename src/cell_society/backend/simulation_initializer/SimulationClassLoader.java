package cell_society.backend.simulation_initializer;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.CellStructure;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_stepper.SimulationStepper;
import cell_society.controller.ErrorHandler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/** A class designed to deal with the loading of different classes and to handle all of the
 * errors that comes along with it. Can be used to make cells, grids, steppers, and cellStructures
 *
 * @author Casey Szilagyi
 */
public class SimulationClassLoader {

  private String STEPPER_LOCATION = "cell_society.backend.simulation_stepper.";
  private String GRID_LOCATION = "cell_society.backend.automata.grid_styles.";
  private String CELL_LOCATION;
  private String PACKAGE_LOCATION = "cell_society.backend.automata.";

  private ClassLoader classLoader = new ClassLoader() {
  };

  /**
   * Sets the specific simulation we are dealing with
   * @param simulationType The simulation type
   */
  public SimulationClassLoader(String simulationType){
    CELL_LOCATION = PACKAGE_LOCATION + simulationType + ".";

  }

  /**
   * Makes a stepper of the specified type
   * @param stepperType The stepper type
   * @return The stepper
   */
  public SimulationStepper makeStepper(String stepperType) {
    SimulationStepper myStepper = null;
    try {
      Object stepper = classLoader
          .loadClass(STEPPER_LOCATION + stepperType)
          .getDeclaredConstructor().newInstance();
      myStepper = (SimulationStepper) stepper;
    } catch(Exception e){
      throw new ErrorHandler("InvalidStepperType");
    }

    return myStepper;
  }

  /**
   * Makes a grid of the specified type
   * @param gridType The grid type
   * @return The grid
   */
  public Grid makeGrid(String gridType) {
    Grid myGrid = null;
    try {
      Object grid = classLoader
          .loadClass(GRID_LOCATION + gridType)
          .getDeclaredConstructor().newInstance();
      myGrid = (Grid) grid;
    } catch (Exception e) {
      throw new ErrorHandler("InvalidGridType");
    }
    return myGrid;
  }

  /**
   * Makes a cell of the specified type
   * @param cellType The cell type
   * @return The cell
   */
  public Cell makeCell(String cellType, CellParameters cellParameters) {
    if(cellType == null){
      throw new ErrorHandler("InvalidCellMapping");
    }
    if(cellType.equals("Empty")){
      return null;
    }
    Cell myCell = null;
    try {
      Object cell = classLoader
          .loadClass(CELL_LOCATION + cellType)
          .getDeclaredConstructor().newInstance();
      myCell = (Cell) cell;
    } catch (Exception e) {
      throw new ErrorHandler("InvalidCellType");
    }

    myCell.initializeParams(cellParameters);
    return myCell;
  }

  /**
   * Makes a cellStructure of the specified type
   * @param structureType The cellStructure type
   * @return The CellStructure
   */
  public CellStructure makeCellStructure(String structureType) {
    CellStructure myStructure = null;
    try {
      Object cellStructure = classLoader
          .loadClass(GRID_LOCATION + structureType +"Structure")
          .getDeclaredConstructor().newInstance();
      myStructure = (CellStructure) cellStructure;
    } catch (Exception e) {
      throw new ErrorHandler("InvalidStructureType");
    }
    return myStructure;
  }


}
