/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package platformergame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
My Platformer game by Ronin Tyrese Mauries
 */
public class PlatformerGame extends JPanel implements ActionListener, KeyListener {

    // --- Game Constants and Player Properties ---
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int PLAYER_SIZE = 30;
    private static final int JUMP_FORCE = -15;
    private static final double GRAVITY = 0.8;
    private static final int WIN_BLOCK_SIZE = 40;

    private int playerX;
    private int playerY;
    private int playerVelocityX;
    private double playerVelocityY;
    private boolean isJumping;

    private int currentLevel = 1;
    private Rectangle winBlock;
    
    // Power-up state
    private boolean isSpeedBoostActive = false;
    private long speedBoostEndTime = 0;
    private int playerBaseSpeed = 5;

    // --- Game Objects ---
    private final ArrayList<Rectangle> platforms;
    private final ArrayList<PowerUp> powerUps;
    private static final int POWER_UP_SIZE = 20;

    // --- Game Timer ---
    private final Timer timer;
    private final JLabel winLabel;

    /**
     * Represents a power-up in the game.
     * @param x The x-coordinate of the power-up.
     * @param y The y-coordinate of the power-up.
     * @param type The type of the power-up (e.g., "speed").
     */
    private record PowerUp(int x, int y, String type) {}

    public PlatformerGame() {
        // Set up the game panel
        setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setFocusable(true);
        addKeyListener(this);

        // Initialize lists
        platforms = new ArrayList<>();
        powerUps = new ArrayList<>();

        // Create the win label
        winLabel = new JLabel("", SwingConstants.CENTER);
        winLabel.setForeground(Color.GREEN);
        winLabel.setFont(new Font("Arial", Font.BOLD, 48));
        this.add(winLabel, BorderLayout.CENTER);
        winLabel.setVisible(false);

        // Load the first level
        loadLevel(currentLevel);

        // Set up the game loop timer
        timer = new Timer(15, this);
        timer.start();
    }

    /**
     * Loads a new game level, clearing existing platforms and creating new ones.
     * @param level The level number to load.
     */
    private void loadLevel(int level) {
        // Clear previous level's data
        platforms.clear();
        powerUps.clear();
        playerX = 100;
        playerY = 400;
        playerVelocityX = 0;
        playerVelocityY = 0;
        isJumping = false;
        isSpeedBoostActive = false;
        winLabel.setVisible(false);

        // Define platforms for each level
        switch (level) {
            case 1:
                platforms.add(new Rectangle(0, GAME_HEIGHT - 50, GAME_WIDTH, 50));
                platforms.add(new Rectangle(200, 450, 150, 20));
                platforms.add(new Rectangle(400, 350, 100, 20));
                platforms.add(new Rectangle(600, 250, 150, 20));
                platforms.add(new Rectangle(100, 200, 150, 20));
                platforms.add(new Rectangle(350, 100, 100, 20));
                // Win block for level 1
                winBlock = new Rectangle(700, 100, WIN_BLOCK_SIZE, WIN_BLOCK_SIZE);
                break;
            case 2:
                platforms.add(new Rectangle(0, GAME_HEIGHT - 50, GAME_WIDTH, 50));
                platforms.add(new Rectangle(100, 400, 100, 20));
                platforms.add(new Rectangle(350, 300, 100, 20));
                platforms.add(new Rectangle(600, 200, 150, 20));
                platforms.add(new Rectangle(200, 100, 100, 20));
                platforms.add(new Rectangle(500, 150, 100, 20));
                // Add power-up to level 2
                powerUps.add(new PowerUp(400, 480, "speed"));
                // Win block for level 2
                winBlock = new Rectangle(50, 50, WIN_BLOCK_SIZE, WIN_BLOCK_SIZE);
                break;
            case 3:
                platforms.add(new Rectangle(0, GAME_HEIGHT - 50, GAME_WIDTH, 50));
                platforms.add(new Rectangle(50, 450, 100, 20));
                platforms.add(new Rectangle(200, 350, 100, 20));
                platforms.add(new Rectangle(350, 250, 100, 20));
                platforms.add(new Rectangle(500, 150, 100, 20));
                platforms.add(new Rectangle(650, 50, 100, 20));
                // Add a power-up for level 3
                powerUps.add(new PowerUp(50, 300, "speed"));
                // Win block for level 3
                winBlock = new Rectangle(700, 50, WIN_BLOCK_SIZE, WIN_BLOCK_SIZE);
                break;
            default:
                // Game over/win state
                winLabel.setText("You Win! Game Over.");
                winLabel.setVisible(true);
                timer.stop();
                break;
        }
    }

    /**
     * This method handles all the game logic and is called by the timer.
     * @param e The ActionEvent from the Timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Apply gravity to player's vertical velocity
        playerVelocityY += GRAVITY;

        // Update player's position based on velocity
        playerX += playerVelocityX;
        playerY += playerVelocityY;

        // Check for collisions
        checkCollisions();
        
        // Check for speed boost duration
        if (isSpeedBoostActive && System.currentTimeMillis() > speedBoostEndTime) {
            isSpeedBoostActive = false;
            playerBaseSpeed = 5; // Reset to original speed
        }

        // Repaint the panel to show the updated state
        repaint();
    }

    /**
     * Checks for collisions between the player and all platforms, power-ups, and the win block.
     */
    private void checkCollisions() {
        Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);

        // Check platform collisions
        for (Rectangle platform : platforms) {
            if (playerRect.intersects(platform)) {
                if (playerVelocityY > 0) {
                    playerVelocityY = 0;
                    playerY = platform.y - PLAYER_SIZE; // Snap player to top of platform
                    isJumping = false;
                }
            }
        }
        
        // Check power-up collisions
        ArrayList<PowerUp> collectedPowerUps = new ArrayList<>();
        for (PowerUp pu : powerUps) {
            if (playerRect.intersects(new Rectangle(pu.x, pu.y, POWER_UP_SIZE, POWER_UP_SIZE))) {
                collectedPowerUps.add(pu);
                if (pu.type.equals("speed")) {
                    isSpeedBoostActive = true;
                    playerBaseSpeed = 10; // Double the speed
                    speedBoostEndTime = System.currentTimeMillis() + 5000; // 5-second boost
                }
            }
        }
        powerUps.removeAll(collectedPowerUps);

        // Check win block collision
        if (playerRect.intersects(winBlock)) {
            currentLevel++;
            if (currentLevel <= 3) {
                loadLevel(currentLevel);
            } else {
                winLabel.setText("You Win! Game Over.");
                winLabel.setVisible(true);
                timer.stop();
            }
        }

        // Keep player within bounds horizontally
        if (playerX < 0) playerX = 0;
        if (playerX + PLAYER_SIZE > GAME_WIDTH) playerX = GAME_WIDTH - PLAYER_SIZE;
    }

    /**
     * This method is called to draw all game elements on the screen.
     * @param g The Graphics object to draw on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        
        g2d.setColor(Color.CYAN);
        g2d.fillOval(playerX, playerY, PLAYER_SIZE, PLAYER_SIZE);

        
        g2d.setColor(Color.WHITE);
        for (Rectangle platform : platforms) {
            g2d.fill(platform);
        }
        
        
        g2d.setColor(Color.YELLOW);
        for (PowerUp pu : powerUps) {
            g2d.fillOval(pu.x, pu.y, POWER_UP_SIZE, POWER_UP_SIZE);
        }

        
        g2d.setColor(Color.GREEN);
        g2d.fill(winBlock);

        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Level: " + currentLevel, 10, 25);

        // Synchronize drawing
        Toolkit.getDefaultToolkit().sync();
    }

    // --- KeyListener methods for player input ---
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            playerVelocityX = -playerBaseSpeed;
        } else if (key == KeyEvent.VK_RIGHT) {
            playerVelocityX = playerBaseSpeed;
        } else if (key == KeyEvent.VK_SPACE && !isJumping) {
            playerVelocityY = JUMP_FORCE;
            isJumping = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            playerVelocityX = 0;
        }
    }

    //Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simple Platformer Game");
            PlatformerGame game = new PlatformerGame();
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
