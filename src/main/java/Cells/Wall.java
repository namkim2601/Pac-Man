package wakawaka.map;

import wakawaka.Cell;

public class Wall extends Cell {
  /** 
  * Constructor for Wall Class
  *
  * @param x x coordinate
  * @param y y coordinate
  * @param type type of wall (1 for horizontal,
  *                             2 for vertical,
  *                             3 for upLeft,
  *                             4 for upRight,
  *                             5 for downLeft,
  *                             6 for downRight)
  */
  public Wall(int x, int y, int type) {
    super(x, y);
    this.setType(type);
  }
}