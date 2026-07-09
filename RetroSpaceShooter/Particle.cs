using System.Drawing;

namespace RetroSpaceShooter
{
    public class Particle
    {
        public int X { get; private set; }
        public int Y { get; private set; }
        private int vx;
        private int vy;
        private Color color;
        private int size;
        private int life;
        private int maxLife;
        public bool IsDead => life <= 0;

        public Particle(int x, int y, int vx, int vy, Color color, int life)
        {
            X = x;
            Y = y;
            this.vx = vx;
            this.vy = vy;
            this.color = color;
            size = 3;
            this.life = life;
            maxLife = life;
        }

        public void Update()
        {
            X += vx;
            Y += vy;
            life--;
            size = (int)(3 * ((float)life / maxLife));
        }

        public void Draw(Graphics g)
        {
            if (IsDead) return;

            int alpha = (int)(255 * ((float)life / maxLife));
            using (SolidBrush brush = new SolidBrush(Color.FromArgb(alpha, color)))
            {
                g.FillEllipse(brush, X - size / 2, Y - size / 2, size, size);
            }
        }
    }
}