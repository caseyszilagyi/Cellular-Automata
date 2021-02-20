package cell_society.backend.automata;

public class Coordinate {

  // immutable instance variables
  // NOTE: these can be any two types, same or different
  private final int first;
  private final int second;

  /**
   * Create a pair directly from the given values
   * @param first
   * @param second
   */
  public Coordinate(int first, int second) {
    this.first = first;
    this.second = second;
  }

  // NOTE: provides getters, but not setters

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

  @Override
  public int hashCode() {
    // https://stackoverflow.com/questions/14677993/how-to-create-a-hashmap-with-two-keys-key-pair-value
    int result = first;
    result = 31 * result + second;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Coordinate)) return false;
    Coordinate coordinate = (Coordinate) obj;
    return first == coordinate.first && second == coordinate.second;
  }
}
