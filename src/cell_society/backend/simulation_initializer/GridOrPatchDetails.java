package cell_society.backend.simulation_initializer;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class GridOrPatchDetails {

  private String TYPE;
  private int GRID_HEIGHT;
  private int GRID_WIDTH;
  private String grid;
  private Map<String, String> PARAMETERS;
  private Map<String, String> CODES;
  private Map<String, String> DECODER;
  private Map<String, String> PROBABILITIES;

  private String DEFAULT_PATH = "data/default_values/";

  public GridOrPatchDetails(String type, int width, int height, String userGrid,
      Map<String, String> parameters, Map<String, String> codes, Map<String, String> decoder, Map<String, String> probabilities) {
    TYPE = type;
    GRID_WIDTH = width;
    GRID_HEIGHT = height;
    grid = userGrid;
    PARAMETERS = parameters;
    CODES = codes;
    DECODER = decoder;
    PROBABILITIES = probabilities;
    setUpGrid();
  }

  private void setUpGrid(){
    if(grid.replace("\n", "").replace(" ", "").equals("Random")){
      StringBuilder sb = new StringBuilder();
      RandomCollection<String> weights = populateWeights();
      for(int i = 0; i<GRID_HEIGHT*GRID_WIDTH; i++){
        sb.append(weights.next());
      }
      grid = sb.toString();
    }
  }

  private RandomCollection<String> populateWeights(){
    RandomCollection<String> weights = new RandomCollection();
    for(String s: PROBABILITIES.keySet()){
      weights.add(Double.parseDouble(PROBABILITIES.get(s)), s);
    }
    return weights;
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
    return grid;
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


  public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
      this(new Random());
    }

    public RandomCollection(Random random) {
      this.random = random;
    }

    public RandomCollection<E> add(double weight, E result) {
      if (weight <= 0) return this;
      total += weight;
      map.put(total, result);
      return this;
    }

    public E next() {
      double value = random.nextDouble() * total;
      return map.higherEntry(value).getValue();
    }
  }

}
