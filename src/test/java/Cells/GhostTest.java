package wakawaka.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;  
import java.util.ArrayList;
import java.util.List;  

public class GhostTest {
  List<Integer> testScatter = new ArrayList<Integer>();
  List<Integer> testChase = new ArrayList<Integer>();
  
  @Test // getNextScatter, rmLastScatter, getNextChase & rmLastChase
  public void scatterAndChaseTest() {
    testScatter.add(7);
    testScatter.add(5);
    testChase.add(20);
    testChase.add(15);

    Ghost testGhost = new Ghost(0, 0, testScatter, testChase);

    // Scatter
    assertEquals(7, testGhost.getNextScatter());
    testGhost.rmLastScatter();
    assertEquals(5, testGhost.getNextScatter());

    // Chase
    assertEquals(20, testGhost.getNextChase());
    testGhost.rmLastChase();
    assertEquals(15, testGhost.getNextChase());
  }

  @Test // moveToPlayer
  public void moveToPlayerTest() {
    Ghost testGhost = new Ghost(0, 0, testScatter, testChase);
    Waka testWaka = new Waka(0, 0, 0, 0, 0, 0);
    // Function behaves the same for when (ghost.direction = 1) & when (ghost.direction = 3)
    testGhost.setDirection(1);

    testWaka.setY(64);
    assertTrue(testGhost.changeDirection(testWaka)); // Move in opposite direction
    assertEquals(2, testGhost.direction);
    assertEquals(0, testGhost.queuedMove);
    assertFalse(testGhost.noQueue);

    testGhost.setDirection(1);
    testGhost.noQueue = true;
    testGhost.setX(64);
    testWaka.setY(0);
    assertTrue(testGhost.changeDirection(testWaka)); // Move in perpendicular direction at wall

    // Function behaves the same for when (ghost.direction = 2) & when (ghost.direction = 4)
    testGhost.setDirection(2);
    testGhost.setX(0);
    testGhost.setY(64);
    assertTrue(testGhost.changeDirection(testWaka)); // Move in opposite direction
    assertEquals(1, testGhost.direction);
    assertEquals(0, testGhost.queuedMove);
    assertFalse(testGhost.noQueue);

    testGhost.setDirection(2);
    testGhost.noQueue = true;
    testWaka.setX(64);
    testGhost.setY(0);
    assertTrue(testGhost.changeDirection(testWaka)); // Move in perpendicular direction at wall
  }

  @Test // changeDirection - Ghost uses queued move at next available exit
  public void queuedMoveTest() {
    Ghost testGhost = new Ghost(0, 0, testScatter, testChase);
    testGhost.setDirection(3);
    assertTrue(testGhost.moveToPlayer(1));
    assertEquals(1, testGhost.queuedMove); // Takes next exit above

    testGhost.setDirection(4);
    assertTrue(testGhost.moveToPlayer(2));
    assertEquals(2, testGhost.queuedMove); // Takes next exit below

    testGhost.setDirection(1);
    assertTrue(testGhost.moveToPlayer(3));
    assertEquals(3, testGhost.queuedMove); // Takes next exit on the left

    testGhost.setDirection(2);
    assertTrue(testGhost.moveToPlayer(4));
    assertEquals(4, testGhost.queuedMove);  // Takes next exit on the right
  }
}
    