/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibeargame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a picnic basket that the player can collect.
 * @author jamesmadoc
 */
public class Basket extends GameEntity {
    private boolean collected;
    
    /**
     * Creates a new basket at the specified position.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Basket(int x, int y) {
        super(x, y, 20, 20);
        this.collected = false;
    }
    
    /**
     * Marks this basket as collected.
     */
    public void collect() {
        collected = true;
    }
    
    /**
     * Draws the basket as a brown square with a handle.
     * Only draws if not collected.
     * 
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        if (!collected) {
            g.setColor(new Color(160, 82, 45));
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.drawArc(x + 5, y - 8, width - 10, 15, 0, -180);
        }
    }
    
    public boolean isCollected() {
        return collected;
    }
}
