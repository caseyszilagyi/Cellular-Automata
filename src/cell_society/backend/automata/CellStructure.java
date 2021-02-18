package cell_society.backend.automata;

import java.util.List;

public abstract class CellStructure {

  public abstract List<Coordinate> getAllNeighboringCoordinates(int row, int col);

  public abstract List<Coordinate> getAllAdjacentCoordinates(int row, int col);

}