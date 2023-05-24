package wakawaka;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellTest {
  Cell testCell = new Cell(20, 20);

  @Test // getX and setX
  public void xTest() {
    assertEquals(20, testCell.getX());

    testCell.setX(10);
    assertEquals(10, testCell.getX());
  }

  @Test //getY and setY
  public void yTest() {
    assertEquals(20, testCell.getY());

    testCell.setY(10);
    assertEquals(10, testCell.getY());
  }

  @Test //getType and setType
  public void typeTest() {
    testCell.setType(4);
    assertEquals(4, testCell.getType());
  }
}

  