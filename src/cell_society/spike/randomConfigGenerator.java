package cell_society.spike;

import java.util.Random;

public class randomConfigGenerator {

  static int WIDTH = 50;
  static int HEIGHT = 50;
  static double PROB = 0.30;
  static double HALFP = 1;

  public static void main(String[]args){
    Random random = new Random();
    for(int i = 0; i<HEIGHT; i++){
      for(int j = 0; j<WIDTH; j++){
        double rand = random.nextDouble();

        if(rand < PROB){
          System.out.print("o");
        }
        else if(rand < HALFP){
          System.out.print("x");
        }
        else{
          System.out.print("o");
        }
      }
      System.out.println();
    }
  }


}


