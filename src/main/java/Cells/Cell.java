package wakawaka;

public class Cell {
  protected int x;
  
  protected int y;
  
  protected int type;

  /**
  * Constructor for Cell Class
  *
  * @param x x coordinate
  * @param y y coordinate
  */
  public Cell(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
  * Getter method for variable x
  *
  * @return x coordinate of cell
  */
  public int getX() {
    return this.x;
  }

  /**
  * Getter method for variable y
  *
  * @return y coordinate of cell
  */
  public int getY() {
    return this.y;
  }

  /**
  * Setter method for variable x
  *
  * @param x new x coordinate
  */
  public void setX(int x) {
    this.x = x;
  }

  /**
  * Setter method for variable y
  *
  * @param y new y coordinate
  */
  public void setY(int y) {
    this.y = y;
  }

  /**
  * Getter method for variable type
  *
  * @return type of cell
  */
  public int getType() {
    return this.type;
  }

  /**
  * Setter method for variable type
  *
  * @param type new cell type
  */
  public void setType(int type) {
    this.type = type;
  }
}