package cell_society.backend.SimulationInitializer;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;

public class GridCreator {

  private Grid gameGrid;

  public GridCreator(int row, int col, String grid){
    gameGrid = new Grid(row, col);

  }

  public Grid getGrid(){
    return gameGrid;
  }

}
