package wakawaka.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import wakawaka.*;
import wakawaka.map.*;

public class GameManagerTest {
  GameManager testGmMan = new GameManager();
  
  @Test // drawAir - draws nothing
  public void drawAirTest() {
    Cell notAir = new Fruit(0, 0);
    assertFalse(testGmMan.drawAir(notAir, 0, 0)); // Not Air cell

    Cell isAir = new Air(0, 0);
    assertTrue(testGmMan.drawAir(isAir, 0, 0)); // Is Air Cell
  }

  @Test // drawFruit - draws Fruit if not eaten, draws Air if eaten
  public void drawFruitTest() {
    Cell notFruit = new Air(0, 0);
    assertFalse(testGmMan.drawFruit(notFruit, 0, 0)); // Not Fruit cell

    Cell isFruit = new Fruit(0, 0);
    assertTrue(testGmMan.drawFruit(isFruit, 0, 0)); // Is Fruit Cell

    testGmMan.player.setX(12);
    testGmMan.player.setY(12);
    testGmMan.drawFruit(isFruit, 16, 16); // Fruit is eaten by player
    List<Cell> compareRow = testGmMan.toDrawMap.get(1);
    Cell compareCell = compareRow.get(1);
    assertTrue(compareCell instanceof Air); // Fruit is replaced by Air cell
    assertTrue(testGmMan.player.eating);
  }

  @Test // drawWall - draws wall based on given type
  public void drawWallTest() {
    Cell notWall = new Air(0, 0);
    assertEquals(-1, testGmMan.drawWall(notWall, 0, 0)); // Not Wall cell

    Cell wallHori = new Wall(0, 0, 1);
    Cell wallVert = new Wall(0, 0, 2);
    Cell wallUpL = new Wall(0, 0, 3);
    Cell wallUpR = new Wall(0, 0, 4);
    Cell wallDownL = new Wall(0, 0, 5);
    Cell wallDownR = new Wall(0, 0, 6);
    Cell noSuchWall = new Wall(0, 0, 100);

    assertEquals(1, testGmMan.drawWall(wallHori, 0, 0));
    assertEquals(2, testGmMan.drawWall(wallVert, 0, 0));
    assertEquals(3, testGmMan.drawWall(wallUpL, 0, 0));
    assertEquals(4, testGmMan.drawWall(wallUpR, 0, 0)); 
    assertEquals(5, testGmMan.drawWall(wallDownL, 0, 0));
    assertEquals(6, testGmMan.drawWall(wallDownR, 0, 0));
    assertEquals(-1, testGmMan.drawWall(noSuchWall, 0, 0));
  }

  @Test // drawWaka - draws player position
  public void drawWakaTest() {
    Cell notWaka = new Air(0, 0);
    assertNull(testGmMan.drawWaka(notWaka, 0, 0)); // Not Waka cell

    Cell isWaka = new Waka(0, 0, 4, 3, 1, 100); // Is Waka cell
    int[] testFirstParams = testGmMan.drawWaka(isWaka, 64, 64); // Player starting position
    assertEquals(60, testGmMan.player.getX());
    assertEquals(60, testGmMan.player.getY());
    assertFalse(testGmMan.player.wakaStart);
    assertEquals(0, testFirstParams[0]);
    assertEquals(60, testFirstParams[1]);
    assertEquals(60, testFirstParams[2]);

    testGmMan.player.setX(61);
    testGmMan.player.setY(140);
    testGmMan.player.setDirection(4);
    int[] testParams = testGmMan.drawWaka(isWaka, 60, 60); // Player current position
    assertEquals(4, testParams[0]);
    assertEquals(61, testParams[1]);
    assertEquals(140, testParams[2]);
  }

  @Test // drawWaka - draws player's remaining lives at bottom left of map
  public void drawLivesTest() {
    Cell notWaka = new Air(0, 0);
    assertEquals(-1, testGmMan.drawLives(notWaka, 0)); // Not Waka cell

    Cell isWaka = testGmMan.player;

    assertEquals(-1, testGmMan.drawLives(isWaka, 0)); // Not bottom left of map
    testGmMan.livesDrawn = 100;
    assertEquals(-1, testGmMan.drawLives(isWaka, 544)); // Number of remaining lives already drawm
                                                        
    assertEquals(1, testGmMan.drawLives(isWaka, 544)); // Successful draw
  }

  @Test // drawGhost
  public void drawGhostTest() {
    Cell notGhost = new Air(0, 0);
    assertNull(testGmMan.drawGhost(notGhost, 0, 0)); // Not Ghost cell

    Cell isGhost = new Ghost(0, 0, null, null);
    assertNotNull(testGmMan.drawGhost(isGhost, 0, 0)); // Ghost cell
  }

  @Test // changeDirection - invalid moves
  public void failedMoveTest() {
    assertFalse(testGmMan.changeDirection(0)); // Move doesn't exist

    testGmMan.player.setDirection(1);
    assertFalse(testGmMan.changeDirection(38)); // Player already moving in given direction
  }

  @Test // movePlayer & changeDirection - Player moves when prompted
  public void successfulMoveTest() {
    testGmMan.movePlayer();

    // Function behaves the same for when (player.type = 1) & when (player.type = 3)
    testGmMan.player.setDirection(1);
    assertTrue(testGmMan.changeDirection(40)); // Move in opposite direction
    assertEquals(2, testGmMan.player.getDirection());
    assertEquals(0, testGmMan.player.queuedMove);
    assertFalse(testGmMan.player.noQueue);

    testGmMan.player.setDirection(1);
    testGmMan.player.noQueue = true;
    assertTrue(testGmMan.changeDirection(40)); // Move in opposite direction at wall

    testGmMan.player.setDirection(1);
    testGmMan.player.noQueue = true;
    assertTrue(testGmMan.changeDirection(37)); // Move in perpendicular direction at wall

    // Function behaves the same for when (player.type = 2) & when (player.type = 4)
    testGmMan.player.setDirection(2);
    assertTrue(testGmMan.changeDirection(38)); // Move in opposite direction
    assertEquals(1, testGmMan.player.getDirection());
    assertEquals(0, testGmMan.player.queuedMove);
    assertFalse(testGmMan.player.noQueue);

    testGmMan.player.setDirection(2);
    testGmMan.player.noQueue = true;
    assertTrue(testGmMan.changeDirection(38)); // Move in opposite direction at wall

    testGmMan.player.setDirection(2);
    testGmMan.player.noQueue = true;
    assertTrue(testGmMan.changeDirection(39)); // Move in perpendicular direction at wall
  }

  @Test // changeDirection - Player uses queued move at next available exit
  public void queuedMoveTest() {
    testGmMan.player.setDirection(3);
    assertTrue(testGmMan.moveWithKey(1));
    assertEquals(1, testGmMan.player.queuedMove); // Takes next exit above

    testGmMan.player.setDirection(4);
    assertTrue(testGmMan.moveWithKey(2));
    assertEquals(2, testGmMan.player.queuedMove); // Takes next exit below

    testGmMan.player.setDirection(1);
    assertTrue(testGmMan.moveWithKey(3));
    assertEquals(3, testGmMan.player.queuedMove); // Takes next exit on the left

    testGmMan.player.setDirection(2);
    assertTrue(testGmMan.moveWithKey(4));
    assertEquals(4, testGmMan.player.queuedMove);  // Takes next exit on the right
  }
 
  @Test // eatingAnimation
  public void eatingAnimationTest() {
    testGmMan.player.eating = false;
    assertEquals(-1, testGmMan.eatingAnimation()); // Player is not eating

    testGmMan.player.eating = true;
    assertEquals(1, testGmMan.eatingAnimation()); // Player is eating

    testGmMan.eatingFrames = 8;
    assertEquals(-1, testGmMan.eatingAnimation()); // Player has finished eating
    assertEquals(0, testGmMan.eatingFrames);
    assertFalse(testGmMan.player.eating);
  }
}