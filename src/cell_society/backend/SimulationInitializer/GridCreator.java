package cell_society.backend.SimulationInitializer;

import cell_society.backend.automata.Cell;
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


  /**
   * Makes the grid
   * @param row Number of rows
   * @param col Number of columns
   */
  public GridCreator(int row, int col){
    gameGrid = new Grid(row, col);
  }

  /**
   * Puts all of the cells in the grid
   */
  public void populateGrid(String grid, Map<String, String> cellCodes){
    grid = parseGrid(grid);
    int i = 0;
    for(int c = 0; c<4; c++){
      for(int r = 0; r<4; r++){
        String curr = Character.toString(grid.charAt(i++));
        try {
          Class currCell = Class.forName(cellCodes.get(curr));
        }  catch (ClassNotFoundException e){
          System.out.println("Class name does not exist");
        }



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
