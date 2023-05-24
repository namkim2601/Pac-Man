package wakawaka.game;

import wakawaka.*;
import wakawaka.map.*;

public class GameCharacter extends Cell {
  protected int direction;
  protected int queuedMove = 0;
  public Boolean noQueue = false;
    
  public GameCharacter(int x, int y) {
    super(x, y);
  } 
  /**
  * Getter method for direction
  *
  * @return current character direction
  */
  public int getDirection() {
    return this.direction;
  }
  
  /**
  * Setter method for direction
  *
  * @param direction new direction
  */
  public void setDirection(int direction) {
    this.direction = direction;
  }
  
  /**
  * Moves character by 1 pixel unless character is stuck on a wall
  */
  public void move() { 
    if (this.noQueue)
      return; 
    if (this.direction == 1)
      this.setY(this.getY() - 1);
    if (this.direction == 2)
      this.setY(this.getY() + 1); 
    if (this.direction == 3)
      this.setX(this.getX() - 1); 
    if (this.direction == 4)
      this.setX(this.getX() + 1); 
  }

  /**
  * character takes the next available exit
  *
  * @param exitX x coordinate of potential exit
  * @param exitY y coordinate of potential exit
  */
  public void nextAvailableExit(int exitX, int exitY) {
    if (cellAbove(exitX, exitY) && 
      this.queuedMove == 1)
      this.direction = 1; 
    if (cellBelow(exitX, exitY) && 
      this.queuedMove == 2)
      this.direction = 2; 
    if (cellLeft(exitX, exitY) && 
      this.queuedMove == 3)
      this.direction = 3; 
    if (cellRight(exitX, exitY) && 
      this.queuedMove == 4)
      this.direction = 4; 
  }

  /**
  * Checks if a specific cell is above character
  *
  * @param cellX x coordinate of cell
  * @param cellY y coordinate of cell
  * @return true if cell is above character
  */
  public boolean cellAbove(int cellX, int cellY) {
    if (this.getX() == cellX - 4 && this.getY() == cellY + 12)
      return true; 
    return false;
  }

  /**
  * Checks if a specific cell is below character
  *
  * @param cellX = x coordinate of cell
  * @param cellY = y coordinate of cell
  * @return true if cell is below character
  */  
  public boolean cellBelow(int cellX, int cellY) { 
    if (this.getX() == cellX - 4 && this.getY() == cellY - 20)
      return true; 
    return false;
  }
  
  /**
  * Checks if a specific cell is on character's left
  *
  * @param cellX x coordinate of cell
  * @param cellY y coordinate of cell
  * @return true if cell is on character's left
  */
  public boolean cellLeft(int cellX, int cellY) { 
    if (this.getX() == cellX + 12 && this.getY() == cellY - 4)
      return true; 
    return false;
  }
  
  /**
  * Checks if a specific cell is on the character's right
  *
  * @param cellX x coordinate of cell
  * @param cellY y coordinate of cell
  * @return true if cell is on character's right
  */
  public boolean cellRight(int cellX, int cellY) { 
    if (this.getX() == cellX - 20 && this.getY() == cellY - 4)
      return true; 
    return false;
  }
  
  /**
  * If character is running into a wall,
  * character either makes a turn or stops moving
  *
  * @param wallX x coordinate of Wall
  * @param wallY y coordinate of Wall
  */
  public void collision(int wallX, int wallY) {
    if (this.direction == 1 && 
      cellAbove(wallX, wallY))
      turnLeftOrRight();
    if (this.direction == 2 && 
      cellBelow(wallX, wallY))
      turnLeftOrRight(); 
    if (this.direction == 3 && 
      cellLeft(wallX, wallY))
      turnUpOrDown(); 
    if (this.direction == 4 && 
      cellRight(wallX, wallY))
      turnUpOrDown(); 
  }

  /**
  * character either turns up, turns down, or stops moving
  */
  public void turnUpOrDown() {
    if (this.queuedMove == 1 || this.queuedMove == 2) {
      this.direction = this.queuedMove;
      if (this.queuedMove == 1)
        this.setY(this.getY() + 1);
      if (this.queuedMove == 2)
        this.setY(this.getY() - 1);
    } else {
      this.noQueue = true;
    }
  }


  /**
  * character either turns left, turns right, or stops moving
  */
  public void turnLeftOrRight() {
    if (this.queuedMove == 3 || this.queuedMove == 4) {
      this.direction = this.queuedMove;
      if (this.queuedMove == 3)
        this.setX(this.getX() + 1);
      if (this.queuedMove == 4)
        this.setX(this.getX() - 1);
    } else {
      this.noQueue = true;
    } 
  }
}