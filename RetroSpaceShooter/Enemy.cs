using System.Drawing;

namespace RetroSpaceShooter
{
    public class Enemy
    {
        public int X { get; private set; }
        public int Y { get; private set; }
        private int width = 30;
        private int height = 30;
        private int speed;
        private int type; // 0: Basic, 1: Fast, 2: Tank
        private int moveTimer;
        private int moveDirection;
        private Color color;

        public Enemy(int x, int y, int type)
        {
            X = x;
            Y = y;
            this.type = type;
            moveTimer = 0;
            moveDirection = 1;

            switch (type)
            {
                case 0: // Basic
                    speed = 2;
                    color = Color.Red;
                    break;
                case 1: // Fast
                    speed = 4;
                    color = Color.Orange;
                    break;
                case 2: // Tank
                    speed = 1;
                    color = Color.Purple;
                    width = 40;
                    height = 40;
                    break;
            }
        }

        public void Update()
        {
            // Move down
            Y += speed;

            // Slight horizontal movement (zigzag)
            moveTimer++;
            if (moveTimer > 30)
            {
                moveTimer = 0;
                moveDirection *= -1;
            }
            X += moveDirection * (type == 1 ? 2 : 1);

            // Keep within horizontal bounds
            if (X < 0) X = 0;
            if (X > 770) X = 770;
        }

        public void Draw(Graphics g)
        {
            Pen pen = new Pen(color, 2);

            // Draw different enemy types
            if (type == 0) // Basic - simple square
            {
                g.FillRectangle(new SolidBrush(color), X, Y, width, height);
                g.DrawRectangle(pen, X, Y, width, height);
                // Eyes
                g.FillRectangle(Brushes.Black, X + 5, Y + 8, 5, 5);
                g.FillRectangle(Brushes.Black, X + 20, Y + 8, 5, 5);
            }
            else if (type == 1) // Fast - diamond shape
            {
                Point[] points = new Point[]
                {
                    new Point(X + width/2, Y),
                    new Point(X + width, Y + height/2),
                    new Point(X + width/2, Y + height),
                    new Point(X, Y + height/2)
                };
                g.FillPolygon(new SolidBrush(color), points);
                g.DrawPolygon(pen, points);
            }
            else if (type == 2) // Tank - larger square with details
            {
                g.FillRectangle(new SolidBrush(color), X, Y, width, height);
                g.DrawRectangle(pen, X, Y, width, height);
                // Armor lines
                g.DrawLine(pen, X + 10, Y, X + 10, Y + height);
                g.DrawLine(pen, X + 30, Y, X + 30, Y + height);
                // Eyes
                g.FillRectangle(Brushes.Black, X + 8, Y + 10, 6, 6);
                g.FillRectangle(Brushes.Black, X + 26, Y + 10, 6, 6);
            }
        }

        public Rectangle GetBounds()
        {
            return new Rectangle(X, Y, width, height);
        }

        public bool IsOffScreen(int screenHeight)
        {
            return Y > screenHeight + 50;
        }
    }
}