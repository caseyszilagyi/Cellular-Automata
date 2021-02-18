package cell_society.backend.simulation_initializer;

import cell_society.backend.Simulation;
import cell_society.backend.automata.Cell;
import cell_society.controller.ErrorHandler;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/** A class designed to deal with the loading of different classes and to handle all of the
 * errors that comes along with it. Can be used to make cells, grids, steppers
 *
 * @author Casey Szilagyi
 */
public class SimulationClassLoader extends ClassLoader{

  private String PACKAGE_LOCATION = "cell_society.backend.automata.";
  private String SPECIFIC_PACKAGE;

  public SimulationClassLoader(String simulationType){
    SPECIFIC_PACKAGE = PACKAGE_LOCATION + simulationType + ".";
  }

  public Cell makeCell(String cellType){
    byte[] b;
    try {
      b = loadClassData(cellType);
    } catch(Exception e){
      throw new ErrorHandler("InvalidCellClass");
    }

    Class cell = defineClass(SPECIFIC_PACKAGE + cellType, b, 0, b.length);
    Cell result;
    try{
      result = (Cell) cell.getDeclaredConstructor().newInstance();
    } catch(Exception e){
      throw new ErrorHandler("Fix this later");
    }

    return result;
  }

  public byte[] loadClassData(String className) throws Exception{

    String path = SPECIFIC_PACKAGE + className;
    /**
    File f = new File(path.replace('.', File.separatorChar) + ".class");
    DataInputStream is = new DataInputStream(new FileInputStream(f));
    int len = (int)f.length();
    byte[] buff = new byte[len];
    is.readFully(buff);
    is.close();
    return buff;
     */

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
  public static void main(String[] args){
    SimulationClassLoader my = new SimulationClassLoader("game_of_life");
    Cell cell = my.makeCell("AliveCell");
    System.out.println(cell.toString());
  }

}
