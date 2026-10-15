using System.Drawing.Drawing2D;
using System.Drawing.Text;

namespace ZipExtractor;

public sealed class LoadingForm : Form
{
    private const int W = 440;
    private const int H = 440;
    private const int BaseTop = 250;      // top surface of the anvil
    private const float CrateFull = 52f;  // crate height when not squashed
    private const float CrateMin = 14f;   // crate height when fully squashed

    private readonly System.Windows.Forms.Timer _timer = new() { Interval = 16 };
    private readonly CancellationTokenSource _cts;
    private readonly Button _cancelButton;

    private float _phase;          // 0..1, one full press stroke
    private float _shownPercent;
    private int _targetPercent;
    private string _currentFile = "Preparing...";

    public LoadingForm(CancellationTokenSource cts)
    {
        _cts = cts;

        FormBorderStyle = FormBorderStyle.None;
        StartPosition = FormStartPosition.CenterParent;
        ShowInTaskbar = false;
        ClientSize = new Size(W, H);
        BackColor = Color.FromArgb(24, 26, 34);
        DoubleBuffered = true;

        _cancelButton = new Button
        {
            Text = "Cancel",
            Size = new Size(110, 34),
            Location = new Point((W - 110) / 2, 392),
            FlatStyle = FlatStyle.Flat,
            ForeColor = Color.White,
            BackColor = Color.FromArgb(52, 55, 70),
            Cursor = Cursors.Hand
        };
        _cancelButton.FlatAppearance.BorderColor = Color.FromArgb(255, 160, 40);
        _cancelButton.Click += (_, _) =>
        {
            _cancelButton.Enabled = false;
            _cancelButton.Text = "Cancelling...";
            _cts.Cancel();
        };
        Controls.Add(_cancelButton);

        _timer.Tick += (_, _) =>
        {
            _phase += 0.012f;                        // about 1.3 s per stroke
            if (_phase >= 1f) _phase -= 1f;
            _shownPercent += (_targetPercent - _shownPercent) * 0.15f;
            Invalidate();
        };
        _timer.Start();
    }

    /// <summary>Called on the UI thread (via Progress&lt;T&gt;).</summary>
    public void SetProgress(int percent, string file)
    {
        _targetPercent = Math.Clamp(percent, 0, 100);
        _currentFile = file;
    }

    // ---------- Rounded window ----------
    protected override void OnHandleCreated(EventArgs e)
    {
        base.OnHandleCreated(e);
        using var path = RoundedRect(new Rectangle(0, 0, Width, Height), 26);
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

    private static LinearGradientBrush Steel(RectangleF r, bool vertical = false) =>
        new(r, Color.FromArgb(138, 148, 168), Color.FromArgb(52, 58, 76),
            vertical ? LinearGradientMode.Vertical : LinearGradientMode.Horizontal);

    // ---------- Painting ----------
    protected override void OnPaint(PaintEventArgs e)
    {
        var g = e.Graphics;
        g.SmoothingMode = SmoothingMode.AntiAlias;
        g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;

        float cx = Width / 2f;

        // d = 0 when the press is fully raised, 1 when fully pressed
        float d = (1f - MathF.Cos(_phase * MathF.Tau)) / 2f;

        using (var borderPath = RoundedRect(new Rectangle(1, 1, Width - 3, Height - 3), 26))
        using (var borderPen = new Pen(Color.FromArgb(255, 160, 40), 2))
            g.DrawPath(borderPen, borderPath);

        DrawTitle(g, cx);
        DrawPress(g, cx, d);
        DrawReadout(g, cx);
    }

    private void DrawTitle(Graphics g, float cx)
    {
        string dots = new string('.', (int)(_phase * 4f) % 4);
        using var font = new Font("Segoe UI Semibold", 13f);
        using var white = new SolidBrush(Color.White);
        float fullWidth = g.MeasureString("Unzipping...", font).Width;
        g.DrawString("Unzipping" + dots, font, white, cx - fullWidth / 2f, 12);
    }

    private void DrawPress(Graphics g, float cx, float d)
    {
        // --- Base with hazard stripes ---
        var baseRect = new RectangleF(cx - 135, BaseTop, 270, 22);
        using (var b = Steel(baseRect, true)) g.FillRectangle(b, baseRect);
        using (var yellow = new SolidBrush(Color.FromArgb(255, 200, 0)))
            for (int i = 0; i < 16; i += 2)
                g.FillRectangle(yellow, baseRect.X + 8 + i * 16, BaseTop + 7, 16, 8);

        // --- Frame columns ---
        foreach (float x in new[] { cx - 112, cx + 94 })
        {
            var r = new RectangleF(x, 50, 18, BaseTop - 50);
            using var b = Steel(r);
            g.FillRectangle(b, r);
        }

        // --- Top beam ---
        var beam = new RectangleF(cx - 120, 50, 240, 30);
        using (var b = Steel(beam, true)) g.FillRectangle(b, beam);
        using (var outline = new Pen(Color.FromArgb(30, 34, 46), 1.5f))
            g.DrawRectangle(outline, beam.X, beam.Y, beam.Width, beam.Height);

        // --- Press travel ---
        float plateY = 130f + d * 90f;

        // --- Piston rod (chrome) ---
        var rod = new RectangleF(cx - 7, 118, 14, plateY - 118 + 2);
        using (var chrome = new LinearGradientBrush(rod, Color.Gray, Color.Silver, LinearGradientMode.Horizontal))
        {
            chrome.InterpolationColors = new ColorBlend
            {
                Colors = new[]
                {
                    Color.FromArgb(110, 115, 130),
                    Color.FromArgb(240, 244, 250),
                    Color.FromArgb(105, 110, 125)
                },
                Positions = new[] { 0f, 0.4f, 1f }
            };
            g.FillRectangle(chrome, rod);
        }

        // --- Cylinder body ---
        var cyl = new RectangleF(cx - 30, 80, 60, 44);
        using (var b = Steel(cyl)) g.FillRectangle(b, cyl);
        using (var band = new SolidBrush(Color.FromArgb(35, 40, 55)))
        {
            g.FillRectangle(band, cyl.X, 92, cyl.Width, 4);
            g.FillRectangle(band, cyl.X, 110, cyl.Width, 4);
        }

        // --- Crate being pressed ---
        float plateBottom = plateY + 16f;
        float crateH = Math.Clamp(BaseTop - plateBottom, CrateMin, CrateFull);
        float crateW = 56f + (CrateFull - crateH) * 0.9f;   // bulges as it squashes
        var crate = new RectangleF(cx - crateW / 2f, BaseTop - crateH, crateW, crateH);

        using (var crateBrush = new LinearGradientBrush(crate,
                   Color.FromArgb(222, 158, 78), Color.FromArgb(160, 100, 40), LinearGradientMode.Vertical))
            g.FillRectangle(crateBrush, crate);
        using (var crateEdge = new Pen(Color.FromArgb(80, 50, 20), 1.5f))
            g.DrawRectangle(crateEdge, crate.X, crate.Y, crate.Width, crate.Height);
        if (crateH > 22)
        {
            using var zipLine = new Pen(Color.FromArgb(70, 45, 20), 2f) { DashStyle = DashStyle.Dash };
            g.DrawLine(zipLine, cx, crate.Top + 4, cx, crate.Bottom - 4);
        }

        // --- Press plate ---
        var plate = new RectangleF(cx - 48, plateY, 96, 16);
        using (var plateBrush = new LinearGradientBrush(plate,
                   Color.FromArgb(255, 170, 50), Color.FromArgb(200, 90, 20), LinearGradientMode.Vertical))
            g.FillRectangle(plateBrush, plate);
        using (var plateEdge = new Pen(Color.FromArgb(120, 55, 10), 1.5f))
            g.DrawRectangle(plateEdge, plate.X, plate.Y, plate.Width, plate.Height);

        // --- Sparks at the bottom of the stroke ---
        float impact = Math.Clamp((d - 0.85f) / 0.15f, 0f, 1f);
        if (impact > 0f)
        {
            using var spark = new Pen(Color.FromArgb((int)(255 * impact), 255, 220, 80), 2f);
            for (int i = 0; i < 5; i++)
            {
                float len = impact * (8 + i * 5);
                float lift = impact * (i * 6);
                foreach (int side in new[] { -1, 1 })
                {
                    float x0 = cx + side * (crateW / 2f + 2);
                    float y0 = BaseTop - 6;
                    g.DrawLine(spark, x0, y0, x0 + side * len, y0 - lift);
                }
            }
        }

        // --- Pressure gauge ---
        float gx = cx + 78, gy = 65, gr = 11;
        using (var face = new SolidBrush(Color.FromArgb(235, 238, 245)))
            g.FillEllipse(face, gx - gr, gy - gr, gr * 2, gr * 2);
        using (var rim = new Pen(Color.FromArgb(30, 34, 46), 1.5f))
            g.DrawEllipse(rim, gx - gr, gy - gr, gr * 2, gr * 2);
        float needleAngle = (135f + d * 270f) * MathF.PI / 180f;
        using (var needle = new Pen(Color.FromArgb(220, 40, 40), 2f))
            g.DrawLine(needle, gx, gy, gx + MathF.Cos(needleAngle) * (gr - 2), gy + MathF.Sin(needleAngle) * (gr - 2));

        // --- Warning lamp (red when pressing) ---
        var lampColor = d > 0.85f ? Color.FromArgb(255, 60, 50) : Color.FromArgb(60, 200, 90);
        using (var lamp = new SolidBrush(lampColor))
            g.FillEllipse(lamp, cx - 85 - 7, 65 - 7, 14, 14);
    }

    private void DrawReadout(Graphics g, float cx)
    {
        // Percentage
        using (var pctFont = new Font("Segoe UI", 22f, FontStyle.Bold))
        using (var white = new SolidBrush(Color.White))
        {
            string pct = $"{(int)Math.Round(_shownPercent)}%";
            var size = g.MeasureString(pct, pctFont);
            g.DrawString(pct, pctFont, white, cx - size.Width / 2f, 284);
        }

        // Current file
        using (var fileFont = new Font("Segoe UI", 9f))
        using (var grey = new SolidBrush(Color.FromArgb(170, 175, 200)))
        using (var fmt = new StringFormat
        {
            Alignment = StringAlignment.Center,
            Trimming = StringTrimming.EllipsisPath,
            FormatFlags = StringFormatFlags.NoWrap
        })
            g.DrawString(_currentFile, fileFont, grey, new RectangleF(30, 336, Width - 60, 22), fmt);

        // Hydraulic fluid progress bar
        var barRect = new Rectangle(40, 366, Width - 80, 12);
        using (var trackPath = RoundedRect(barRect, 6))
        using (var trackBrush = new SolidBrush(Color.FromArgb(48, 52, 68)))
            g.FillPath(trackBrush, trackPath);

        int fillWidth = (int)(barRect.Width * (_shownPercent / 100f));
        if (fillWidth > 12)
        {
            var fillRect = new Rectangle(barRect.X, barRect.Y, fillWidth, barRect.Height);
            using var fillPath = RoundedRect(fillRect, 6);
            using var fluid = new LinearGradientBrush(fillRect,
                Color.FromArgb(255, 200, 60), Color.FromArgb(255, 90, 30), LinearGradientMode.Horizontal);
            g.FillPath(fluid, fillPath);
        }
    }

    protected override void OnFormClosed(FormClosedEventArgs e)
    {
        _timer.Stop();
        _timer.Dispose();
        base.OnFormClosed(e);
    }
}