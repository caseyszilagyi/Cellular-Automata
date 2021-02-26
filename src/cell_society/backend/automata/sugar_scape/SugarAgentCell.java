package cell_society.backend.automata.sugar_scape;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_initializer.CellParameters;
import java.util.List;

/**
 * Represents an Agent in the SugarScape simulation.
 *
 * @author George Hong
 */
public class SugarAgentCell extends Cell {

  public static final String SUGAR_METABOLISM = "sugarmetabolism";
  public static final String SUGAR = "sugar";
  public static final String VISION = "vision";
  private int sugarMetabolism;
  private int sugar;
  private int vision;

  /**
   * Initializes an instance of SugarAgent
   *
   * @param row             row index resided in by the SugarAgent
   * @param col             column index resided in by the SugarAgent
   * @param sugarMetabolism amount of sugar automatically consumed each step by the SugarAgent
   * @param sugar           starting amount of sugar possessed by the SugarAgent
   * @param vision          (not used), intended to increase the line of sight of possible patches
   *                        for the SugarAgent to relocate to.
   */
  public SugarAgentCell(int row, int col, int sugarMetabolism, int sugar, int vision) {
    super(row, col);
    this.sugar = sugar;
    this.sugarMetabolism = sugarMetabolism;
    this.vision = 1;
  }

  /**
   * Initializes an instance of SugarAgent with no parameters, intended for use with the XML Grid
   * initializer.
   */
  public SugarAgentCell() {

  }

  /**
   * Intended for use with the parameter-less constructor to set the Agent properties.
   *
   * @param parameters parameters object containing Agent properties
   */
  @Override
  public void initializeParams(CellParameters parameters) {
    sugarMetabolism = parameters.getAsInt(SUGAR_METABOLISM);
    sugar = parameters.getAsInt(SUGAR);
    vision = parameters.getAsInt(VISION);
  }

  /**
   * The SugarAgent considers all adjacent locations to be a neighbor.
   *
   * @param grid grid holding the current configuration of cells
   * @return Neighbors object containing all neighbors to the SugarAgent
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = getRow();
    int col = getCol();
    return grid.getAdjacentNeighbors(row, col);
  }

  /**
   * The SugarAgent considers all adjacent coordinates to move to, and selects the unoccupied
   * position with the most Sugar available.  It obtains all sugar contained in the patch it moves
   * to, before applying its metabolism rules.  If its metabolism drops to zero, the SugarAgent
   * dies.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid Grid containing the current configuration of cells
   * @param nextGrid    Grid to contain the next configuration of cells
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    // Doesn't need neighbors.  Coordinates are more significant.
    List<Coordinate> coordinates = currentGrid.getGridCellStructure()
        .getAllAdjacentCoordinates(getRow(), getCol());
    Coordinate maxPatch = null;
    int maxSugar = -1;
    maxPatch = getHighestSugarCoordinate(currentGrid, nextGrid, coordinates, maxPatch, maxSugar);

    int nextRow = maxPatch == null ? getRow() : maxPatch.getFirst();
    int nextCol = maxPatch == null ? getCol() : maxPatch.getSecond();
    Patch sugarPatch = nextGrid.getPatch(nextRow, nextCol);
    int sugarGain = sugarPatch.getState(SugarPatch.SUGAR);
    sugarPatch.setState(SugarPatch.SUGAR, 0);
    sugar += (sugarGain - sugarMetabolism);
    // Dies, do not persist into the next round
    if (sugar <= 0) {
      return;
    }
    nextGrid.placeCell(nextRow, nextCol,
        new SugarAgentCell(nextRow, nextCol, sugarMetabolism, sugar, vision));
  }

  private Coordinate getHighestSugarCoordinate(Grid currentGrid, Grid nextGrid,
      List<Coordinate> coordinates,
      Coordinate maxPatch, int maxSugar) {
    for (Coordinate coordinate : coordinates) {
      int coordRow = coordinate.getFirst();
      int coordCol = coordinate.getSecond();
      // Must make sure point off the grid is not chosen
      if (!nextGrid.inBoundaries(coordRow, coordCol)) {
        continue;
      }
      // Patch has the potential to be null
      if (currentGrid.getPatch(coordRow, coordCol) == null) {
        continue;
      }
      int coordSugar = currentGrid.getPatch(coordRow, coordCol).getState(SugarPatch.SUGAR);
      if (coordSugar > maxSugar && nextGrid.isEmpty(coordRow, coordCol)) {
        maxPatch = coordinate;
        maxSugar = coordSugar;
      }
    }
    return maxPatch;
  }

  /**
   * @return String "@" for agent, sugar
   * @Deprecated Returns the length 1 string representation of this SugarAgent
   */
  @Override
  public String getGridRepresentation() {
    return "@";
  }

  /**
   * Returns the String representation of this class, for use with the CellDecoder
   *
   * @return String "SugarAgentCell"
   */
  public String toString() {
    return "SugarAgentCell";
  }
}
