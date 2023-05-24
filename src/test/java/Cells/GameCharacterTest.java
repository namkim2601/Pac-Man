package wakawaka.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import wakawaka.map.*;


public class GameCharacterTest {
  GameCharacter testGmCh = new GameCharacter(60, 60);

  @Test // cellAbove
  public void cellAboveTest() {
    assertFalse(testGmCh.cellAbove(0, 0));
    assertFalse(testGmCh.cellAbove(64, 0));
    assertTrue(testGmCh.cellAbove(64, 48));
  }
  @Test // cellBelow
  public void cellBelowTest() {
    assertFalse(testGmCh.cellBelow(0, 0));
    assertFalse(testGmCh.cellBelow(64, 0));
    assertTrue(testGmCh.cellBelow(64, 80));
  }
  @Test // cellLeft
  public void cellLeftTest() {
    assertFalse(testGmCh.cellLeft(0, 0));
    assertFalse(testGmCh.cellLeft(48, 0));
    assertTrue(testGmCh.cellLeft(48, 64));
  }
  @Test // cellRight
  public void cellRightTest() {
    assertFalse(testGmCh.cellRight(0, 0));
    assertFalse(testGmCh.cellRight(80, 0));
    assertTrue(testGmCh.cellRight(80, 64));
  }

  @Test // turn - nothing queued
  public void turnNoQueueTest() {
    testGmCh.queuedMove = -1;
    testGmCh.turnUpOrDown();
    assertTrue(testGmCh.noQueue);

    testGmCh.noQueue = false;

    testGmCh.queuedMove = -1;
    testGmCh.turnLeftOrRight();
    assertTrue(testGmCh.noQueue);
  }

  @Test // turnUpOrDown - Up
  public void turnUpTest() {
    testGmCh.queuedMove = 1;
    testGmCh.turnUpOrDown();
    assertEquals(1, testGmCh.getDirection());
  }
  @Test // turnUpOrDown - Below
  public void turnBelowTest() {
    testGmCh.queuedMove = 2;
    testGmCh.turnUpOrDown();
    assertEquals(2, testGmCh.getDirection());
  }
  @Test // turnLeftOrRight - Left
  public void turnLeftTest() {
    testGmCh.queuedMove = 3;
    testGmCh.turnLeftOrRight();
    assertEquals(3, testGmCh.getDirection());
  }
  @Test // turnLeftOrRight - Right
  public void turnRightTest() {
    testGmCh.queuedMove = 4;
    testGmCh.turnLeftOrRight();
    assertEquals(4, testGmCh.getDirection());
  }

  @Test // nextAvaialableExit - Above
  public void exitUpTest() {
    testGmCh.queuedMove = -1;
    testGmCh.nextAvailableExit(64, 48); // Invalid move
    assertEquals(0, testGmCh.getDirection());

    testGmCh.queuedMove = 1;
    testGmCh.nextAvailableExit(64, 48);
    assertEquals(1, testGmCh.getDirection());
  }
  @Test // nextAvaialableExit - Below
  public void exitDownTest() {
    testGmCh.queuedMove = -1;
    testGmCh.nextAvailableExit(64, 80); // Invalid move
    assertEquals(0, testGmCh.getDirection());

    testGmCh.queuedMove = 2;
    testGmCh.nextAvailableExit(64, 80);
    assertEquals(2, testGmCh.getDirection());
  }
  @Test // nextAvaialableExit - Left
  public void exitLeftTest() {
    testGmCh.queuedMove = -1;
    testGmCh.nextAvailableExit(48, 64); // Invalid move
    assertEquals(0, testGmCh.getDirection());

    testGmCh.queuedMove = 3;
    testGmCh.nextAvailableExit(48, 64);
    assertEquals(3, testGmCh.getDirection());
  } 
  @Test // nextAvaialableExit - Right
  public void exitRightTest() {
    testGmCh.queuedMove = -1;
    testGmCh.nextAvailableExit(80, 64); // Invalid move
    assertEquals(0, testGmCh.getDirection());

    testGmCh.queuedMove = 4;
    testGmCh.nextAvailableExit(80, 64);
    assertEquals(4, testGmCh.getDirection());
  }

  @Test // collision - Above
  public void collisionAboveTest() {
    testGmCh.setDirection(1);
    testGmCh.queuedMove = 3;
    testGmCh.collision(0, 0); // No wall in way
    assertEquals(1, testGmCh.getDirection());

    testGmCh.queuedMove = 3;
    testGmCh.collision(64, 48); // Turn Left
    assertEquals(3, testGmCh.getDirection());

    testGmCh.setDirection(1);
    testGmCh.queuedMove = 4;
    testGmCh.collision(65, 48); // Turn right
    assertEquals(4, testGmCh.getDirection());
  }
  @Test // collision - Below
  public void collisionBelowTest() {
    testGmCh.setDirection(2);
    testGmCh.queuedMove = 3;
    testGmCh.collision(0, 0); // No wall in way
    assertEquals(2, testGmCh.getDirection());

    testGmCh.queuedMove = 3;
    testGmCh.collision(64, 80); // Turn left
    assertEquals(3, testGmCh.getDirection());

    testGmCh.setDirection(2);
    testGmCh.queuedMove = 4;
    testGmCh.collision(65, 80); // Turn right
    assertEquals(4, testGmCh.getDirection());
  }
  @Test // collision - Left
  public void collisionLeftTest() {
    testGmCh.setDirection(3);
    testGmCh.queuedMove = 1;
    testGmCh.collision(0, 0); // No wall in way
    assertEquals(3, testGmCh.getDirection());

    testGmCh.queuedMove = 1;
    testGmCh.collision(48, 64); // Turn up
    assertEquals(1, testGmCh.getDirection());

    testGmCh.setDirection(2);
    testGmCh.queuedMove = 2;
    testGmCh.collision(48, 65); // Turn down
    assertEquals(2, testGmCh.getDirection());
  }
  @Test // collision - Right
  public void collisionRightTest() {
    testGmCh.setDirection(4);
    testGmCh.queuedMove = 1;
    testGmCh.collision(0, 0); // No wall in way
    assertEquals(4, testGmCh.getDirection());

    testGmCh.queuedMove = 1;
    testGmCh.collision(80, 64); // Turn up
    assertEquals(1, testGmCh.getDirection());

    testGmCh.setDirection(2);
    testGmCh.queuedMove = 2;;
    testGmCh.collision(80, 65); // Turn down
    assertEquals(2, testGmCh.getDirection());
  }
}