package brickbreaker;

import java.awt.*;

public class PowerUp {
    public int x, y;
    public int width = 20, height = 10;
    public boolean active = false;
    public int type; // 1=expand paddle, 2=extra life, 3=slow ball
    private Color color;
    
    public PowerUp(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        
        switch(type) {
            case 1: color = Color.GREEN; break;
            case 2: color = Color.RED; break;
            case 3: color = Color.BLUE; break;
            default: color = Color.YELLOW;
        }
    }
    
    public void draw(Graphics2D g) {
        if (active) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
            
            // Draw symbol based on type
            g.setColor(Color.BLACK);
            switch(type) {
                case 1: // Expand
                    g.fillRect(x + 5, y + 2, 10, 6);
                    break;
                case 2: // Extra life
                    g.fillOval(x + 5, y + 2, 10, 6);
                    break;
                case 3: // Slow ball
                    g.fillRect(x + 2, y + 5, 16, 2);
                    break;
            }
        }
    }
    
    public void move() {
        if (active) {
            y += 2; // Move downward
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}