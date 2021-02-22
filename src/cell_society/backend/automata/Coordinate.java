package cell_society.backend.automata;

/**
 * Object representing a pair of Cartesian Coordinates
 *
 * @author George Hong
 */
public class Coordinate {

  private final int first;
  private final int second;

  /**
   * Create a pair directly from the given values
   *
   * @param first  first coordinate
   * @param second second coordinate
   */
  public Coordinate(int first, int second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Copy a coordiante object
   *
   * @param coordinate coordinate object to copy
   */
  public Coordinate(Coordinate coordinate) {
    this.first = coordinate.first;
    this.second = coordinate.second;
  }


  /**
   * Returns first value in pair
   */
  public int getFirst() {
    return first;
  }

  /**
   * Returns second value in pair
   */
  public int getSecond() {
    return second;
  }

  /**
   * @see Object#toString()
   */
  @Override
  public String toString() {
    return "(" + first + ", " + second + ")";
  }

  /**
   * @return int hashcode
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    // https://stackoverflow.com/questions/14677993/how-to-create-a-hashmap-with-two-keys-key-pair-value
    int result = first;
    result = 31 * result + second;
    return result;
  }

  /**
   * @param obj other coordinate object to compare to
   * @return boolean representing whether the two objects are considered equal
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {
    // https://stackoverflow.com/questions/14677993/how-to-create-a-hashmap-with-two-keys-key-pair-value
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Coordinate)) {
      return false;
    }
    Coordinate coordinate = (Coordinate) obj;
    return first == coordinate.first && second == coordinate.second;
  }
}
