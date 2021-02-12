package cell_society.backend.SimulationInitializer;

import cell_society.backend.Simulation;
import cell_society.backend.automata.Cell;
import cell_society.backend.automata.game_of_life.DeadCell;
import cell_society.backend.automata.Grid;
import java.util.Map;

/**
 * Makes the grid that is used to hold the cells
 *
 * @author Casey Szilagyi
 */
public class GridCreator {

  private Grid gameGrid;
  private Map<String,String> cellBehavior;
  private String CELL_LOCATION = "cell_society.backend.automata.";
  private String SIMULATION_TYPE;

  DeadCell deadCell = new DeadCell();

  /**
   * Makes the grid
   * @param row Number of rows
   * @param col Number of columns
   */
  public GridCreator(int row, int col, String simulationType){
    gameGrid = new Grid(row, col);
    SIMULATION_TYPE = simulationType + ".";
  }

  /**
   * Puts all of the cells in the grid
   */
  public void populateGrid(String grid, Map<String, String> cellCodes){
    grid = parseGrid(grid);
    int i = 0;
    for(int c = 0; c< gameGrid.getGridWidth(); c++){
      for(int r = 0; r< gameGrid.getGridHeight(); r++){
        String cellType = cellCodes.get(Character.toString(grid.charAt(i)));
        String curr = CELL_LOCATION + SIMULATION_TYPE + cellType;
        Class<Cell> currCell = null;
        try {
          currCell = (Class<Cell>) Class.forName(curr);
        }  catch (ClassNotFoundException e){
          System.out.println("Class name does not exist");
        }

        //Cell cell = new Cell();
        //cell = (Cell) currCell;

      }
    }
  }

  public String parseGrid(String grid){
    return grid.replace("\n", "").replace(" ", "");
  }


  public void setCellBehavior(Map<String, String> userCellBehavior){
    cellBehavior = userCellBehavior;
  }

  public Grid getGrid(){
    return gameGrid;
  }

}
