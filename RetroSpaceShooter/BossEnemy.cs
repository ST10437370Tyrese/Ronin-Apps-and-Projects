using System;
using System.Drawing;

namespace RetroSpaceShooter
{
    public class BossEnemy
    {
        public int X { get; private set; }
        public int Y { get; private set; }
        private int width = 100;
        private int height = 80;
        private int health = 100;
        private int maxHealth = 100;
        private int speed = 1;
        private int moveTimer;
        private int moveDirection;
        private int shootTimer;
        private int shootInterval;
        private Random random;
        public bool IsDead => health <= 0;
        public bool CanShoot => shootTimer >= shootInterval;

        public BossEnemy(int x, int y)
        {
            X = x;
            Y = y;
            random = new Random();
            moveDirection = 1;
            shootTimer = 0;
            shootInterval = 30; // Frames between shots
        }

        public void Update()
        {
            // Move down until reaching position
            if (Y < 50)
            {
                Y += speed;
            }
            else
            {
                // Zigzag movement
                moveTimer++;
                if (moveTimer > 60)
                {
                    moveTimer = 0;
                    moveDirection *= -1;
                }
                X += moveDirection * speed * 2;
                X = MathHelper.Clamp(X, 20, 680);
            }

            shootTimer++;
            if (shootTimer > shootInterval)
            {
                shootTimer = 0;
            }
        }

        public void Shoot()
        {
            shootTimer = 0;
            // Boss shoots 3 bullets in a spread
            // In a full implementation, you'd create boss bullets here
        }

        public void TakeDamage(int damage)
        {
            health -= damage;
        }

        public void Draw(Graphics g)
        {
            // Boss body (large diamond)
            Pen pen = new Pen(Color.Red, 3);
            Point[] points = new Point[]
            {
                new Point(X + width/2, Y),
                new Point(X + width, Y + height/2),
                new Point(X + width/2, Y + height),
                new Point(X, Y + height/2)
            };
            g.FillPolygon(new SolidBrush(Color.DarkRed), points);
            g.DrawPolygon(pen, points);

            // Health bar
            float healthPercent = (float)health / maxHealth;
            g.FillRectangle(Brushes.Red, X, Y - 20, width, 10);
            g.FillRectangle(new SolidBrush(Color.Green), X, Y - 20, width * healthPercent, 10);
            g.DrawRectangle(Pens.White, X, Y - 20, width, 10);

            // Eyes
            g.FillEllipse(Brushes.Yellow, X + 20, Y + 25, 15, 15);
            g.FillEllipse(Brushes.Yellow, X + 65, Y + 25, 15, 15);
            g.FillEllipse(Brushes.Black, X + 25, Y + 30, 5, 5);
            g.FillEllipse(Brushes.Black, X + 70, Y + 30, 5, 5);

            // Cannons
            g.FillRectangle(Brushes.Red, X + 10, Y + 50, 10, 20);
            g.FillRectangle(Brushes.Red, X + width - 20, Y + 50, 10, 20);
        }

        public Rectangle GetBounds()
        {
            return new Rectangle(X, Y, width, height);
        }

        public bool IsOffScreen(int screenHeight)
        {
            return Y > screenHeight + 100;
        }
    }
}