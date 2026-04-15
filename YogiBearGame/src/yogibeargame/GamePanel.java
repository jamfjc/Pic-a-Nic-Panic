/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibeargame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Main game panel that handles game logic, rendering, and input.
 * Contains the game loop and all game entities.
 * @author jamesmadoc
 */
public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private Level currentLevel;
    private Timer gameTimer;
    private Timer clockTimer;
    private int elapsedSeconds;
    private boolean gameRunning;
    private int currentLevelNumber;
    private HighScoreManager scoreManager;
    
    private boolean wPressed, aPressed, sPressed, dPressed;
    private static final int MOVE_SPEED = 5;
    
    /**
     * Creates a new game panel and initializes the game.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(144, 238, 144));
        setFocusable(true);
        addKeyListener(this);
        
        scoreManager = new HighScoreManager();
        currentLevel = new Level();
        currentLevelNumber = 1;
        
        initGame();
        
        gameTimer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameRunning) {
                    updateGame();
                    repaint();
                }
            }
        });
        
        clockTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameRunning) {
                    elapsedSeconds++;
                }
            }
        });
        
        gameTimer.start();
        clockTimer.start();
    }
    
    /**
     * Initializes a new game, loading the first level.
     */
    public void initGame() {
        currentLevel.loadLevel(currentLevelNumber);
        player = new Player(currentLevel.getPlayerSpawnX(), currentLevel.getPlayerSpawnY());
        elapsedSeconds = 0;
        gameRunning = true;
    }
    
    /**
     * Restarts the game from level 1.
     */
    public void restartGame() {
        currentLevelNumber = 1;
        initGame();
    }
    
    /**
     * Updates game state each frame.
     * Handles player movement, collisions, and ranger updates.
     */
    private void updateGame() {
        player.update();
        
        int oldX = player.getX();
        int oldY = player.getY();
        
        if (wPressed) player.move(0, -MOVE_SPEED);
        if (sPressed) player.move(0, MOVE_SPEED);
        if (aPressed) player.move(-MOVE_SPEED, 0);
        if (dPressed) player.move(MOVE_SPEED, 0);
        
        if (player.getX() < 0) player.setX(0);
        if (player.getY() < 0) player.setY(0);
        if (player.getX() > getWidth() - player.getWidth()) {
            player.setX(getWidth() - player.getWidth());
        }
        if (player.getY() > getHeight() - player.getHeight()) {
            player.setY(getHeight() - player.getHeight());
        }
        
        for (int i = 0; i < currentLevel.getObstacles().size(); i++) {
            if (player.intersects(currentLevel.getObstacles().get(i))) {
                player.setX(oldX);
                player.setY(oldY);
                break;
            }
        }
        
        boolean levelComplete = false;
        for (int i = 0; i < currentLevel.getBaskets().size(); i++) {
            Basket basket = currentLevel.getBaskets().get(i);
            if (!basket.isCollected() && player.intersects(basket)) {
                basket.collect();
                player.collectBasket();
                
                if (currentLevel.getRemainingBaskets() == 0) {
                    levelComplete = true;
                }
            }
        }
        
        if (levelComplete) {
            loadNextLevel();
            return;
        }
        
        for (int i = 0; i < currentLevel.getRangers().size(); i++) {
            Ranger ranger = currentLevel.getRangers().get(i);
            ranger.update();
            
            if (!player.isInvulnerable() && ranger.isCloseToPlayer(player)) {
                handlePlayerHit();
                break;
            }
        }
    }
    
    /**
     * Handles player being hit by a ranger.
     * Player loses a life and respawns or game ends.
     */
    private void handlePlayerHit() {
        player.loseLife();
        
        if (player.getLives() > 0) {
            player.respawn();
        } else {
            endGame();
        }
    }
    
    /**
     * Loads the next level in sequence.
     * Cycles back to level 1 after level 10.
     */
    private void loadNextLevel() {
        currentLevelNumber++;
        if (currentLevelNumber > 10) {
            currentLevelNumber = 1;
        }
        
        boolean loaded = currentLevel.loadLevel(currentLevelNumber);
        player.setX(currentLevel.getPlayerSpawnX());
        player.setY(currentLevel.getPlayerSpawnY());
        player.updateSpawn(currentLevel.getPlayerSpawnX(), currentLevel.getPlayerSpawnY());
    }
    
    /**
     * Ends the game and shows the score entry dialog.
     */
    private void endGame() {
        gameRunning = false;
        int finalScore = player.getBasketsCollected();
        
        if (scoreManager.isHighScore(finalScore)) {
            String name = JOptionPane.showInputDialog(this, 
                "Game Over! You collected " + finalScore + " baskets!\n" +
                "Enter your name for the high score:", 
                "High Score!", 
                JOptionPane.PLAIN_MESSAGE);
            
            if (name != null && !name.trim().isEmpty()) {
                scoreManager.addScore(name.trim(), finalScore);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Game Over! You collected " + finalScore + " baskets!", 
                "Game Over", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Shows the high score table in a dialog.
     */
    public void showHighScores() {
        StringBuilder sb = new StringBuilder();
        sb.append("TOP 10 HIGH SCORES\n\n");
        sb.append(String.format("%-20s %s\n", "Player", "Score"));
        sb.append("--------------------------------\n");
        
        ArrayList<HighScoreManager.ScoreEntry> scores = scoreManager.getScores();
        for (int i = 0; i < scores.size(); i++) {
            HighScoreManager.ScoreEntry entry = scores.get(i);
            sb.append(String.format("%-20s %d\n", entry.getPlayerName(), entry.getScore()));
        }
        
        if (scores.isEmpty()) {
            sb.append("No scores yet!");
        }
        
        JOptionPane.showMessageDialog(this, sb.toString(), "High Scores", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Paints all game elements on the screen.
     * 
     * @param g the Graphics object
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (Obstacle obstacle : currentLevel.getObstacles()) {
            obstacle.draw(g);
        }
        
        for (Basket basket : currentLevel.getBaskets()) {
            basket.draw(g);
        }
        
        for (Ranger ranger : currentLevel.getRangers()) {
            ranger.draw(g);
        }
        
        player.draw(g);
        
        g.setColor(Color.BLACK);
        g.drawString("Lives: " + player.getLives(), 10, 20);
        g.drawString("Baskets: " + player.getBasketsCollected(), 10, 40);
        g.drawString("Level: " + currentLevel.getLevelNumber(), 10, 60);
        g.drawString("Time: " + elapsedSeconds + "s", 10, 80);
        g.drawString("Remaining: " + currentLevel.getRemainingBaskets(), 10, 100);
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) wPressed = true;
        if (key == KeyEvent.VK_S) sPressed = true;
        if (key == KeyEvent.VK_A) aPressed = true;
        if (key == KeyEvent.VK_D) dPressed = true;
    }
    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) wPressed = false;
        if (key == KeyEvent.VK_S) sPressed = false;
        if (key == KeyEvent.VK_A) aPressed = false;
        if (key == KeyEvent.VK_D) dPressed = false;
    }
    
    public void keyTyped(KeyEvent e) {
    }
}