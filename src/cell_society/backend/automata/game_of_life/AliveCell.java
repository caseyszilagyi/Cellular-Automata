package cell_society.backend.automata.game_of_life;


import cell_society.backend.automata.Cell;
import cell_society.backend.automata.Neighbors;
import cell_society.backend.automata.grid_styles.Grid;

/**
 * The AliveCell represents the live cell in Conway's Game of Life.
 * <p>
 *
 * @author George Hong
 */
public class AliveCell extends Cell {

  /**
   * Constructs an instance of an AliveCell
   *
   * @param row row index where AliveCell will be located
   * @param col column index where DeadCell will be located
   */
  public AliveCell(int row, int col) {
    super(row, col);
  }

  /**
   * Parameter-less constructor for the AliveCell intended for use with the XML Reader.
   */
  public AliveCell() {

  }

  /**
   * The AliveCell considers all cells within one block of it to be a neighbor.
   *
   * @param grid grid where AliveCell is residing
   * @return Neighbors object containing all of the cells adjacent to this Cell.
   */
  @Override
  public Neighbors getNeighbors(Grid grid) {
    int row = getRow();
    int col = getCol();
    return grid.getDirectNeighbors(row, col);
  }

  /**
   * Updates the next Grid state according to the rules obeyed by AliveCells: 1. Any live cell with
   * fewer than two live neighbours dies, as if by underpopulation. 2. Any live cell with two or
   * three live neighbours lives on to the next generation. 3. Any live cell with more than three
   * live neighbours dies, as if by overpopulation.
   *
   * @param neighbors   Cells that this cell uses to make its decision
   * @param currentGrid Current state of the grid.
   * @param nextGrid    grid to hold the next configuration of cells.
   */
  @Override
  public void performPrimaryAction(Neighbors neighbors, Grid currentGrid, Grid nextGrid) {
    int numLiveNeighbors = neighbors.getTypeCount(this);
    int row = getRow();
    int col = getCol();
    if (numLiveNeighbors < 2 || numLiveNeighbors > 3) {
      DeadCell deadCell = new DeadCell(row, col);
      nextGrid.placeCell(row, col, deadCell);
    } else {
      AliveCell aliveCell = new AliveCell(row, col);
      nextGrid.placeCell(row, col, aliveCell);
    }
  }

  /**
   * Returns the Grid representation of this Cell for use with the CellDecoder
   * @return String "AliveCell"
   */
  @Override
  public String toString() {
    return "AliveCell";
  }

  /**
   * @Deprecated
   * Returns the length-1 String representation of this Cell for debugging purposes through the console
   * @return String "0" representing an alive cell, for easy viewability.
   */
  @Override
  public String getGridRepresentation() {
    return "O";
  }
}
