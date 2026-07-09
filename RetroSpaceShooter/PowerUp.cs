using System.Drawing;

namespace RetroSpaceShooter
{
    public class PowerUp
    {
        public int X { get; private set; }
        public int Y { get; private set; }
        public int Type { get; private set; } // 0: Double, 1: Triple, 2: Rapid
        private int width = 20;
        private int height = 20;
        private int speed = 2;
        private Color color;
        private string symbol;

        public PowerUp(int x, int y, int type)
        {
            X = x;
            Y = y;
            Type = type;

            switch (type)
            {
                case 0:
                    color = Color.Cyan;
                    symbol = "2";
                    break;
                case 1:
                    color = Color.Magenta;
                    symbol = "3";
                    break;
                case 2:
                    color = Color.Yellow;
                    symbol = "R";
                    break;
            }
        }

        public void Update()
        {
            Y += speed;
        }

        public void Draw(Graphics g)
        {
            // Glow effect
            g.FillEllipse(new SolidBrush(Color.FromArgb(50, color)), X - 5, Y - 5, width + 10, height + 10);

            // Main shape
            g.FillEllipse(new SolidBrush(color), X, Y, width, height);
            g.DrawEllipse(new Pen(Color.White, 2), X, Y, width, height);

            // Symbol
            using (Font font = new Font("Arial", 12, FontStyle.Bold))
            {
                SizeF textSize = g.MeasureString(symbol, font);
                g.DrawString(symbol, font, Brushes.White,
                    X + (width - textSize.Width) / 2,
                    Y + (height - textSize.Height) / 2);
            }
        }

        public Rectangle GetBounds()
        {
            return new Rectangle(X, Y, width, height);
        }

        public bool IsOffScreen(int screenHeight)
        {
            return Y > screenHeight + 20;
        }
    }
}