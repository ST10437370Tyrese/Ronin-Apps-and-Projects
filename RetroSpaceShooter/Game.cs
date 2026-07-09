using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;
using System.Media;
using System.IO;

namespace RetroSpaceShooter
{
    public class Game
    {
        private Player player;
        private List<Enemy> enemies;
        private List<Bullet> bullets;
        private List<Explosion> explosions;
        private List<PowerUp> powerUps;
        private List<Particle> particles;
        private BossEnemy boss;
        private int score;
        private int highScore;
        private bool isGameOver;
        private int enemySpawnTimer;
        private int enemySpawnInterval;
        private Random random;
        private int screenWidth;
        private int screenHeight;
        private int frameCount;
        private bool isRestarting;
        private int bossSpawnTimer;
        private int bossSpawnInterval;
        private bool bossActive;
        private SoundPlayer backgroundMusic;
        private SoundPlayer shootSound;
        private SoundPlayer explosionSound;
        private SoundPlayer powerUpSound;
        private SoundPlayer gameOverSound;
        private string currentWeapon;
        private int weaponTimer;
        private bool musicPlaying;
        private List<Bullet> bossBullets;

        public int Score => score;
        public int HighScore => highScore;
        public bool IsGameOver => isGameOver;
        public string CurrentWeapon => currentWeapon;

        public Game(int width, int height)
        {
            screenWidth = width;
            screenHeight = height;
            random = new Random();
            bossBullets = new List<Bullet>();
            LoadHighScore();
            LoadSounds();
            InitializeGame();
        }

        private void LoadHighScore()
        {
            try
            {
                string path = Path.Combine(Application.StartupPath, "highscore.txt");
                if (File.Exists(path))
                {
                    highScore = int.Parse(File.ReadAllText(path));
                }
                else
                {
                    highScore = 0;
                }
            }
            catch
            {
                highScore = 0;
            }
        }

        private void SaveHighScore()
        {
            try
            {
                string path = Path.Combine(Application.StartupPath, "highscore.txt");
                File.WriteAllText(path, highScore.ToString());
            }
            catch { }
        }

        private void LoadSounds()
        {
            try
            {
                backgroundMusic = new SoundPlayer();
                shootSound = new SoundPlayer();
                explosionSound = new SoundPlayer();
                powerUpSound = new SoundPlayer();
                gameOverSound = new SoundPlayer();
            }
            catch
            {
                // Silent fail if sounds not available
            }
        }

        private void InitializeGame()
        {
            player = new Player(screenWidth / 2 - 20, screenHeight - 80);
            enemies = new List<Enemy>();
            bullets = new List<Bullet>();
            explosions = new List<Explosion>();
            powerUps = new List<PowerUp>();
            particles = new List<Particle>();
            bossBullets = new List<Bullet>();
            boss = null;
            score = 0;
            isGameOver = false;
            isRestarting = false;
            enemySpawnTimer = 0;
            enemySpawnInterval = 60;
            frameCount = 0;
            bossSpawnTimer = 0;
            bossSpawnInterval = 600;
            bossActive = false;
            currentWeapon = "Single";
            weaponTimer = 0;
            musicPlaying = false;

            PlayBackgroundMusic();
        }

        public void Restart()
        {
            if (score > highScore)
            {
                highScore = score;
                SaveHighScore();
            }
            InitializeGame();
        }

        private void PlayBackgroundMusic()
        {
            try
            {
                musicPlaying = true;
            }
            catch { }
        }

        private void PlayShootSound()
        {
            try
            {
                System.Media.SystemSounds.Beep.Play();
            }
            catch { }
        }

        private void PlayExplosionSound()
        {
            try { }
            catch { }
        }

        private void PlayPowerUpSound()
        {
            try
            {
                System.Media.SystemSounds.Asterisk.Play();
            }
            catch { }
        }

        private void PlayGameOverSound()
        {
            try
            {
                System.Media.SystemSounds.Hand.Play();
            }
            catch { }
        }

        public void Update()
        {
            if (isGameOver)
            {
                if (isRestarting)
                {
                    Restart();
                    isRestarting = false;
                }
                return;
            }

            frameCount++;

            // Update player
            player.Update();

            // Update weapon timer
            if (weaponTimer > 0)
            {
                weaponTimer--;
                if (weaponTimer == 0)
                {
                    currentWeapon = "Single";
                }
            }

            // Spawn enemies
            enemySpawnTimer++;
            if (enemySpawnTimer >= enemySpawnInterval && !bossActive)
            {
                enemySpawnTimer = 0;
                int enemyType = random.Next(3);
                int x = random.Next(20, screenWidth - 40);
                enemies.Add(new Enemy(x, -30, enemyType));

                if (enemySpawnInterval > 20)
                {
                    enemySpawnInterval = Math.Max(20, enemySpawnInterval - 1);
                }
            }

            // Spawn boss
            if (!bossActive && !isGameOver)
            {
                bossSpawnTimer++;
                if (bossSpawnTimer >= bossSpawnInterval)
                {
                    bossSpawnTimer = 0;
                    boss = new BossEnemy(screenWidth / 2 - 50, -80);
                    bossActive = true;
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

            // Update boss bullets
            for (int i = bossBullets.Count - 1; i >= 0; i--)
            {
                bossBullets[i].Update();
                if (bossBullets[i].IsOffScreen(screenHeight))
                {
                    bossBullets.RemoveAt(i);
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

            // Update boss - FIXED: Check if boss exists before updating
            if (boss != null)
            {
                boss.Update();

                if (boss.IsOffScreen(screenHeight))
                {
                    boss = null;
                    bossActive = false;
                }
                else if (boss.CanShoot)
                {
                    // Boss shooting - create boss bullets
                    boss.Shoot();
                    // Add boss bullets
                    bossBullets.Add(new Bullet(boss.X + 45, boss.Y + 70));
                    bossBullets.Add(new Bullet(boss.X + 25, boss.Y + 70));
                    bossBullets.Add(new Bullet(boss.X + 65, boss.Y + 70));
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

            // Update particles
            for (int i = particles.Count - 1; i >= 0; i--)
            {
                particles[i].Update();
                if (particles[i].IsDead)
                {
                    particles.RemoveAt(i);
                }
            }

            // Update power-ups
            for (int i = powerUps.Count - 1; i >= 0; i--)
            {
                powerUps[i].Update();
                if (powerUps[i].IsOffScreen(screenHeight))
                {
                    powerUps.RemoveAt(i);
                }
            }

            // Spawn particles for player thrusters
            if (player.IsMoving)
            {
                for (int i = 0; i < 2; i++)
                {
                    particles.Add(new Particle(
                        player.X + 18 + random.Next(-5, 5),
                        player.Y + 38 + random.Next(-2, 2),
                        random.Next(-2, 3),
                        random.Next(1, 4),
                        Color.Orange,
                        10 + random.Next(10)
                    ));
                }
            }

            // Collision detection: Bullets vs Enemies
            for (int i = bullets.Count - 1; i >= 0; i--)
            {
                for (int j = enemies.Count - 1; j >= 0; j--)
                {
                    if (bullets[i].GetBounds().IntersectsWith(enemies[j].GetBounds()))
                    {
                        explosions.Add(new Explosion(enemies[j].X, enemies[j].Y));
                        PlayExplosionSound();

                        if (random.Next(10) < 3)
                        {
                            int powerUpType = random.Next(3);
                            powerUps.Add(new PowerUp(enemies[j].X, enemies[j].Y, powerUpType));
                        }

                        enemies.RemoveAt(j);
                        bullets.RemoveAt(i);
                        score += 10;
                        break;
                    }
                }
            }

            // Collision detection: Bullets vs Boss - FIXED: Check if boss exists
            if (boss != null)
            {
                for (int i = bullets.Count - 1; i >= 0; i--)
                {
                    if (bullets[i].GetBounds().IntersectsWith(boss.GetBounds()))
                    {
                        boss.TakeDamage(10);
                        explosions.Add(new Explosion(bullets[i].X, bullets[i].Y));
                        bullets.RemoveAt(i);

                        // FIXED: Check if boss is dead after taking damage
                        if (boss.IsDead)
                        {
                            // Store boss position before nullifying
                            int bossX = boss.X;
                            int bossY = boss.Y;

                            // Create explosion at boss position
                            explosions.Add(new Explosion(bossX, bossY));
                            PlayExplosionSound();
                            score += 100;

                            // Boss drops multiple power-ups
                            for (int p = 0; p < 3; p++)
                            {
                                int powerUpType = random.Next(3);
                                powerUps.Add(new PowerUp(
                                    bossX + random.Next(-50, 50),
                                    bossY + random.Next(-50, 50),
                                    powerUpType
                                ));
                            }

                            // Now set boss to null
                            boss = null;
                            bossActive = false;
                        }
                    }
                }
            }

            // Collision detection: Player vs Enemies
            foreach (Enemy enemy in enemies)
            {
                if (player.GetBounds().IntersectsWith(enemy.GetBounds()))
                {
                    explosions.Add(new Explosion(player.X, player.Y));
                    PlayExplosionSound();
                    isGameOver = true;
                    PlayGameOverSound();
                    SaveHighScore();
                    break;
                }
            }

            // Collision detection: Player vs Boss - FIXED: Check if boss exists
            if (boss != null && player.GetBounds().IntersectsWith(boss.GetBounds()))
            {
                explosions.Add(new Explosion(player.X, player.Y));
                PlayExplosionSound();
                isGameOver = true;
                PlayGameOverSound();
                SaveHighScore();
            }

            // Collision detection: Player vs Boss Bullets
            foreach (Bullet bossBullet in bossBullets)
            {
                if (player.GetBounds().IntersectsWith(bossBullet.GetBounds()))
                {
                    explosions.Add(new Explosion(player.X, player.Y));
                    PlayExplosionSound();
                    isGameOver = true;
                    PlayGameOverSound();
                    SaveHighScore();
                    break;
                }
            }

            // Collision detection: Player vs Power-ups
            for (int i = powerUps.Count - 1; i >= 0; i--)
            {
                if (player.GetBounds().IntersectsWith(powerUps[i].GetBounds()))
                {
                    ApplyPowerUp(powerUps[i].Type);
                    PlayPowerUpSound();
                    powerUps.RemoveAt(i);
                }
            }

            // Player shooting
            if (player.IsShooting)
            {
                ShootWeapon();
                player.IsShooting = false;
            }
        }

        private void ShootWeapon()
        {
            PlayShootSound();

            switch (currentWeapon)
            {
                case "Single":
                    bullets.Add(new Bullet(player.X + 18, player.Y - 10));
                    break;
                case "Double":
                    bullets.Add(new Bullet(player.X + 8, player.Y - 10));
                    bullets.Add(new Bullet(player.X + 28, player.Y - 10));
                    break;
                case "Triple":
                    bullets.Add(new Bullet(player.X + 18, player.Y - 10));
                    bullets.Add(new Bullet(player.X + 5, player.Y - 5));
                    bullets.Add(new Bullet(player.X + 31, player.Y - 5));
                    break;
                case "Rapid":
                    bullets.Add(new Bullet(player.X + 18, player.Y - 10));
                    bullets.Add(new Bullet(player.X + 18, player.Y - 20));
                    break;
            }
        }

        private void ApplyPowerUp(int type)
        {
            switch (type)
            {
                case 0:
                    currentWeapon = "Double";
                    weaponTimer = 600;
                    break;
                case 1:
                    currentWeapon = "Triple";
                    weaponTimer = 600;
                    break;
                case 2:
                    currentWeapon = "Rapid";
                    weaponTimer = 600;
                    break;
            }
        }

        public void Draw(Graphics g)
        {
            DrawStars(g);

            player.Draw(g);

            foreach (Enemy enemy in enemies)
            {
                enemy.Draw(g);
            }

            // Draw boss only if it exists
            if (boss != null)
            {
                boss.Draw(g);
            }

            foreach (Bullet bullet in bullets)
            {
                bullet.Draw(g);
            }

            foreach (Bullet bossBullet in bossBullets)
            {
                bossBullet.Draw(g);
            }

            foreach (Explosion explosion in explosions)
            {
                explosion.Draw(g);
            }

            foreach (Particle particle in particles)
            {
                particle.Draw(g);
            }

            foreach (PowerUp powerUp in powerUps)
            {
                powerUp.Draw(g);
            }
        }

        private void DrawStars(Graphics g)
        {
            for (int i = 0; i < 50; i++)
            {
                int x = (i * 137 + i * 97) % 800;
                int y = (i * 251 + frameCount * (i % 3 + 1)) % 600;
                int size = i % 3 + 1;
                g.FillRectangle(Brushes.White, x, y, size, size);
            }
        }

        public void HandleKeyDown(Keys key)
        {
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

        public void Cleanup()
        {
            SaveHighScore();
            try { }
            catch { }
        }
    }
}