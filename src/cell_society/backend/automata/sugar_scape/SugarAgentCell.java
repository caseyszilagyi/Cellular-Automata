package cell_society.backend.automata.sugar_scape;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Coordinate;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.Patch;
import cell_society.backend.automata.grid_styles.Grid;
import cell_society.backend.simulation_initializer.CellParameters;
import java.util.List;

/**
 * @author George Hong
 */
public class SugarAgentCell extends Cell {

  public static final String SUGAR_METABOLISM = "sugarmetabolism";
  public static final String SUGAR = "sugar";
  public static final String VISION = "vision";
  private int sugarMetabolism;
  private int sugar;
  private int vision;

  public SugarAgentCell(int row, int col, int sugarMetabolism, int sugar, int vision) {
    super(row, col);
    this.sugar = sugar;
    this.sugarMetabolism = sugarMetabolism;
    this.vision = 1;
  }

  public SugarAgentCell() {

  }

  @Override
  public void initializeParams(CellParameters parameters) {
    sugarMetabolism = parameters.getAsInt(SUGAR_METABOLISM);
    sugar = parameters.getAsInt(SUGAR);
    vision = parameters.getAsInt(VISION);
  }

  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = getRow();
    int col = getCol();
    return grid.getAdjacentNeighbors(row, col);
  }

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

  @Override
  public String getGridRepresentation() {
    return "@";
  }

  public String toString(){ return "SugarAgentCell"; }
}
