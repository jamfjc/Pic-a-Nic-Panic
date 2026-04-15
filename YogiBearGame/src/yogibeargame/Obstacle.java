/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibeargame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents an obstacle (tree or mountain) in the game.
 * Obstacles block player movement.
 * @author jamesmadoc
 */
public class Obstacle extends GameEntity {
    private String type;
    
    /**
     * Creates a new obstacle.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width
     * @param height the height
     * @param type the type of obstacle ("tree" or "mountain")
     */
    public Obstacle(int x, int y, int width, int height, String type) {
        super(x, y, width, height);
        this.type = type;
    }
    
    /**
     * Draws the obstacle.
     * Trees are drawn as green circles with brown trunks.
     * Mountains are drawn as gray triangles.
     * 
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        if (type.equals("tree")) {
            g.setColor(new Color(34, 139, 34));
            g.fillOval(x, y, width, height);
            g.setColor(new Color(101, 67, 33));
            g.fillRect(x + width / 2 - 5, y + height - 10, 10, 15);
        } else if (type.equals("mountain")) {
            g.setColor(Color.GRAY);
            int[] xPoints = {x, x + width / 2, x + width};
            int[] yPoints = {y + height, y, y + height};
            g.fillPolygon(xPoints, yPoints, 3);
            g.setColor(Color.DARK_GRAY);
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }
    
    public String getType() {
        return type;
    }
}
