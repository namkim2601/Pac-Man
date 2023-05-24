package wakawaka;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import wakawaka.map.*;
import wakawaka.game.*;

public class Parser {
  protected String mapFile;
  
  protected int lives;
  
  protected int speed;
  
  protected int fruitToWin;

  protected List<Integer> ghostsCoords = new ArrayList<Integer>();
  
  protected List<Integer> scatter = new ArrayList<Integer>();
  
  protected List<Integer> chase = new ArrayList<Integer>();

  /**
  * Reads configuration file and parses appropriate data
  * 
  * @param configFile Configuration file path
  * @return true if successful
  * @return false if file path doesnt exist
  */
  public boolean readConfig(String configFile) {
    File f = new File("configs/" + configFile);
    try {
      Scanner scan = new Scanner(f);
      scan.nextLine();
      for (int i = 0; i < Double.POSITIVE_INFINITY; i++) {
        if (scan.hasNextLine()) {
          String[] s = scan.nextLine().split(": ");
          if (i == 0) { // Map
            String txtFile = s[1].replaceAll("\\s+", "").replaceAll(",", "").replaceAll("\"", "");
            this.mapFile = txtFile; 
          } else if (i == 1) { // Lives
            int lives = Integer.parseInt(s[1].replaceAll("\\s+", "").replaceAll(",", ""));
            this.lives = lives; 
          } else if (i == 2) { // Speed
            int speed = Integer.parseInt(s[1].replaceAll("\\s+", "").replaceAll(",", ""));
            this.speed = speed;
          } else if (i > 3) { // Mode Lengths
            String lengthStr = s[0].replaceAll("\\s+", "").replaceAll(",", "");
            if (lengthStr.equals("]"))
              break; 
            if (i % 2 == 0) { // Scatter
              this.scatter.add(Integer.parseInt(lengthStr));
            } else { // Chase
              this.chase.add(Integer.parseInt(lengthStr));
            } 
          } 
        } 
      } 
    } catch (FileNotFoundException e) {
      System.err.println("Configuration Error: File does not exist");
      return false;
    } 
    return true; 
  }
  
  /**
  * Reads map file and parses characters into cells
  * 
  * @return A list of list of cells
  */
  public List<List<Cell>> parseCells() {  
    List<List<Cell>> parsedCells = new ArrayList<>();
    try {
      File f = new File("maps/" + this.mapFile);
      Scanner scan = new Scanner(f);

      boolean wakaExists = false;
      int fruitOnMap = 0;
      int livesParsed = 0;

      int y = 0;
      while (scan.hasNextLine()) {
        String[] row = scan.nextLine().split("");
        List<Cell> rowToAdd = new ArrayList<>();
        for (int x = 0; x < row.length; x++) {
          String cellStr = row[x];
          int posX = x * 16;
          int posY = y * 16;
          if (cellStr.equals("0"))
            // Lives
            if (livesParsed < this.lives && y == 34) {
              rowToAdd.add(new Waka(posX + 1 + livesParsed, posY, 4, 0, 1, this.fruitToWin)); 
              livesParsed++;
            // Air
            } else {
              rowToAdd.add(new Air(posX, posY));
            }  
          // Wall  
          if (cellStr.equals("1") || cellStr.equals("2") || cellStr.equals("3") || cellStr.equals("4") || cellStr.equals("5") || cellStr.equals("6"))
            rowToAdd.add(new Wall(posX, posY, Integer.parseInt(cellStr)));
          // Fruit
          if (cellStr.equals("7")) {
            rowToAdd.add(new Fruit(posX, posY));
            fruitOnMap++;
          }
          // Player 
          if (cellStr.equals("p"))
            if (wakaExists) { // 
              System.err.println("Configuration Error: Only one Waka allowed");
              return null;
            } else {
              rowToAdd.add(new Waka(posX, posY, 0, this.lives, this.speed, 0));
              wakaExists = true;
            }
          // Ghost 
          if (cellStr.equals("g")) {
            rowToAdd.add(new Ghost(posX, posY, this.scatter, this.chase));
            ghostsCoords.add(posX);
            ghostsCoords.add(posY);
          }
      
        } 
        if (rowToAdd.size() != 28) {
          System.err.println("Configuration Error: Character has no corresponding cell");
          return null;
        } 
        parsedCells.add(rowToAdd);
        y++;
      } 
      this.fruitToWin = fruitOnMap;
      if (this.fruitToWin == 0) {
        System.err.println("Configuration Error: Map requires at least 1 fruit");
        return null;
      }
      if (!wakaExists) {
        System.err.println("Configuration Error: Map requires 1 Waka");
        return null;
      }
    } catch (FileNotFoundException e) {
      System.err.println("Configuration Error: File does not exist");
      return null;
    } 
    return parsedCells;
  }

  /**
  * Parses a Waka 
  * 
  * @return Waka with the correct lives, speed and fruitToWin
  */
  public Waka parseWaka() {
    Waka newWaka = new Waka(0, 0, 0, this.lives, this.speed, this.fruitToWin);
    return newWaka;
  }

  /**
  * Parses a Ghost
  * 
  * @return Ghost with the correct mode lengths for scatter and chase
  */
  public List<Ghost> parseGhosts() {
    List<Ghost> newGhosts = new ArrayList<Ghost>();
    for (int i = 0; i < ghostsCoords.size(); i += 2) {
      int x = ghostsCoords.get(i) - 4;
      int y = ghostsCoords.get(i+1) - 4;
      newGhosts.add(new Ghost(x, y, this.scatter, this.chase));
    }
    return newGhosts;
  }
}