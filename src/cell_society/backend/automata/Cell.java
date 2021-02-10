package cell_society.backend.automata;

public class Cell {

  private final int row;
  private final int col;

  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  protected int getRow() {
    return row;
  }

  protected int getCol() {
    return col;
  }

  public Neighbors getNeighbors() {
    return null;
  }

  public void makeDecisions(Neighbors neighbors, Grid grid) {

  }
}
