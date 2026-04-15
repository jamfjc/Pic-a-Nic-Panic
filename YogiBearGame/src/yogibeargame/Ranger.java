/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibeargame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a park ranger enemy.
 * Rangers move horizontally or vertically and can harm the player.
 * @author jamesmadoc
 */
public class Ranger extends GameEntity {
    private int direction;
    private int speed;
    private boolean movesHorizontally;
    private int minBound;
    private int maxBound;
    
    /**
     * Creates a new ranger.
     * 
     * @param x starting x position
     * @param y starting y position
     * @param movesHorizontally true if ranger moves horizontally, false for vertical
     * @param minBound minimum movement boundary
     * @param maxBound maximum movement boundary
     */
    public Ranger(int x, int y, boolean movesHorizontally, int minBound, int maxBound) {
        super(x, y, 25, 25);
        this.movesHorizontally = movesHorizontally;
        this.direction = 1;
        this.speed = 2;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }
    
    /**
     * Updates the ranger's position.
     * Rangers patrol back and forth within their boundaries.
     */
    public void update() {
        if (movesHorizontally) {
            x += direction * speed;
            if (x <= minBound || x >= maxBound) {
                direction *= -1;
            }
        } else {
            y += direction * speed;
            if (y <= minBound || y >= maxBound) {
                direction *= -1;
            }
        }
    }
    
    /**
     * Checks if the ranger is close to the player.
     * Close means within one unit distance.
     * 
     * @param player the player to check distance to
     * @return true if ranger is close to player
     */
    public boolean isCloseToPlayer(Player player) {
        int dx = Math.abs(this.x - player.getX());
        int dy = Math.abs(this.y - player.getY());
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        return distance <= 35;
    }
    
    /**
     * Draws the ranger as a blue rectangle with a hat.
     * 
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x + 5, y - 5, width - 10, 5);
    }
}