/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibeargame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Manages game levels, loading them from files.
 * Each level contains obstacles, rangers, and baskets.
 * @author jamesmadoc
 */
public class Level {
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Ranger> rangers;
    private ArrayList<Basket> baskets;
    private int playerSpawnX;
    private int playerSpawnY;
    private int levelNumber;
    
    /**
     * Creates an empty level.
     */
    public Level() {
        obstacles = new ArrayList<Obstacle>();
        rangers = new ArrayList<Ranger>();
        baskets = new ArrayList<Basket>();
        playerSpawnX = 50;
        playerSpawnY = 50;
    }
    
    /**
     * Loads a level from a file.
     * File format:
     * Line 1: spawn_x spawn_y
     * Following lines: type x y [additional parameters]
     * 
     * @param levelNumber the level number to load (1-10)
     * @return true if level loaded successfully, false otherwise
     */
    public boolean loadLevel(int levelNumber) {
        this.levelNumber = levelNumber;
        obstacles.clear();
        rangers.clear();
        baskets.clear();
        
        String filename = "level" + levelNumber + ".txt";
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            
            if (line != null) {
                String[] spawn = line.split(" ");
                playerSpawnX = Integer.parseInt(spawn[0]);
                playerSpawnY = Integer.parseInt(spawn[1]);
            }
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String type = parts[0];
                
                if (type.equals("tree") || type.equals("mountain")) {
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    int w = Integer.parseInt(parts[3]);
                    int h = Integer.parseInt(parts[4]);
                    obstacles.add(new Obstacle(x, y, w, h, type));
                } else if (type.equals("ranger")) {
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    boolean horizontal = parts[3].equals("h");
                    int min = Integer.parseInt(parts[4]);
                    int max = Integer.parseInt(parts[5]);
                    rangers.add(new Ranger(x, y, horizontal, min, max));
                } else if (type.equals("basket")) {
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    baskets.add(new Basket(x, y));
                }
            }
            reader.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error loading level: " + e.getMessage());
            createDefaultLevel();
            return false;
        }
    }
    
    /**
     * Creates a simple default level if file loading fails.
     */
    private void createDefaultLevel() {
        playerSpawnX = 50;
        playerSpawnY = 50;
        
        obstacles.add(new Obstacle(200, 150, 40, 40, "tree"));
        obstacles.add(new Obstacle(400, 200, 60, 50, "mountain"));
        obstacles.add(new Obstacle(300, 350, 40, 40, "tree"));
        
        rangers.add(new Ranger(150, 300, true, 100, 500));
        rangers.add(new Ranger(450, 100, false, 50, 400));
        
        baskets.add(new Basket(550, 100));
        baskets.add(new Basket(200, 400));
        baskets.add(new Basket(450, 300));
    }
    
    /**
     * Gets the total number of baskets in the level.
     * 
     * @return total basket count
     */
    public int getTotalBaskets() {
        return baskets.size();
    }
    
    /**
     * Gets the number of uncollected baskets.
     * 
     * @return remaining basket count
     */
    public int getRemainingBaskets() {
        int count = 0;
        for (Basket basket : baskets) {
            if (!basket.isCollected()) {
                count++;
            }
        }
        return count;
    }
    
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
    
    public ArrayList<Ranger> getRangers() {
        return rangers;
    }
    
    public ArrayList<Basket> getBaskets() {
        return baskets;
    }
    
    public int getPlayerSpawnX() {
        return playerSpawnX;
    }
    
    public int getPlayerSpawnY() {
        return playerSpawnY;
    }
    
    public int getLevelNumber() {
        return levelNumber;
    }
}