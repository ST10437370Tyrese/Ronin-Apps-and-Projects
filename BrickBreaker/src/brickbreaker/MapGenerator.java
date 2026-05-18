package brickbreaker;

import java.awt.*;
import java.util.Random;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    private Random random;
    
    public MapGenerator(int row, int col) {
        random = new Random();
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                // 70% normal, 20% hard, 10% unbreakable
                double r = random.nextDouble();
                if (r < 0.7) map[i][j] = 1;      // Normal
                else if (r < 0.9) map[i][j] = 2; // Hard
                else map[i][j] = 3;              // Unbreakable
            }
        }
        
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    if (map[i][j] == 1) {
                        g.setColor(Color.WHITE); // Normal brick
                    } else if (map[i][j] == 2) {
                        g.setColor(Color.ORANGE); // Hard brick
                    } else {
                        g.setColor(Color.GRAY); // Unbreakable
                    }
                    
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }
    
    public boolean hitBrick(int row, int col) {
        if (map[row][col] == 1) { // Normal brick
            map[row][col] = 0;
            return true;
        } else if (map[row][col] == 2) { // Hard brick
            map[row][col] = 1; // Turn into normal brick
            return false;
        }
        return false; // Unbreakable brick
    }
    
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}