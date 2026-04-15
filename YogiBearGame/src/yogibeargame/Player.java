/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibeargame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents the player character (Yogi Bear).
 * Handles player movement and lives.
 * @author jamesmadoc
 */
public class Player extends GameEntity {
    private int lives;
    private int basketsCollected;
    private int spawnX;
    private int spawnY;
    private boolean invulnerable;
    private int invulnerabilityTimer;
    
    /**
     * Creates a new player at the specified spawn position.
     * Player starts with 3 lives.
     * 
     * @param spawnX the spawn x coordinate
     * @param spawnY the spawn y coordinate
     */
    public Player(int spawnX, int spawnY) {
        super(spawnX, spawnY, 30, 30);
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.lives = 3;
        this.basketsCollected = 0;
        this.invulnerable = false;
        this.invulnerabilityTimer = 0;
    }
    
    /**
     * Moves the player in the specified direction.
     * 
     * @param dx change in x direction
     * @param dy change in y direction
     */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    /**
     * Respawns the player at the spawn position.
     * Called when player loses a life.
     */
    public void respawn() {
        x = spawnX;
        y = spawnY;
        invulnerable = true;
        invulnerabilityTimer = 60;
    }
    
    /**
     * Updates player state (invulnerability timer).
     */
    public void update() {
        if (invulnerable) {
            invulnerabilityTimer--;
            if (invulnerabilityTimer <= 0) {
                invulnerable = false;
            }
        }
    }
    
    /**
     * Updates spawn position for new levels.
     */
    public void updateSpawn(int spawnX, int spawnY) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }
    
    /**
     * Removes one life from the player.
     */
    public void loseLife() {
        lives--;
    }
    
    /**
     * Increases the basket count.
     */
    public void collectBasket() {
        basketsCollected++;
    }
    
    /**
     * Resets the player for a new game.
     */
    public void reset() {
        lives = 3;
        basketsCollected = 0;
        respawn();
    }
    
    /**
     * Draws the player as a brown circle (representing Yogi Bear).
     * 
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        if (invulnerable && invulnerabilityTimer % 10 < 5) {
            return;
        }
        g.setColor(new Color(139, 69, 19));
        g.fillOval(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
    }
    
    public int getLives() {
        return lives;
    }
    
    public int getBasketsCollected() {
        return basketsCollected;
    }
    
    public void setBasketsCollected(int basketsCollected) {
        this.basketsCollected = basketsCollected;
    }
    
    public boolean isInvulnerable() {
        return invulnerable;
    }
}