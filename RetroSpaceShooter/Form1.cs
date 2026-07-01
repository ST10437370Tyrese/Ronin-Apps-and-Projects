using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

namespace RetroSpaceShooter
{
    public partial class Form1 : Form
    {
        private Game game;
        private Timer gameTimer;
        private Label scoreLabel;
        private Label gameOverLabel;
        private Label restartLabel;

        public Form1()
        {
            InitializeComponent();
            InitializeCustomComponents();
            InitializeGame();
        }

        private void InitializeCustomComponents()
        {
            this.DoubleBuffered = true;
            this.BackColor = Color.Black;
            this.Size = new Size(800, 600);
            this.StartPosition = FormStartPosition.CenterScreen;
            this.KeyDown += Form1_KeyDown;
            this.KeyUp += Form1_KeyUp;
            this.Paint += Form1_Paint;

            // Score Label
            scoreLabel = new Label();
            scoreLabel.ForeColor = Color.White;
            scoreLabel.Font = new Font("Courier New", 16, FontStyle.Bold);
            scoreLabel.Location = new Point(10, 10);
            scoreLabel.AutoSize = true;
            this.Controls.Add(scoreLabel);

            // Game Over Label
            gameOverLabel = new Label();
            gameOverLabel.ForeColor = Color.Red;
            gameOverLabel.Font = new Font("Courier New", 36, FontStyle.Bold);
            gameOverLabel.Text = "GAME OVER";
            gameOverLabel.Location = new Point(250, 200);
            gameOverLabel.Size = new Size(300, 50);
            gameOverLabel.TextAlign = ContentAlignment.MiddleCenter;
            gameOverLabel.Visible = false;
            this.Controls.Add(gameOverLabel);

            // Restart Label
            restartLabel = new Label();
            restartLabel.ForeColor = Color.White;
            restartLabel.Font = new Font("Courier New", 16, FontStyle.Bold);
            restartLabel.Text = "Press R to Restart";
            restartLabel.Location = new Point(280, 270);
            restartLabel.Size = new Size(240, 30);
            restartLabel.TextAlign = ContentAlignment.MiddleCenter;
            restartLabel.Visible = false;
            this.Controls.Add(restartLabel);
        }

        private void InitializeGame()
        {
            game = new Game(this.ClientSize.Width, this.ClientSize.Height);
            gameTimer = new Timer();
            gameTimer.Interval = 16; // ~60 FPS
            gameTimer.Tick += GameTimer_Tick;
            gameTimer.Start();
        }

        private void GameTimer_Tick(object sender, EventArgs e)
        {
            game.Update();
            UpdateUI();
            this.Invalidate(); // Trigger Paint event
        }

        private void Form1_Paint(object sender, PaintEventArgs e)
        {
            game.Draw(e.Graphics);
        }

        private void Form1_KeyDown(object sender, KeyEventArgs e)
        {
            game.HandleKeyDown(e.KeyCode);
        }

        private void Form1_KeyUp(object sender, KeyEventArgs e)
        {
            game.HandleKeyUp(e.KeyCode);
        }

        private void UpdateUI()
        {
            scoreLabel.Text = $"Score: {game.Score}";
            gameOverLabel.Visible = game.IsGameOver;
            restartLabel.Visible = game.IsGameOver;
        }
    }
}