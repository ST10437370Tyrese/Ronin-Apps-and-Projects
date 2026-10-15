using System.Text;
using System.Drawing.Drawing2D;

namespace ZipExtractor;

public sealed class LoadingForm : Form
{
    private readonly System.Windows.Forms.Timer _timer = new() { Interval = 16 };
    private readonly CancellationTokenSource _cts;
    private readonly Button _cancelButton;

    private float _angle;
    private float _shownPercent;
    private int _targetPercent;
    private string _currentFile = "Preparing...";

    private static readonly Color[] RingColors =
    {
        Color.FromArgb(0, 229, 255),   // cyan
        Color.FromArgb(186, 104, 255), // purple
        Color.FromArgb(255, 82, 164)   // pink
    };

    public LoadingForm(CancellationTokenSource cts)
    {
        _cts = cts;

        FormBorderStyle = FormBorderStyle.None;
        StartPosition = FormStartPosition.CenterParent;
        ShowInTaskbar = false;
        ClientSize = new Size(420, 390);
        BackColor = Color.FromArgb(22, 24, 36);
        DoubleBuffered = true;

        _cancelButton = new Button
        {
            Text = "Cancel",
            Size = new Size(110, 34),
            Location = new Point((420 - 110) / 2, 335),
            FlatStyle = FlatStyle.Flat,
            ForeColor = Color.White,
            BackColor = Color.FromArgb(45, 48, 70),
            Cursor = Cursors.Hand
        };
        _cancelButton.FlatAppearance.BorderColor = Color.FromArgb(90, 95, 130);
        _cancelButton.Click += (_, _) =>
        {
            _cancelButton.Enabled = false;
            _cancelButton.Text = "Cancelling...";
            _cts.Cancel();
        };
        Controls.Add(_cancelButton);

        _timer.Tick += (_, _) =>
        {
            _angle = (_angle + 4f) % 360f;
            _shownPercent += (_targetPercent - _shownPercent) * 0.15f; // smooth easing
            Invalidate();
        };
        _timer.Start();
    }

    /// <summary>Called from the UI thread (via Progress&lt;T&gt;) to update the screen.</summary>
    public void SetProgress(int percent, string file)
    {
        _targetPercent = Math.Clamp(percent, 0, 100);
        _currentFile = file;
    }

    // ---------- Rounded window shape ----------
    protected override void OnHandleCreated(EventArgs e)
    {
        base.OnHandleCreated(e);
        ApplyRoundedRegion();
    }

    private void ApplyRoundedRegion()
    {
        using var path = RoundedRect(new Rectangle(0, 0, Width, Height), 28);
        Region = new Region(path);
    }

    private static GraphicsPath RoundedRect(Rectangle r, int radius)
    {
        int d = radius * 2;
        var p = new GraphicsPath();
        p.AddArc(r.X, r.Y, d, d, 180, 90);
        p.AddArc(r.Right - d, r.Y, d, d, 270, 90);
        p.AddArc(r.Right - d, r.Bottom - d, d, d, 0, 90);
        p.AddArc(r.X, r.Bottom - d, d, d, 90, 90);
        p.CloseFigure();
        return p;
    }

    // ---------- Custom drawing ----------
    protected override void OnPaint(PaintEventArgs e)
    {
        var g = e.Graphics;
        g.SmoothingMode = SmoothingMode.AntiAlias;
        g.TextRenderingHint = System.Drawing.Text.TextRenderingHint.ClearTypeGridFit;

        // Border
        using (var borderPath = RoundedRect(new Rectangle(1, 1, Width - 3, Height - 3), 28))
        using (var borderPen = new Pen(Color.FromArgb(70, 75, 110), 2))
            g.DrawPath(borderPen, borderPath);

        float cx = Width / 2f;
        float cy = 150f;

        // Title
        using (var titleFont = new Font("Segoe UI Semibold", 13f))
        using (var white = new SolidBrush(Color.White))
        {
            var dots = new string('.', (int)(_angle / 90f) % 4);
            var title = "Unzipping" + dots;
            var size = g.MeasureString(title, titleFont);
            g.DrawString(title, titleFont, white, cx - size.Width / 2f - 8, 22);
        }

        // Pulsing glow behind the rings
        float pulse = (float)(Math.Sin(_angle * Math.PI / 180.0) + 1) / 2f; // 0..1
        int glowAlpha = 25 + (int)(pulse * 35);
        using (var glow = new SolidBrush(Color.FromArgb(glowAlpha, 0, 229, 255)))
            g.FillEllipse(glow, cx - 88, cy - 88, 176, 176);

        // Three counter-rotating rings
        for (int i = 0; i < 3; i++)
        {
            float radius = 80 - i * 17;
            var rect = new RectangleF(cx - radius, cy - radius, radius * 2, radius * 2);

            // Faint track
            using (var track = new Pen(Color.FromArgb(35, RingColors[i]), 5))
                g.DrawEllipse(track, rect);

            // Moving arc
            float direction = (i % 2 == 0) ? 1f : -1.6f;
            float start = _angle * direction * (1 + i * 0.4f) + i * 70f;
            float sweep = 95f + i * 35f;

            using var pen = new Pen(RingColors[i], 6 - i)
            {
                StartCap = LineCap.Round,
                EndCap = LineCap.Round
            };
            g.DrawArc(pen, rect, start, sweep);
        }

        // Percentage in the centre
        using (var pctFont = new Font("Segoe UI", 24f, FontStyle.Bold))
        using (var white = new SolidBrush(Color.White))
        {
            string pct = $"{(int)Math.Round(_shownPercent)}%";
            var size = g.MeasureString(pct, pctFont);
            g.DrawString(pct, pctFont, white, cx - size.Width / 2f, cy - size.Height / 2f);
        }

        // Current file name (path ellipsis if too long)
        using (var fileFont = new Font("Segoe UI", 9f))
        using (var grey = new SolidBrush(Color.FromArgb(170, 175, 205)))
        using (var fmt = new StringFormat
        {
            Alignment = StringAlignment.Center,
            Trimming = StringTrimming.EllipsisPath,
            FormatFlags = StringFormatFlags.NoWrap
        })
        {
            g.DrawString(_currentFile, fileFont, grey, new RectangleF(30, 262, Width - 60, 22), fmt);
        }

        // Progress bar
        var barRect = new Rectangle(40, 295, Width - 80, 10);
        using (var trackPath = RoundedRect(barRect, 5))
        using (var trackBrush = new SolidBrush(Color.FromArgb(45, 48, 70)))
            g.FillPath(trackBrush, trackPath);

        int fillWidth = (int)(barRect.Width * (_shownPercent / 100f));
        if (fillWidth > 10)
        {
            var fillRect = new Rectangle(barRect.X, barRect.Y, fillWidth, barRect.Height);
            using var fillPath = RoundedRect(fillRect, 5);
            using var gradient = new LinearGradientBrush(
                fillRect, RingColors[0], RingColors[2], LinearGradientMode.Horizontal);
            g.FillPath(gradient, fillPath);
        }
    }

    protected override void OnFormClosed(FormClosedEventArgs e)
    {
        _timer.Stop();
        _timer.Dispose();
        base.OnFormClosed(e);
    }
}