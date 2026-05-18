/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package spaceshooter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple 2D space shooter game implemented in Java using Swing.
 * This class handles all game logic, drawing, and user input.
 */
public class SpaceShooter extends JPanel implements ActionListener, KeyListener {

    // --- Game Constants and Player Properties ---
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int PLAYER_SIZE = 50;
    private static final int PLAYER_SPEED = 12; // Increased player speed
    private static final int BULLET_SIZE = 10;
    private static final int BULLET_SPEED = 10;
    private static final int ENEMY_SIZE = 40;
    private static final int ENEMY_SPEED = 2;
    private static final int SCORE_PER_ENEMY = 10;

    private int playerX;
    private int playerY;
    private int score = 0;
    private boolean gameOver = false;

    // --- Game Objects ---
    private final ArrayList<Rectangle> bullets;
    private final ArrayList<Rectangle> enemies;
    private final Random random = new Random();

    // --- Game Timer ---
    private final Timer timer;

    public SpaceShooter() {
        // Set up the game panel
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Initialize player position and object lists
        playerX = GAME_WIDTH / 2 - PLAYER_SIZE / 2;
        playerY = GAME_HEIGHT - PLAYER_SIZE - 20;
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();

        // Set up the game loop timer
        timer = new Timer(15, this);
        timer.start();
    }

    /**
     * Resets the game to its initial state, allowing a new round to begin.
     */
    private void resetGame() {
        score = 0;
        gameOver = false;
        playerX = GAME_WIDTH / 2 - PLAYER_SIZE / 2;
        playerY = GAME_HEIGHT - PLAYER_SIZE - 20;
        bullets.clear();
        enemies.clear();
        timer.start();
    }

    /**
     * This method handles all the game logic and is called by the timer.
     * @param e The ActionEvent from the Timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            timer.stop();
            return;
        }

        // Move bullets
        for (Rectangle bullet : bullets) {
            bullet.y -= BULLET_SPEED;
        }

        // Move enemies and check for player collision
        Iterator<Rectangle> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Rectangle enemy = enemyIterator.next();
            enemy.y += ENEMY_SPEED;
            if (enemy.y > GAME_HEIGHT) {
                enemyIterator.remove();
            }
            if (new Rectangle(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE).intersects(enemy)) {
                gameOver = true;
            }
        }

        // Check for bullet-enemy collisions
        Iterator<Rectangle> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Rectangle bullet = bulletIterator.next();
            Iterator<Rectangle> enemyCheckIterator = enemies.iterator();
            while (enemyCheckIterator.hasNext()) {
                Rectangle enemy = enemyCheckIterator.next();
                if (bullet.intersects(enemy)) {
                    bulletIterator.remove();
                    enemyCheckIterator.remove();
                    score += SCORE_PER_ENEMY;
                    break;
                }
            }
        }

        // Spawn new enemies randomly
        if (random.nextInt(100) < 5) { // 5% chance to spawn an enemy each frame
            int enemyX = random.nextInt(GAME_WIDTH - ENEMY_SIZE);
            enemies.add(new Rectangle(enemyX, -ENEMY_SIZE, ENEMY_SIZE, ENEMY_SIZE));
        }

        // Repaint the panel to show the updated state
        repaint();
    }

    /**
     * This method is called to draw all game elements on the screen.
     * @param g The Graphics object to draw on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw player spaceship
        g2d.setColor(Color.CYAN);
        g2d.fillOval(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);
        
        // Draw bullets
        g2d.setColor(Color.YELLOW);
        for (Rectangle bullet : bullets) {
            g2d.fill(bullet);
        }

        // Draw enemies
        g2d.setColor(Color.RED);
        for (Rectangle enemy : enemies) {
            g2d.fillRect(enemy.x, enemy.y, ENEMY_SIZE, ENEMY_SIZE);
        }

        // Draw score
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Score: " + score, 10, 30);

        // Draw game over screen if applicable
        if (gameOver) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            String msg = "Game Over!";
            FontMetrics metrics = g2d.getFontMetrics();
            int msgX = (GAME_WIDTH - metrics.stringWidth(msg)) / 2;
            int msgY = metrics.getAscent() + (GAME_HEIGHT - metrics.getHeight()) / 2;
            g2d.drawString(msg, msgX, msgY);

            // Draw restart prompt
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            String restartMsg = "Press Enter to Restart";
            int restartMsgX = (GAME_WIDTH - metrics.stringWidth(restartMsg)) / 2;
            g2d.drawString(restartMsg, restartMsgX, msgY + 40);
        }

        // Synchronize drawing
        Toolkit.getDefaultToolkit().sync();
    }

    // --- KeyListener methods for player input ---
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (gameOver) {
            if (key == KeyEvent.VK_ENTER) {
                resetGame();
            }
            return;
        }

        if (key == KeyEvent.VK_LEFT) {
            playerX -= PLAYER_SPEED;
        } else if (key == KeyEvent.VK_RIGHT) {
            playerX += PLAYER_SPEED;
        } else if (key == KeyEvent.VK_SPACE) {
            // Create a new bullet at the player's position
            bullets.add(new Rectangle(playerX + PLAYER_SIZE / 2 - BULLET_SIZE / 2, playerY, BULLET_SIZE, BULLET_SIZE));
        }

        // Ensure player stays within the game window
        if (playerX < 0) playerX = 0;
        if (playerX + PLAYER_SIZE > GAME_WIDTH) playerX = GAME_WIDTH - PLAYER_SIZE;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    // --- Main method to set up and run the game ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simple Space Shooter Game");
            SpaceShooter game = new SpaceShooter();
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
