package brickbreaker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private int lives = 3;
    private int level = 1;
    private Timer timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator map;
    private PowerUp[] powerUps = new PowerUp[5];
    private boolean paddleExpanded = false;
    private long paddleExpandTime = 0;
    private boolean ballSlowed = false;
    private long ballSlowTime = 0;
    private HighScoreManager highScoreManager;
    private boolean showHighScores = false;

    public Gameplay() {
        highScoreManager = new HighScoreManager();
        map = new MapGenerator(3, 7);
        
        for (int i = 0; i < powerUps.length; i++) {
            powerUps[i] = new PowerUp(0, 0, (i % 3) + 1);
        }
        
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // Draw map
        map.draw((Graphics2D)g);

        // Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Scores and info
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
        
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Lives: " + lives, 20, 30);
        g.drawString("Level: " + level, 20, 60);

        // Power-up indicators
        if (paddleExpanded) {
            g.setColor(Color.GREEN);
            g.drawString("Paddle Expanded", 200, 30);
        }
        if (ballSlowed) {
            g.setColor(Color.BLUE);
            g.drawString("Ball Slowed", 350, 30);
        }

        // The paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, paddleExpanded ? 150 : 100, 8);

        // The ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        // Power-ups
        for (PowerUp powerUp : powerUps) {
            if (powerUp.active) {
                powerUp.draw((Graphics2D)g);
            }
        }

        // Game over/win conditions
        if (totalBricks <= 0) {
            if (level < 5) {
                g.setColor(Color.GREEN);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("Level Complete!", 230, 300);
                
                g.setFont(new Font("serif", Font.BOLD, 20));
                g.drawString("Press Enter for Next Level", 220, 350);
            } else {
                gameWon(g);
            }
        }

        if (ballposY > 570) {
            lives--;
            if (lives <= 0) {
                gameOver(g);
            } else {
                play = false;
                ballXdir = 0;
                ballYdir = 0;
                g.setColor(Color.RED);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("Life Lost!", 260, 300);
                
                g.setFont(new Font("serif", Font.BOLD, 20));
                g.drawString("Press Enter to Continue", 230, 350);
            }
        }

        // High scores display
        if (showHighScores) {
            drawHighScores(g);
        }

        g.dispose();
    }

    private void gameWon(Graphics g) {
        play = false;
        if (highScoreManager.isHighScore(score)) {
            highScoreManager.addScore(score);
            showHighScores = true;
        }
        
        g.setColor(Color.RED);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("You Won All Levels!", 180, 300);
        g.drawString("Final Score: " + score, 240, 350);
        
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Press Enter to Restart", 230, 400);
    }

    private void gameOver(Graphics g) {
        play = false;
        if (highScoreManager.isHighScore(score)) {
            highScoreManager.addScore(score);
            showHighScores = true;
        }
        
        g.setColor(Color.RED);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("Game Over, Scores: " + score, 190, 300);
        
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Press Enter to Restart", 230, 350);
    }

    private void drawHighScores(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(150, 150, 400, 300);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("High Scores", 260, 190);
        
        ArrayList<Integer> scores = highScoreManager.getHighScores();
        g.setFont(new Font("serif", Font.BOLD, 20));
        
        for (int i = 0; i < scores.size(); i++) {
            g.drawString((i+1) + ". " + scores.get(i), 250, 230 + i * 30);
        }
        
        g.drawString("Press Enter to Continue", 210, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        
        long currentTime = System.currentTimeMillis();
        if (paddleExpanded && currentTime - paddleExpandTime > 10000) {
            paddleExpanded = false;
        }
        if (ballSlowed && currentTime - ballSlowTime > 5000) {
            ballSlowed = false;
            ballXdir = (ballXdir > 0) ? 2 : -2;
            ballYdir = (ballYdir > 0) ? 2 : -2;
        }
        
        if (play) {
            // Move power-ups
            for (PowerUp powerUp : powerUps) {
                if (powerUp.active) {
                    powerUp.move();
                    if (powerUp.y > 600) {
                        powerUp.active = false;
                    }
                }
            }
            
            // Check power-up collisions
            Rectangle paddleRect = new Rectangle(playerX, 550, paddleExpanded ? 150 : 100, 8);
            for (PowerUp powerUp : powerUps) {
                if (powerUp.active && paddleRect.intersects(powerUp.getBounds())) {
                    activatePowerUp(powerUp);
                }
            }
            
            // Ball-paddle collision
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(paddleRect)) {
                ballYdir = -ballYdir;
            }
            
            // Brick collisions
            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballRect.intersects(brickRect)) {
                            if (map.hitBrick(i, j)) {
                                score += 5;
                                totalBricks--;
                                spawnPowerUp(brickX + brickWidth/2, brickY + brickHeight/2);
                            }

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    private void activatePowerUp(PowerUp powerUp) {
        switch(powerUp.type) {
            case 1: // Expand paddle
                if (!paddleExpanded) {
                    paddleExpanded = true;
                    paddleExpandTime = System.currentTimeMillis();
                }
                break;
            case 2: // Extra life
                lives++;
                break;
            case 3: // Slow ball
                if (!ballSlowed) {
                    ballSlowed = true;
                    ballXdir = (ballXdir > 0) ? 1 : -1;
                    ballYdir = (ballYdir > 0) ? 1 : -1;
                    ballSlowTime = System.currentTimeMillis();
                }
                break;
        }
        powerUp.active = false;
    }

    private void spawnPowerUp(int x, int y) {
        if (Math.random() < 0.2) {
            for (PowerUp powerUp : powerUps) {
                if (!powerUp.active) {
                    powerUp.x = x;
                    powerUp.y = y;
                    powerUp.active = true;
                    break;
                }
            }
        }
    }

    private void nextLevel() {
        level++;
        play = false;
        ballposX = 120;
        ballposY = 350;
        ballXdir = -1 - (level / 2);
        ballYdir = -2 - (level / 2);
        playerX = 310;
        
        int rows = 3 + (level / 2);
        if (rows > 6) rows = 6;
        map = new MapGenerator(rows, 7);
        totalBricks = rows * 7;
        
        for (PowerUp powerUp : powerUps) {
            powerUp.active = false;
        }
        
        paddleExpanded = false;
        ballSlowed = false;
    }

    private void resetGame() {
        play = true;
        lives = 3;
        score = 0;
        level = 1;
        ballposX = 120;
        ballposY = 350;
        ballXdir = -1;
        ballYdir = -2;
        playerX = 310;
        map = new MapGenerator(3, 7);
        totalBricks = 21;
        
        for (PowerUp powerUp : powerUps) {
            powerUp.active = false;
        }
        
        paddleExpanded = false;
        ballSlowed = false;
        showHighScores = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (showHighScores && e.getKeyCode() == KeyEvent.VK_ENTER) {
            showHighScores = false;
            resetGame();
            return;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600 - (paddleExpanded ? 150 : 100)) {
                playerX = 600 - (paddleExpanded ? 150 : 100);
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                if (totalBricks <= 0 && level < 5) {
                    nextLevel();
                } else if (lives <= 0 || (totalBricks <= 0 && level >= 5)) {
                    resetGame();
                } else {
                    play = true;
                    ballposX = 120;
                    ballposY = 350;
                    ballXdir = -1 - (level / 2);
                    ballYdir = -2 - (level / 2);
                    playerX = 310;
                }
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}