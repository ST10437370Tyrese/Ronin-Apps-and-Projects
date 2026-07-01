using RetroSpaceShooter;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

namespace RetroSpaceShooter
{
    public class Game
    {
        private Player player;
        private List<Enemy> enemies;
        private List<Bullet> bullets;
        private List<Explosion> explosions;
        private int score;
        private bool isGameOver;
        private int enemySpawnTimer;
        private int enemySpawnInterval;
        private Random random;
        private int screenWidth;
        private int screenHeight;
        private int frameCount;
        private bool isRestarting;

        public int Score => score;
        public bool IsGameOver => isGameOver;

        public Game(int width, int height)
        {
            screenWidth = width;
            screenHeight = height;
            random = new Random();
            InitializeGame();
        }

        private void InitializeGame()
        {
            player = new Player(screenWidth / 2 - 20, screenHeight - 80);
            enemies = new List<Enemy>();
            bullets = new List<Bullet>();
            explosions = new List<Explosion>();
            score = 0;
            isGameOver = false;
            isRestarting = false;
            enemySpawnTimer = 0;
            enemySpawnInterval = 60; // Frames between spawns
            frameCount = 0;
        }

        public void Update()
        {
            if (isGameOver)
            {
                if (isRestarting)
                {
                    InitializeGame();
                    isRestarting = false;
                }
                return;
            }

            frameCount++;

            // Update player
            player.Update();

            // Spawn enemies
            enemySpawnTimer++;
            if (enemySpawnTimer >= enemySpawnInterval)
            {
                enemySpawnTimer = 0;
                int enemyType = random.Next(3);
                int x = random.Next(20, screenWidth - 40);
                enemies.Add(new Enemy(x, -30, enemyType));

                // Increase difficulty
                if (enemySpawnInterval > 20)
                {
                    enemySpawnInterval = Math.Max(20, enemySpawnInterval - 1);
                }
            }

            // Update bullets
            for (int i = bullets.Count - 1; i >= 0; i--)
            {
                bullets[i].Update();
                if (bullets[i].IsOffScreen(screenHeight))
                {
                    bullets.RemoveAt(i);
                }
            }

            // Update enemies
            for (int i = enemies.Count - 1; i >= 0; i--)
            {
                enemies[i].Update();
                if (enemies[i].IsOffScreen(screenHeight))
                {
                    enemies.RemoveAt(i);
                }
            }

            // Update explosions
            for (int i = explosions.Count - 1; i >= 0; i--)
            {
                explosions[i].Update();
                if (explosions[i].IsFinished)
                {
                    explosions.RemoveAt(i);
                }
            }

            // Collision detection: Bullets vs Enemies
            for (int i = bullets.Count - 1; i >= 0; i--)
            {
                for (int j = enemies.Count - 1; j >= 0; j--)
                {
                    if (bullets[i].GetBounds().IntersectsWith(enemies[j].GetBounds()))
                    {
                        // Enemy hit
                        explosions.Add(new Explosion(enemies[j].X, enemies[j].Y));
                        enemies.RemoveAt(j);
                        bullets.RemoveAt(i);
                        score += 10;
                        break;
                    }
                }
            }

            // Collision detection: Player vs Enemies
            foreach (Enemy enemy in enemies)
            {
                if (player.GetBounds().IntersectsWith(enemy.GetBounds()))
                {
                    explosions.Add(new Explosion(player.X, player.Y));
                    isGameOver = true;
                    break;
                }
            }

            // Player shooting
            if (player.IsShooting)
            {
                bullets.Add(new Bullet(player.X + 18, player.Y - 10));
                player.IsShooting = false;
            }
        }

        public void Draw(Graphics g)
        {
            // Draw background stars
            DrawStars(g);

            // Draw entities
            player.Draw(g);

            foreach (Enemy enemy in enemies)
            {
                enemy.Draw(g);
            }

            foreach (Bullet bullet in bullets)
            {
                bullet.Draw(g);
            }

            foreach (Explosion explosion in explosions)
            {
                explosion.Draw(g);
            }
        }

        private void DrawStars(Graphics g)
        {
            // Simple static stars
            g.FillRectangle(Brushes.White, 100, 50, 2, 2);
            g.FillRectangle(Brushes.White, 200, 150, 2, 2);
            g.FillRectangle(Brushes.White, 500, 30, 2, 2);
            g.FillRectangle(Brushes.White, 600, 200, 2, 2);
            g.FillRectangle(Brushes.White, 300, 400, 2, 2);
            g.FillRectangle(Brushes.White, 700, 350, 2, 2);
            g.FillRectangle(Brushes.White, 50, 300, 2, 2);
            g.FillRectangle(Brushes.White, 400, 500, 2, 2);
            g.FillRectangle(Brushes.White, 650, 450, 2, 2);
            g.FillRectangle(Brushes.White, 150, 250, 2, 2);
        }

        public void HandleKeyDown(Keys key)
        {
            if (key == Keys.R && isGameOver)
            {
                isRestarting = true;
                return;
            }

            if (isGameOver) return;

            switch (key)
            {
                case Keys.Left:
                    player.MoveLeft = true;
                    break;
                case Keys.Right:
                    player.MoveRight = true;
                    break;
                case Keys.Up:
                    player.MoveUp = true;
                    break;
                case Keys.Down:
                    player.MoveDown = true;
                    break;
                case Keys.Space:
                    player.IsShooting = true;
                    break;
            }
        }

        public void HandleKeyUp(Keys key)
        {
            if (isGameOver) return;

            switch (key)
            {
                case Keys.Left:
                    player.MoveLeft = false;
                    break;
                case Keys.Right:
                    player.MoveRight = false;
                    break;
                case Keys.Up:
                    player.MoveUp = false;
                    break;
                case Keys.Down:
                    player.MoveDown = false;
                    break;
            }
        }
    }
}