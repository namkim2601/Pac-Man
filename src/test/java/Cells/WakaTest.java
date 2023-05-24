package wakawaka.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import wakawaka.map.*;


class WakaTest {
  Waka testWaka = new Waka(60, 60, 0, 3, 1, 1);

  @Test // ateAllFruit & eat
  public void eatTest() {
    assertFalse(testWaka.ateAllFruit());

    assertFalse(testWaka.eat(0, 0));
    assertFalse(testWaka.eat(64, 0));
    assertTrue(testWaka.eat(64, 64));

    assertTrue(testWaka.ateAllFruit());
  }

  @Test // move -  nothing queued
  public void moveNoQueueTest() {
    testWaka.setDirection(1);
    testWaka.noQueue = true;
    testWaka.move();
    assertEquals(60, testWaka.getX());
  }
  @Test // move - up
  public void moveUpTest() {
    testWaka.setDirection(1);
    testWaka.move();
    assertEquals(59, testWaka.getY());
  }
  @Test // move - down
  public void moveDownTest() {
    testWaka.setDirection(2);
    testWaka.move();
    assertEquals(61, testWaka.getY());
  }
  @Test // move - left
  public void moveLeftTest() {
    testWaka.setDirection(3);
    testWaka.move();
    assertEquals(59, testWaka.getX());
  }
  @Test // move - right
  public void moveRightTest() {
    testWaka.setDirection(4);
    testWaka.move();
    assertEquals(61, testWaka.getX());
  }
}