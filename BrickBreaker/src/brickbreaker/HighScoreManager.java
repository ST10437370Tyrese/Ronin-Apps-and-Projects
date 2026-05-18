package brickbreaker;

import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String HIGHSCORE_FILE = "highscores.dat";
    private ArrayList<Integer> highScores;
    
    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadHighScores();
    }
    
    private void loadHighScores() {
        try {
            File file = new File(HIGHSCORE_FILE);
            if (!file.exists()) {
                return;
            }
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            highScores = (ArrayList<Integer>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Error loading high scores: " + e.getMessage());
        }
    }
    
    public void saveHighScores() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            oos.writeObject(highScores);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving high scores: " + e.getMessage());
        }
    }
    
    public void addScore(int score) {
        highScores.add(score);
        Collections.sort(highScores, Collections.reverseOrder());
        if (highScores.size() > 10) {
            highScores = new ArrayList<>(highScores.subList(0, 10));
        }
        saveHighScores();
    }
    
    public ArrayList<Integer> getHighScores() {
        return highScores;
    }
    
    public boolean isHighScore(int score) {
        if (highScores.size() < 10) return true;
        return score > highScores.get(highScores.size() - 1);
    }
}