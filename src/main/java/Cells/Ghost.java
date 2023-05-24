package wakawaka.game;

import wakawaka.Cell;
import java.util.List;
import static java.lang.Math.abs;
import java.util.Random;

public class Ghost extends GameCharacter {
  private List<Integer> scatter;

  private List<Integer> chase;

  /**
  * Constructor for Ghost Class
  *
  * @param x x coordinate
  * @param y y coordinate
  * @param scatter List of lengths for mode scatter
  * @param chase List of lengths for mode chase
  */
  public Ghost(int x, int y, List<Integer> scatter, List<Integer> chase) {
    super(x, y);
    this.scatter = scatter;
    this.chase = chase;
  }

  /**
  * @return next scatter mode length
  */
  public int getNextScatter() {
    return this.scatter.get(0);
  }

  /**
  * @return next chase mode length
  */
  public int getNextChase() {
    return this.chase.get(0);
  }

  /**
  * Removes most recently used scatter mode length
  */
  public void rmLastScatter() {
    this.scatter.remove(0);
  }
  /**
  * Removes most recently used chase mode length
  */
  public void rmLastChase() {
    this.chase.remove(0);
  }

  /**
  * Move ghost in given direction
  *
  * @param direction direction to move as int {1, 2, 3, 4}
  * @return if move was successful
  */
  public boolean moveToPlayer(int direction) {
    if (this.direction == direction)
      return false;
    if ((direction == 1 || direction == 3) && (
      this.direction == 0 || this.direction == direction + 1 || this.noQueue)) {
      this.direction = direction;
      this.queuedMove = 0;
      this.noQueue = false;
      return true;
    } 
    if ((direction == 2 || direction == 4) && (
      this.direction == 0 || this.direction == direction - 1 || this.noQueue)) {
      this.direction = direction;
      this.queuedMove = 0;
      this.noQueue = false;
      return true;
    } 
    this.queuedMove = direction;
    return true;
  }
  
  /**
  * Move ghost in direction of player
  *
  * @param player target location
  * @return if move was successful
  */
  public boolean changeDirection(Waka player) {
    int diffX = abs(this.getX() - player.getX());
    int diffY = abs(this.getY() - player.getY());
    if (diffY > diffX) {
      if (this.getY() > player.getY())
        return moveToPlayer(1);
      if (this.getY() < player.getY())
        return moveToPlayer(2);
    }
    else {
      if (this.getX() > player.getX()) 
        return moveToPlayer(3);
      if (this.getX() < player.getX())
        return moveToPlayer(4);
    }
    return false;
  }
}
