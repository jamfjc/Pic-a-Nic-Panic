/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yogibeargame;

import java.sql.*;
import java.util.ArrayList;

/**
 * Manages high scores using SQLite database.
 * Stores player names and their scores.
 * @author jamesmadoc
 */
public class HighScoreManager {
    private ArrayList<ScoreEntry> scores;
    private static final String DB_URL = "jdbc:sqlite:highscores.db";
    private static final int MAX_SCORES = 10;
    private Connection conn;
    
    /**
     * Represents a single score entry with player name and score.
     */
    public static class ScoreEntry {
        private String playerName;
        private int score;
        
        public ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }
        
        public String getPlayerName() {
            return playerName;
        }
        
        public int getScore() {
            return score;
        }
    }
    
    /**
     * Creates a new high score manager and loads existing scores.
     */
    public HighScoreManager() {
        scores = new ArrayList<ScoreEntry>();
        initDatabase();
        loadScores();
    }
    
    /**
     * Initializes the database and creates table if needed.
     */
    private void initDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS highscores (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "player_name TEXT NOT NULL," +
                        "score INTEGER NOT NULL)";
            stmt.execute(sql);
            stmt.close();
        } catch (Exception e) {
            System.out.println("Database init error: " + e.getMessage());
        }
    }
    
    /**
     * Adds a new score to the database.
     * 
     * @param playerName the name of the player
     * @param score the score achieved
     */
    public void addScore(String playerName, int score) {
        try {
            String sql = "INSERT INTO highscores (player_name, score) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
            pstmt.close();
            loadScores();
        } catch (SQLException e) {
            System.out.println("Error adding score: " + e.getMessage());
        }
    }
    
    /**
     * Loads top 10 high scores from database.
     */
    private void loadScores() {
        scores.clear();
        try {
            String sql = "SELECT player_name, score FROM highscores ORDER BY score DESC LIMIT ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, MAX_SCORES);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String name = rs.getString("player_name");
                int score = rs.getInt("score");
                scores.add(new ScoreEntry(name, score));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error loading scores: " + e.getMessage());
        }
    }
    
    /**
     * Gets the list of high scores.
     * 
     * @return ArrayList of score entries
     */
    public ArrayList<ScoreEntry> getScores() {
        return scores;
    }
    
    /**
     * Checks if a score qualifies for the high score list.
     * 
     * @param score the score to check
     * @return true if score is high enough
     */
    public boolean isHighScore(int score) {
        if (scores.size() < MAX_SCORES) {
            return true;
        }
        return score > scores.get(scores.size() - 1).getScore();
    }
    
    /**
     * Closes database connection.
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database: " + e.getMessage());
        }
    }
}
