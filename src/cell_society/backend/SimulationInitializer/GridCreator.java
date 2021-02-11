package cell_society.backend.SimulationInitializer;

import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Grid;

public class GridCreator {

  private Grid gameGrid;
  private CellInitializer cellInitializer;

  public GridCreator(int row, int col, String grid, String gameType){
    gameGrid = new Grid(row, col);
    makeCellInitializer(gameType);
    populateGrid();
  }

  public void populateGrid(){

  }

  public void makeCellInitializer(String gameType){
    cellInitializer = new CellInitializer();
  }

  public Grid getGrid(){
    return gameGrid;
  }

}
