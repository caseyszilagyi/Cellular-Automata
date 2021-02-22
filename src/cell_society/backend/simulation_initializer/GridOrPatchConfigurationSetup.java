package cell_society.backend.simulation_initializer;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class GridOrPatchConfigurationSetup {

  private String SIMULATION_TYPE;
  private String GRID_OR_PATCH;
  private int GRID_HEIGHT;
  private int GRID_WIDTH;
  private String grid;
  private Map<String, String> PARAMETERS;
  private Map<String, String> CODES;
  private Map<String, String> DECODER;
  private Map<String, String> PROBABILITIES;
  private Map<String, double[]> frontEndParameterSpecifications = new HashMap<>();
  private XMLFileReader xmlFileReader=new XMLFileReader();

  private String DEFAULT_PATH = "data/default_values/";

  public GridOrPatchConfigurationSetup(String simType, String type, int width, int height, String userGrid,
      Map<String, String> parameters, Map<String, String> codes, Map<String, String> decoder, Map<String, String> probabilities) {
    SIMULATION_TYPE = simType;
    GRID_OR_PATCH = type;
    GRID_WIDTH = width;
    GRID_HEIGHT = height;
    grid = userGrid;
    PARAMETERS = parameters;
    CODES = codes;
    DECODER = decoder;
    PROBABILITIES = probabilities;
    setUpGrid();
    setupFrontEndParameters();
  }

  private void setupFrontEndParameters(){
    xmlFileReader.setFile(DEFAULT_PATH + SIMULATION_TYPE + ".xml");
    xmlFileReader.setSimulationType(SIMULATION_TYPE);
    Map<String, String> defaultValues = xmlFileReader.getAttributeMap("parameters");

    for(String s: defaultValues.keySet()){
      String[] stringOfParameters = defaultValues.get(s).split(",");
      double[] parameters = new double[stringOfParameters.length];
      for(int i = 0; i<parameters.length; i++){
        parameters[i] = Double.parseDouble(stringOfParameters[i]);
      }
      frontEndParameterSpecifications.put(s, parameters);
    }

    for(String s:frontEndParameterSpecifications.keySet()){
      if(!PARAMETERS.containsKey(s)){
        PARAMETERS.put(s, Double.toString(frontEndParameterSpecifications.get(s)[2]));
      }
      else{
        frontEndParameterSpecifications.get(s)[2] = Double.parseDouble(PARAMETERS.get(s));
      }
    }
  }

  private void parseGrid(){
    grid = grid.replace("\n", "").replace(" ", "");
  }

  private void setUpGrid(){
    parseGrid();
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
    return GRID_OR_PATCH;
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

  public Map getFrontEndParameterSpecifications(){
    return frontEndParameterSpecifications;
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
