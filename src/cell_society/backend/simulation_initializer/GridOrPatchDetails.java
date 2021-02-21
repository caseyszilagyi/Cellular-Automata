package cell_society.backend.simulation_initializer;

import java.util.Map;

public class GridOrPatchDetails {

  private String TYPE;
  private int GRID_HEIGHT;
  private int GRID_WIDTH;
  private String GRID;
  private Map<String, String> PARAMETERS;
  private Map<String, String> CODES;
  private Map<String, String> DECODER;

  public GridOrPatchDetails(String type, int width, int height, String grid,
      Map<String, String> parameters, Map<String, String> codes, Map<String, String> decoder) {
    TYPE = type;
    GRID_WIDTH = width;
    GRID_HEIGHT = height;
    GRID = grid;
    PARAMETERS = parameters;
    CODES = codes;
    DECODER = decoder;
  }

  public String getType() {
    return TYPE;
  }

  public int getGridHeight() {
    return GRID_HEIGHT;
  }

  public int getGridWidth() {
    return GRID_WIDTH;
  }

  public String getGrid() {
    return GRID;
  }

  public Map<String, String> getParameters() {
    return PARAMETERS;
  }

  public Map<String, String> getCodes() {
    return CODES;
  }

  public Map<String, String> getDecoder() {
    return DECODER;
  }

}
