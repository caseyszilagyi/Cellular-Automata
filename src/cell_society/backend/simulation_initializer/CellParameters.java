package cell_society.backend.simulation_initializer;

import java.util.Map;

/**
 * A class that is used to contain the parameters that define a cells behavior. Is designed to be
 * flexible so that initializing any cell can be done with this class, and the parameters are
 * assigned in each cell's class
 *
 * @author Casey Szilagyi
 */
public class CellParameters {

  private Map<String, String> SIMULATION_MAP;

  /**
   * Assigns the map
   *
   * @param userMap Has the variables and values that each cell is defined by
   */
  public CellParameters(Map<String, String> userMap) {
    SIMULATION_MAP = userMap;
  }

  /**
   * Gets the parameter value as a double
   *
   * @param key The name of the parameter that defines the cell
   * @return The mapped value as a double
   */
  public double getAsDouble(String key) {
    return Double.parseDouble(SIMULATION_MAP.get(key));
  }

  /**
   * Gets the parameter value as an integer
   *
   * @param key The name of the parameter that defines the cell
   * @return The mapped value as a integer
   */
  public Integer getAsInt(String key) {
    return Integer.parseInt(SIMULATION_MAP.get(key));
  }

  /**
   * Gets the parameter value as a String
   *
   * @param key The name of the parameter that defines the cell
   * @return The mapped value as a string
   */
  public String getAsString(String key) {
    return SIMULATION_MAP.get(key);
  }

}
