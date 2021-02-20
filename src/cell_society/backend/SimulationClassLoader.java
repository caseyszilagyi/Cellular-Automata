package cell_society.backend;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.grid.Grid;
import cell_society.backend.simulation_stepper.SimulationStepper;
import cell_society.controller.ErrorHandler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/** A class designed to deal with the loading of different classes and to handle all of the
 * errors that comes along with it. Can be used to make cells, grids, steppers
 *
 * @author Casey Szilagyi
 */
public class SimulationClassLoader extends ClassLoader{

  private String STEPPER_LOCATION = "cell_society.backend.simulation_stepper.";
  private String GRID_LOCATION = "cell_society.backend.automata.grid.";
  private String CELL_LOCATION;
  private String PACKAGE_LOCATION = "cell_society.backend.automata.";


  public SimulationClassLoader(String simulationType){
    CELL_LOCATION = PACKAGE_LOCATION + simulationType + ".";
  }


  /**
   * Makes a grid of the given class
   * @param stepperType The class name of the grid we want to make
   * @return The grid
   */
  public Class makeStepper(String stepperType){
    byte[] b;
    try {
      b = loadClassData(stepperType, STEPPER_LOCATION);
    } catch(Exception e){
      throw new ErrorHandler("InvalidStepperClass");
    }


    Class stepper = defineClass(STEPPER_LOCATION +stepperType, b, 0, b.length);
    /**
    SimulationStepper result;
    try{
      result = (SimulationStepper) stepper.getDeclaredConstructor().newInstance();
    } catch(Exception e){
      throw new ErrorHandler(e);
    }
    */
    return stepper;
  }

  /**
   * Makes a grid of the given class
   * @param gridType The class name of the grid we want to make
   * @return The grid
   */
  public Grid makeGrid(String gridType){
    byte[] b;
    try {
      b = loadClassData(gridType, GRID_LOCATION);
    } catch(Exception e){
      throw new ErrorHandler("InvalidGridClass");
    }

    Class grid = defineClass(GRID_LOCATION + gridType, b, 0, b.length);
    Grid result;
    try{
      result = (Grid) grid.getDeclaredConstructor().newInstance();
    } catch(Exception e){
      throw new ErrorHandler("InvalidGridClass");
    }

    return result;
  }

  /**
   * Makes a cell of the specified class type
   * @param cellType The type of cell
   * @return The cell
   */
  public Cell makeCell(String cellType){
    byte[] b;
    try {
      b = loadClassData(cellType, CELL_LOCATION);
    } catch(Exception e){
      throw new ErrorHandler("InvalidCellClass");
    }

    Class cell = defineClass(CELL_LOCATION + cellType, b, 0, b.length);
    Cell result;
    try{
      result = (Cell) cell.getDeclaredConstructor().newInstance();
    } catch(Exception e){
      throw new ErrorHandler("InvalidCellClass");
    }

    return result;
  }

  public Class findClass(String className){
    byte[] b = null;
    try {
      b = loadClassData("Grid", GRID_LOCATION);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return defineClass(GRID_LOCATION + "Grid", b, 0, b.length);
  }

  public byte[] loadClassData(String className, String path) throws Exception{
    path = path+className;
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
        path.replace('.', File.separatorChar) + ".class");
    byte[] buffer;
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    int nextValue = 0;

      while ( (nextValue = inputStream.read()) != -1 ) {
        byteStream.write(nextValue);
      }

    buffer = byteStream.toByteArray();
    return buffer;
  }

  // Testing
  public static void main(String[] args) throws Exception {
    ClassLoader classLoader = new ClassLoader() {
    };
    Object grido = classLoader.loadClass("cell_society.backend.automata.grid.Grid").getDeclaredConstructor().newInstance();
    Grid griddy = (Grid) grido;
    griddy.makeGrid(3,4);


    SimulationClassLoader my = new SimulationClassLoader("game_of_life");
    //Grid grid = my.makeGrid("Grid");
    //Cell cell = my.makeCell("AliveCell");
    System.out.println();

    Object mine = my.loadClass("Grid").newInstance();
    Grid grid = (Grid) mine;

    Class stepper = my.makeStepper("SimulationStepper");
    SimulationStepper result;
    try{
      result = (SimulationStepper) stepper.newInstance();
    } catch(Exception e){
      throw new ErrorHandler(e);
    }

    //System.out.println(cell.toString());
  }

}
