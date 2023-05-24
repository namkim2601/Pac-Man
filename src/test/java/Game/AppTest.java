package wakawaka;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;


public class AppTest {
  @Test // Constructor and setup method
  public void appTest() {
      App testApp = new App();
      try {
      PApplet.runSketch(new String[] {""}, testApp);
      } catch (Exception e) {
        fail();
      }
      testApp.delay(1000);
      testApp.noLoop();

      assertNotNull(testApp.gameManager);

      testApp.setup();
      assertNotNull(testApp.fruit); // Fruit image is loaded
      assertNotNull(testApp.ghost); // Ghost image is loaded
      assertEquals(12, testApp.wallImages.size()); // 6 Wall images are loaded
      assertEquals(10, testApp.playerImages.size()); // 5 Player images are loaded
  }
}


