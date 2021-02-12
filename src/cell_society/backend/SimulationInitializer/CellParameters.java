package cell_society.backend.SimulationInitializer;

import java.util.Map;

public class CellParameters {

  private Map<String, String> SIMULATION_MAP;

  public CellParameters(Map<String, String> userMap){
    SIMULATION_MAP = userMap;
  }

  public double getAsDouble(String key){
    return Double.parseDouble(SIMULATION_MAP.get(key));
  }

  public Integer getAsInt(String key){
    return Integer.parseInt(SIMULATION_MAP.get(key));
  }

  public String getAsString(String key){
    return SIMULATION_MAP.get(key);
  }

}
