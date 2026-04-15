/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibeargame;

import javax.swing.*;
import java.awt.event.*;

/**
 * Main game window with menu bar.
 * Contains the game panel and menu options.
 * @author jamesmadoc
 */
public class GameWindow extends JFrame {
    private GamePanel gamePanel;
    
    /**
     * Creates the main game window with menu bar.
     */
    public GameWindow() {
        setTitle("Yogi Bear - Picnic Basket Collector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        gamePanel = new GamePanel();
        add(gamePanel);
        
        createMenuBar();
        
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Creates the menu bar with Game menu.
     * Menu items: New Game, High Scores, Exit.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    GameWindow.this,
                    "Start a new game? Current progress will be lost.",
                    "New Game",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (choice == JOptionPane.YES_OPTION) {
                    gamePanel.restartGame();
                    gamePanel.requestFocusInWindow();
                }
            }
        });
        
        JMenuItem highScoresItem = new JMenuItem("High Scores");
        highScoresItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.showHighScores();
            }
        });
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    GameWindow.this,
                    "Are you sure you want to exit?",
                    "Exit Game",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        gameMenu.add(newGameItem);
        gameMenu.add(highScoresItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }
}