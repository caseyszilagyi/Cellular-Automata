# Simulation Lab Discussion

## Breakout with Inheritance

## Names and NetIDs

Donghan Park (dp239),
George Hong (grh17),
Casey Szilagyi (crs79)

### Power-up

This superclass's purpose as an abstraction: The purpose of the abstraction is to decide what factors all powerups will have in common. This includes things such as the size, and the speed (or aspects of the speed that are the same among all power ups)
```java
 public class PowerUp {
     public void applyPowerup(...gameStateVariables);
 }
```

Description about current powerup implementation:
Currently, powerup type is defined as a private variable of the PowerUp class.

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
Would be able to influence the main game states as appropriate for the power-up, whatever we want to include.

Each subclass can then have an applyPowerup method, which can alter the game state based on the parameters that are passed. This way, we embrace the "tell the other guy" policy, and have all of the power up implementation in the powerup class rather than in the overall game class.

The only issue with this is if there is one power up that only modifies the ball, and another one only modifies a paddle. We want the applyPowerup method to be the same so we don't have any conditionals at the highest level, so we might be passing in too much information. Maybe if there was some type of intermediate class (powerUpManager) that dealt with
this logic to avoid passing the power up too much information.

Instead of having:
```java
if (powerUp instanceof TYPEA){
    powerUp.applyActionA();
}
else if (powerUp instanceof TYPEB){
    powerUp.applyActionB();
}
```
Do instead:
```java
powerUp.applyPowerup();
```
```java
 public class X extends PowerUp {
     public void applyPowerup();
     // unique powerup implementation 
 }
```

#### Affect on Game/Level class (the Closed part)

Avoids unnecessary conditionals and messy code and moves the implementation of each powerup into the class of
that power up type.

