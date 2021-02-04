# Simulation Lab Discussion

## Cell Society

## Names and NetIDs

Donghan Park (dp239),
George Hong (grh17),
Casey Szilagyi (crs79)

### High Level Design Ideas

- Need some kind of board to hold all of the objects. More than 1 because everything is updated simultaneously
- Objects have different properties based on the specific simulation type.
- No matter which simulation, each object needs to know:
    - Current state
    - Neighbors
    - Neighbors states
- One part of the project updates the states, while the other displays. Then there is something in the middle communicating this

### CRC Card Classes

```java
public abstract class Simulation{
    
}
```

```java
public class SpecificSimulation extends Simulation{
    
}
```


### Use Cases

* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML file
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Switch simulations: use the GUI to change the current simulation from Game of Life to Wa-Tor
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```