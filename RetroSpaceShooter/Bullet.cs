using System.Drawing;

namespace RetroSpaceShooter
{
    public class Bullet
    {
        public int X { get; private set; }
        public int Y { get; private set; }
        private int width = 4;
        private int height = 12;
        private int speed = 8;

        public Bullet(int x, int y)
        {
            X = x;
            Y = y;
        }

        public void Update()
        {
            Y -= speed;
        }

        public void Draw(Graphics g)
        {
            // Retro pixel-style bullet
            g.FillRectangle(Brushes.Cyan, X, Y, width, height);
            // Glow effect
            g.FillRectangle(new SolidBrush(Color.FromArgb(100, Color.Cyan)), X - 2, Y - 2, width + 4, height + 4);
        }

        public Rectangle GetBounds()
        {
            return new Rectangle(X, Y, width, height);
        }

        public bool IsOffScreen(int screenHeight)
        {
            return Y < -20;
        }
    }
}