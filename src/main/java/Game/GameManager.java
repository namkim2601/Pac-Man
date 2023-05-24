package wakawaka.game;

import java.util.ArrayList;
import java.util.List;
import wakawaka.*;
import wakawaka.map.*;

public class GameManager {
  public final Parser parser;
  
  public final List<List<Cell>> toDrawMap;
  
  public final Waka player;

  public final List<Ghost> ghosts;
  
  protected int livesToDraw;
  
  protected int livesDrawn = 0;
  
  protected int queuedMove = 0;
  
  protected int eatingFrames = 0;

  public final int ghostsToDraw;
  
  protected int ghostsDrawn = 0;

  /**
  * Constructor for GameManager class
  *
  * Retrieves map to draw, player and player lives from Parser
  */
  public GameManager() {
    this.parser = new Parser();
    this.parser.readConfig("config.json");
    this.toDrawMap = this.parser.parseCells();
    this.player = this.parser.parseWaka();
    this.livesToDraw = this.player.lives;
    this.ghosts = this.parser.parseGhosts();
    this.ghostsToDraw = this.ghosts.size();
  }
  /**
  * Checks if given cell is Air
  *
  * @param toDrawCell Cell to compare
  * @param posX x coordinate of cell
  * @param posY y coordinate of cell
  * @return true if cell is Air
  */
  public boolean drawAir(Cell toDrawCell, int posX, int posY) {
    if (toDrawCell instanceof Air) {
      this.player.nextAvailableExit(posX, posY); 
      for (Ghost ghost : this.ghosts) {
        ghost.nextAvailableExit(posX, posY);
      }
      return true;
    }
    return false;
  }
  
  /**
  * Checks if given cell is Fruit
  *
  * @param toDrawCell Cell to compare 
  * @param posX x coordinate of cell
  * @param posY y coordinate of cell
  * @return true if cell is Fruit
  */
  public boolean drawFruit(Cell toDrawCell, int posX, int posY) {
    if (toDrawCell instanceof Fruit) {
      this.player.nextAvailableExit(posX, posY);
      for (Ghost ghost : this.ghosts) {
        ghost.nextAvailableExit(posX, posY);
      }
      if (this.player.eat(posX, posY)) {
        Air air = new Air(posX, posY);
        int x = posX / 16;
        int y = posY / 16;
        List<Cell> toChangeRow = this.toDrawMap.get(y);
        toChangeRow.set(x, air);
        this.toDrawMap.set(y, toChangeRow);
        this.player.eating = true;
        if (this.player.ateAllFruit()) {
          System.out.println("You won!");
          System.exit(-1);
        } 
      } 
      return true;
    } 
    return false;
  }
  
  /**
  * Checks if given cell is Wall
  *
  * @param toDrawCell Cell to compare 
  * @param posX x coordinate of cell
  * @param posY y coordinate of cell
  * @return type of wall
  */
  public int drawWall(Cell toDrawCell, int posX, int posY) {
    if (toDrawCell instanceof Wall) {
      int wallType = toDrawCell.getType();
      this.player.collision(posX, posY);
      for (Ghost ghost : this.ghosts) {
        ghost.collision(posX, posY);
      }
      if (wallType == 1 || wallType == 2 || wallType == 3 || wallType == 4 || wallType == 5 || wallType == 6)
        return wallType; 
    } 
    return -1;
  }

  /**
  * Check if given cell is Waka
  *
  * @param toDrawCell Cell to compare 
  * @param posX x coordinate of cell
  * @param posY y coordinate of cell
  * @return parameters - player type, player x coordinate and player y coordinate
  * @return null if unsuccessful
  */  
  public int[] drawWaka(Cell toDrawCell, int posX, int posY) {
    if (toDrawCell instanceof Waka) {
      int[] params = new int[3];
      if (this.player.wakaStart) {
        this.player.setX(posX - 4);
        this.player.setY(posY - 4);
        this.player.wakaStart = false;
        params[0] = 0;
        params[1] = this.player.getX();
        params[2] = this.player.getY();
        return params;
      } 
      int playerType = this.player.getDirection();
      int newX = this.player.getX();
      int newY = this.player.getY();
      params[0] = playerType;
      params[1] = newX;
      params[2] = newY;
      return params;
    } 
    return null;
  }

  /**
  * If Cell is of instance Waka and is located in bottom left of map,
  * draw Waka's remaining lives
  *
  * @param toDrawCell Cell to compare 
  * @param posY y coordinate of cell
  * @return number of lives drawn
  * @return -1 if unsuccessful
  */  
  public int drawLives(Cell toDrawCell, int posY) {
    if (toDrawCell instanceof Waka) {
      if (this.livesDrawn < this.livesToDraw && posY == 544) {
        this.livesDrawn++;
        return this.livesDrawn;
      } 
      this.livesDrawn = 0;
      return -1;
    } 
    return -1;
  }
  
  /**
  * If cell is of instance Ghost, draw nothing
  *
  * @param toDrawCell Cell to compare 
  * @param posX x coordinate of cell
  * @param posY y coordinate of cell
  * @return true if Ghost is drawn
  */
  public int[] drawGhost(Cell toDrawCell, int posX, int posY) {
    if (toDrawCell instanceof Ghost) {
      int[] params = new int[2];
      if (this.ghostsDrawn < this.ghostsToDraw) {
        int newX = this.ghosts.get(ghostsDrawn).getX();
        int newY = this.ghosts.get(ghostsDrawn).getY();
        this.ghostsDrawn++;
        params[0] = newX;
        params[1] = newY;
        return params;
      } else {
        this.ghostsDrawn = 0;
      }
    }
    return null;
  }
  
  /**
  * Move player
  */
  public void movePlayer() {
    this.player.move();
  }

  /**
  * Move ghost
  */
  public void moveGhosts() {
    for (Ghost ghost : this.ghosts) {
        ghost.move();
        ghost.changeDirection(this.player);
    }
  }
  
  /**
  * Move player in given direction
  *
  * @param direction direction to move as int {1, 2, 3, 4}
  * @return if move was successful
  */
  public boolean moveWithKey(int direction) {
    int playerType = this.player.getDirection();
    if (playerType == direction)
      return false;
    if ((direction == 1 || direction == 3) && (
      playerType == 0 || playerType == direction + 1 || this.player.noQueue)) {
      this.player.setDirection(direction);
      this.player.queuedMove = 0;
      this.player.noQueue = false;
      return true;
    } 
    if ((direction == 2 || direction == 4) && (
      playerType == 0 || playerType == direction - 1 || this.player.noQueue)) {
      this.player.setDirection(direction);
      this.player.queuedMove = 0;
      this.player.noQueue = false;
      return true;
    } 
    this.player.queuedMove = direction;
    return true;
  }
  
  /**
  * Move player in direction of key pressed
  *
  * @param key key code of key pressed
  * @return if move was succesful
  */
  public boolean changeDirection(int key) {
    if (key == 38) 
      return moveWithKey(1); 
    if (key == 40) 
      return moveWithKey(2); 
    if (key == 37) 
      return moveWithKey(3); 
    if (key == 39) 
      return moveWithKey(4);
    return false;
  }
  
  /**
  * Waka closes mouth for 8 frames while eating
  *
  * @return current frame(0 to 7) if Waka is eating
  * @return -1 if Waka is not eating
  */
  public int eatingAnimation() {
    if (this.player.eating) {
      if (this.eatingFrames < 8) {
        this.eatingFrames++;
        return this.eatingFrames;
      } 
      this.eatingFrames = 0;
      this.player.eating = false;
    } 
    return -1;
  }
}