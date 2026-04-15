/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibeargame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * Base class for all game entities (player, rangers, baskets, obstacles).
 * Contains common properties like position and size.
 * @author jamesmadoc
 */
public abstract class GameEntity {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    /**
     * Creates a new game entity at the specified position.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width of the entity
     * @param height the height of the entity
     */
    public GameEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Draws the entity on the screen.
     * 
     * @param g the Graphics object used for drawing
     */
    public abstract void draw(Graphics g);
    
    /**
     * Gets the bounding rectangle of this entity.
     * Used for collision detection.
     * 
     * @return Rectangle representing the entity's bounds
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    /**
     * Checks if this entity intersects with another entity.
     * 
     * @param other the other entity to check collision with
     * @return true if entities collide, false otherwise
     */
    public boolean intersects(GameEntity other) {
        return this.getBounds().intersects(other.getBounds());
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}