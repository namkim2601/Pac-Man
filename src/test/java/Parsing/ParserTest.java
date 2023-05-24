 package wakawaka;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import wakawaka.map.*;
import wakawaka.game.*;
import java.util.List;

public class ParserTest {
  Parser testParser = new Parser();
  @Test // configuration file doesn't exist
  public void noConfigTest() {
    assertFalse(testParser.readConfig("nosuchfile.json"));
  }
  
  @Test // parseCells - map file doesn't exist
  public void noFileTest() {
    testParser.readConfig("2_NoSuchMapFile.json");
    assertNull(testParser.parseCells());
  }

  @Test // parseCells - bad character
  public void badFileTest() {
    testParser.readConfig("3_BadFile.json");
    assertNull(testParser.parseCells());
  }

  @Test // parseCells - no player
  public void noPlayerTest() {
    testParser.readConfig("4_NoPlayer.json");
    assertNull(testParser.parseCells());
  }

  @Test // parseCells - more than 1 player
  public void tooManyPlayersTest() {
    testParser.readConfig("5_ManyPlayers.json");
    assertNull(testParser.parseCells());
  }

  @Test // parseCells - no fruit
  public void noFruitTest() {
    testParser.readConfig("6_NoFruit.json");
    assertNull(testParser.parseCells());
  }


  @Test // parseWaka
  public void parseWakaTest() {
    testParser.readConfig("config.json");
    testParser.parseCells();
    Waka testWaka = testParser.parseWaka();
    assertNotNull(testWaka);
  }

  @Test // parseGhost    
  public void parseGhostTest() {
    testParser.readConfig("config.json");
    testParser.parseCells();
    List<Ghost> testGhosts = testParser.parseGhosts();
    assertNotNull(testGhosts);
  }

  @Test // parseCells - {0, 1, 2, 3, 4, 5, 6, 7, p, g}
  public void allTypesTest() {
    testParser.readConfig("1_AllCellTypes.json");
    List<List<Cell>> testGrid = testParser.parseCells();
    List<Cell> testRow = testGrid.get(0);
  
    Cell actual = testRow.get(0); // "0" = Air
    assertTrue(actual instanceof Air);

    actual = testRow.get(1); // "1" = Wall
    assertTrue(actual instanceof Wall);

    actual = testRow.get(2); // "2" = Wall
    assertTrue(actual instanceof Wall);

    actual = testRow.get(3); // "3" = Wall
    assertTrue(actual instanceof Wall);

    actual = testRow.get(4); // "4" = Wall
    assertTrue(actual instanceof Wall);

    actual = testRow.get(5); // "5" = Wall
    assertTrue(actual instanceof Wall);

    actual = testRow.get(6); // "6" = Wall
    assertTrue(actual instanceof Wall);

    actual = testRow.get(7); // "7" = Fruit
    assertTrue(actual instanceof Fruit);

    actual = testRow.get(8); // "p" = Waka
    assertTrue(actual instanceof Waka);

    actual = testRow.get(9); // "g" = Ghost
    assertTrue(actual instanceof Ghost);
  }
}