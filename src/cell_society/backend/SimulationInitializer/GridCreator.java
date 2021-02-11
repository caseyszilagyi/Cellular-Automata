package cell_society.backend.SimulationInitializer;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;

/**
 * Makes the grid that is used to hold the cells
 *
 * @author Casey Szilagyi
 */
public class GridCreator {

  private Grid gameGrid;
  private CellInitializer cellInitializer;

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
  public void populateGrid(String grid){

  }

  /** May not need this. Instead what might be done is a type of key will be added in
   * the xml file that specifies what numbers in the grid correspond to a specific cell type.
   * Then, Class.forName can be used to initialize a cell.
   * @param gameType
   */
  public void makeCellInitializer(String gameType){
    cellInitializer = new CellInitializer();
  }

  public Grid getGrid(){
    return gameGrid;
  }

}
