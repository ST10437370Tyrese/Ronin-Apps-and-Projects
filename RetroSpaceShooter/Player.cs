using System.Drawing;

namespace RetroSpaceShooter
{
    public class Player
    {
        public int X { get; private set; }
        public int Y { get; private set; }
        private int width = 40;
        private int height = 40;
        private int speed = 5;
        public bool MoveLeft { get; set; }
        public bool MoveRight { get; set; }
        public bool MoveUp { get; set; }
        public bool MoveDown { get; set; }
        public bool IsShooting { get; set; }
        public bool IsMoving => MoveLeft || MoveRight || MoveUp || MoveDown;

        public Player(int x, int y)
        {
            X = x;
            Y = y;
        }

        public void Update()
        {
            if (MoveLeft) X -= speed;
            if (MoveRight) X += speed;
            if (MoveUp) Y -= speed;
            if (MoveDown) Y += speed;

            X = MathHelper.Clamp(X, 0, 760);
            Y = MathHelper.Clamp(Y, 0, 560);
        }

        public void Draw(Graphics g)
        {
            Pen greenPen = new Pen(Color.LimeGreen, 2);

            // Ship body (triangle)
            Point[] shipPoints = new Point[]
            {
                new Point(X + 20, Y),
                new Point(X, Y + 35),
                new Point(X + 10, Y + 35),
                new Point(X + 10, Y + 40),
                new Point(X + 30, Y + 40),
                new Point(X + 30, Y + 35),
                new Point(X + 40, Y + 35)
            };
            g.FillPolygon(Brushes.LimeGreen, shipPoints);
            g.DrawPolygon(greenPen, shipPoints);

            // Cockpit
            g.FillRectangle(Brushes.DarkGreen, X + 15, Y + 8, 10, 12);
            g.DrawRectangle(greenPen, X + 15, Y + 8, 10, 12);

            // Shield indicator (if any)
            g.DrawRectangle(new Pen(Color.Cyan, 1), X - 2, Y - 2, width + 4, height + 4);
        }

        public Rectangle GetBounds()
        {
            return new Rectangle(X, Y, width, height);
        }
    }
}