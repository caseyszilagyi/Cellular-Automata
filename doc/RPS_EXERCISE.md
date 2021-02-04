# Simulation Lab Discussion

## Rock Paper Scissors

## Names and NetIDs

Donghan Park (dp239),
George Hong (grh17),
Casey Szilagyi (crs79)

### High Level Design Ideas

NOTES:
* must support any number of choices
* game class keeps list of player scores, with the index representing the player number
* main game class that reads file
    * maintains an internal data structure to see *what beats what*
    * method that takes an arbitrary size list of player actions during a round and returns the winner index or position

### CRC Card Classes


The purpose of this class is to serve as the Game and run the main loop.
```java
public class Engine {
    public Game makeGame;
    public int getRoundResult(String[] playerActions);
    private void updateScore(int winner);
}
```

The purpose of this class is to set up the internal logic of the game and to aggregate components.
```java
public class Game {
    public Game(int numPlayers, String textFileName);
    public int playRound();
    public makeGameLogic(String textFileName);
}
```

The purpose of this class is to read in the text file and make the datastructure that deals with the game logic
```java
public class GameLogic {
    public GameLogic(String textFileName);
    public String getWinner(String... weapons);
    public void addWeaponOption(boolean into, boolean outof);
}
```

Purpose of this class is to keep track of what weapon each player uses in a round
```java  
public class Player {
    public String getWeapon()
}
```

Purpose of this class is to keep track of how many points each player has
```java  
public class Scoreboard {
    public List<Player> players
    public void updateScore(Player player)
}
```

### Use Cases
```java 
Engine engine = new Engine("data.txt");
Player p1 = ;
Player p2 = ;
p1.getMove();
p2.getMove();
```

* A new game is started with five players, their scores are reset to 0.
 ```java
Game game = new Game(5, "data.txt");
 ```

* A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
Player p1 = new Player();
p1.getWeapon();
 ```

* Given three players' choices, one player wins the round, and their scores are updated.
 ```java
Game game = new Game(3, "data.txt");
...
game.getRoundResult(p1.getWeapon(), p2.getWeapon(), p3.getWeapon())
 ```

* A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
//
 ```

* A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
//
 ```
