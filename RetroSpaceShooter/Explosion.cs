using System.Drawing;

namespace RetroSpaceShooter
{
    public class Explosion
    {
        public int X { get; private set; }
        public int Y { get; private set; }
        private int size;
        private int maxSize;
        private int alpha;
        private bool expanding;
        public bool IsFinished { get; private set; }

        public Explosion(int x, int y)
        {
            X = x;
            Y = y;
            size = 5;
            maxSize = 60;
            alpha = 255;
            expanding = true;
            IsFinished = false;
        }

        public void Update()
        {
            if (expanding)
            {
                size += 3;
                alpha -= 10;
                if (size >= maxSize)
                {
                    expanding = false;
                }
            }
            else
            {
                size -= 1;
                if (size <= 0)
                {
                    IsFinished = true;
                }
            }
        }

        public void Draw(Graphics g)
        {
            if (IsFinished) return;

            // Draw explosion with fading effect
            using (SolidBrush brush = new SolidBrush(Color.FromArgb(alpha, Color.Orange)))
            {
                g.FillEllipse(brush, X - size / 2, Y - size / 2, size, size);
            }

            using (SolidBrush brush = new SolidBrush(Color.FromArgb(alpha / 2, Color.Red)))
            {
                g.FillEllipse(brush, X - size / 4, Y - size / 4, size / 2, size / 2);
            }

            using (SolidBrush brush = new SolidBrush(Color.FromArgb(alpha, Color.Yellow)))
            {
                g.FillEllipse(brush, X - size / 6, Y - size / 6, size / 3, size / 3);
            }
        }
    }
}