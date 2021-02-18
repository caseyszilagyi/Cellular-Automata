# Cell Society Refactoring Discussion
### Team Number: 6
### Names

Donghan Park (dp239), George Hong (grh17), Casey Szilagyi (crs79)

## Introduction

# Cell Society Refactoring Discussion
### Team Number: 6
### Names:

Donghan Park (dp239), George Hong (grh17), Casey Szilagyi (crs79)

## Highest Priority

- In DisplayManager class: makeAllButtons() method is +40 lines long

- We have many uses of instanceOf and a couple uses of  downcasting in our simulation steppers.  Additionally, because types are wrapped up into the subclasses of Cell, using instanceof is necessary for checking the types of other cells in a simulation.
    - Trying to think of ways to fix this but couldn't come up with much that is substantial
    - It feels like there

## Moderate Priority

- In AnimationManager class: setNextFPS() method has several conditional statements that could be rewritten more efficiently

- The GridCreator class is the thing that makes the grid object and also instantiates all of the cell objects. This violates the single responsibility principle

- The simulation class is what makes the simulation stepper. The stepper should really be created in the simulation initializer, because it doesn't really make much sense in the simulation class. It needs to be initialized just like the grid does.
