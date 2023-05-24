package wakawaka.game;

import wakawaka.*;
import wakawaka.map.*;

public class Waka extends GameCharacter {
  protected int lives;
  
  public final int speed;
  
  public final int fruitToWin;
  
  protected int fruitEaten = 0;

  protected boolean wakaStart = true;
  
  protected boolean eating = false;

  /**
  *Constuctor for Waka Class
  *
  * @param x x coordinate
  * @param y y coordinate
  * @param direction direction Waka is facing (1 for up, 2 for below, 3 for left, 4 for right)
  * @param lives Waka remaining lives
  * @param speed Waka speed
  * @param fruitToWin Fruit required to win game
  */
  public Waka(int x, int y, int direction, int lives, int speed, int fruitToWin) {
    super(x, y);
    this.direction = direction;
    this.lives = lives;
    this.speed = speed;
    this.fruitToWin = fruitToWin;
  }
  /**
  * If Waka collides with a Fruit cell, Waka eats the fruit
  *
  * @param fruitX x coordinate of Fruit
  * @param fruitY y coordinate of Fruit
  * @return true if Waka eats fruit
  */
  public boolean eat(int fruitX, int fruitY) {
    if (this.getX() == fruitX - 4 && this.getY() == fruitY - 4) {
      this.fruitEaten++;
      return true;
    } 
    return false;
  }

  /**
  * Checks if waka has eaten all fruit
  * 
  * @return true if all fruit is eaten
  */
  public boolean ateAllFruit() { 
    if (this.fruitEaten == this.fruitToWin)
      return true; 
    return false;
  }
}