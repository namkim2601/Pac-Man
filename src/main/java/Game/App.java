package wakawaka;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;
import wakawaka.game.GameManager;

public class App extends PApplet {
  protected GameManager gameManager;
  
  protected PImage fruit;
  
  protected PImage ghost;
  
  protected List<PImage> wallImages = new ArrayList<>();
  
  protected List<PImage> playerImages = new ArrayList<>();
  
  public static final int WIDTH = 448;
  
  public static final int HEIGHT = 576;
  
  /**
  * Constructor for App class
  *
  * Assigns a new gameManager
  */
  public App() {
    this.gameManager = new GameManager();
  }
  
  /**
  * Loads Images
  */
  public void setup() {
    frameRate(60);
    this.wallImages.add(loadImage("src/resources/horizontal.png"));
    this.wallImages.add(loadImage("src/resources/vertical.png"));
    this.wallImages.add(loadImage("src/resources/upLeft.png"));
    this.wallImages.add(loadImage("src/resources/upRight.png"));
    this.wallImages.add(loadImage("src/resources/downLeft.png"));
    this.wallImages.add(loadImage("src/resources/downRight.png"));
    this.playerImages.add(loadImage("src/resources/playerClosed.png"));
    this.playerImages.add(loadImage("src/resources/playerUp.png"));
    this.playerImages.add(loadImage("src/resources/playerDown.png"));
    this.playerImages.add(loadImage("src/resources/playerLeft.png"));
    this.playerImages.add(loadImage("src/resources/playerRight.png"));
    this.fruit = loadImage("src/resources/fruit.png");
    this.ghost = loadImage("src/resources/ghost.png");
  }
  
  /**
  * Specifies map dimensions
  */
  public void settings() {
    size(448, 576);
  }
  
  /**
  * Draws images to map
  */
  public void draw() {
    background(0, 0, 0);
    for (List<Cell> toDrawRow : this.gameManager.toDrawMap) {
      for (Cell toDrawCell : toDrawRow) {
        int posX = toDrawCell.getX();
        int posY = toDrawCell.getY();
        this.gameManager.drawAir(toDrawCell, posX, posY);
        if (this.gameManager.drawFruit(toDrawCell, posX, posY))
          image(this.fruit, posX, posY);

        int wallType = this.gameManager.drawWall(toDrawCell, posX, posY);
        if (wallType != -1)
          image(this.wallImages.get(wallType - 1), posX, posY);

        int[] playerParams = this.gameManager.drawWaka(toDrawCell, posX, posY);
        if (playerParams != null && 
          playerParams[0] != -1) {
          if (this.gameManager.eatingAnimation() != -1) {
            image(this.playerImages.get(0), playerParams[1], playerParams[2]);

          } else {
            image(this.playerImages.get(playerParams[0]), playerParams[1], playerParams[2]);
          }
        }

        int livesToDraw = this.gameManager.drawLives(toDrawCell, posY);
        if (livesToDraw != -1)
          image(this.playerImages.get(4), (posX + (8 * livesToDraw)), posY);

        int[] ghostParams = this.gameManager.drawGhost(toDrawCell, posX, posY);
        if (ghostParams != null)
          image(this.ghost, ghostParams[0], ghostParams[1]); 
      } 
    }
    this.gameManager.moveGhosts();
    this.gameManager.movePlayer();
  }
  
  /**
  * Recieves key pressed as an input and sends it to gameManager
  */
  public void keyPressed() {
    if (this.key == CODED)
      this.gameManager.changeDirection(this.keyCode); 
  }
  
  /**
  * Game Start!
  *
  * @param args command line arguments
  */
  public static void main(String[] args) {
    PApplet.main("wakawaka.App");
  }
}