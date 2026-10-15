using System.Diagnostics;
using System.Drawing.Drawing2D;

namespace ZipExtractor;

public class MainForm : Form
{
    private readonly Label _dropZone = new();
    private readonly TextBox _txtZip = new();
    private readonly TextBox _txtDest = new();
    private readonly Button _btnBrowseZip = new() { Text = "Browse..." };
    private readonly Button _btnBrowseDest = new() { Text = "Browse..." };
    private readonly Button _btnUnzip = new() { Text = "Unzip" };
    private readonly Button _btnExit = new() { Text = "Exit" };
    private readonly CheckBox _chkOpenFolder = new()
    {
        Text = "Open folder when done",
        Checked = true,
        AutoSize = true
    };
    private readonly Label _lblStatus = new() { Text = "Select or drop a zip file to begin." };

    private bool _busy;

    private static readonly Color DropIdle = Color.FromArgb(245, 246, 250);
    private static readonly Color DropActive = Color.FromArgb(255, 236, 205);

    public MainForm()
    {
        Text = "Zip Extractor";
        ClientSize = new Size(560, 335);
        FormBorderStyle = FormBorderStyle.FixedDialog;
        MaximizeBox = false;
        StartPosition = FormStartPosition.CenterScreen;
        Font = new Font("Segoe UI", 9.5f);

        // Drop zone
        _dropZone.SetBounds(20, 15, 520, 70);
        _dropZone.Text = "Drag and drop a .zip file here";
        _dropZone.TextAlign = ContentAlignment.MiddleCenter;
        _dropZone.ForeColor = Color.FromArgb(110, 115, 135);
        _dropZone.BackColor = DropIdle;
        _dropZone.UseMnemonic = false;
        _dropZone.Paint += (_, e) =>
        {
            using var pen = new Pen(Color.FromArgb(150, 155, 175), 2) { DashStyle = DashStyle.Dash };
            e.Graphics.DrawRectangle(pen, 1, 1, _dropZone.Width - 3, _dropZone.Height - 3);
        };

        var lblZip = new Label { Text = "Zip file:", Location = new Point(20, 100), AutoSize = true };
        _txtZip.SetBounds(20, 123, 420, 27);
        _btnBrowseZip.SetBounds(450, 122, 90, 29);

        var lblDest = new Label { Text = "Extract to:", Location = new Point(20, 160), AutoSize = true };
        _txtDest.SetBounds(20, 183, 420, 27);
        _btnBrowseDest.SetBounds(450, 182, 90, 29);

        _chkOpenFolder.Location = new Point(20, 222);

        _btnUnzip.SetBounds(20, 254, 120, 38);
        _btnExit.SetBounds(150, 254, 120, 38);
        _lblStatus.SetBounds(20, 302, 520, 22);

        Controls.AddRange(new Control[]
        {
            _dropZone, lblZip, _txtZip, _btnBrowseZip,
            lblDest, _txtDest, _btnBrowseDest,
            _chkOpenFolder, _btnUnzip, _btnExit, _lblStatus
        });

        // Drag-and-drop on the form, the drop zone and the zip box
        foreach (Control c in new Control[] { this, _dropZone, _txtZip })
        {
            c.AllowDrop = true;
            c.DragEnter += OnDragEnter;
            c.DragLeave += OnDragLeave;
            c.DragDrop += OnDragDrop;
        }

        _btnBrowseZip.Click += BtnBrowseZip_Click;
        _btnBrowseDest.Click += BtnBrowseDest_Click;
        _btnUnzip.Click += BtnUnzip_Click;
        _btnExit.Click += (_, _) => Close();
    }

    // ---------- Drag and drop ----------
    private static string? GetDroppedZip(DragEventArgs e)
    {
        if (e.Data?.GetData(DataFormats.FileDrop) is string[] files)
        {
            return files.FirstOrDefault(f =>
                f.EndsWith(".zip", StringComparison.OrdinalIgnoreCase) && File.Exists(f));
        }
        return null;
    }

    private void OnDragEnter(object? sender, DragEventArgs e)
    {
        bool ok = !_busy && GetDroppedZip(e) != null;
        e.Effect = ok ? DragDropEffects.Copy : DragDropEffects.None;
        _dropZone.BackColor = ok ? DropActive : DropIdle;
    }

    private void OnDragLeave(object? sender, EventArgs e) => _dropZone.BackColor = DropIdle;

    private void OnDragDrop(object? sender, DragEventArgs e)
    {
        _dropZone.BackColor = DropIdle;
        if (_busy) return;

        var zip = GetDroppedZip(e);
        if (zip == null)
        {
            MessageBox.Show(this, "Please drop a single .zip file.", "Not a zip file",
                MessageBoxButtons.OK, MessageBoxIcon.Warning);
            return;
        }
        SetZip(zip);
    }

    // ---------- Browsing ----------
    private void SetZip(string path)
    {
        _txtZip.Text = path;
        _dropZone.Text = "Loaded: " + Path.GetFileName(path);

        // Suggest a destination folder next to the zip
        if (string.IsNullOrWhiteSpace(_txtDest.Text))
        {
            _txtDest.Text = Path.Combine(
                Path.GetDirectoryName(path)!,
                Path.GetFileNameWithoutExtension(path));
        }
        _lblStatus.Text = "Ready.";
    }

    private void BtnBrowseZip_Click(object? sender, EventArgs e)
    {
        using var dlg = new OpenFileDialog
        {
            Title = "Choose a zip file",
            Filter = "Zip files (*.zip)|*.zip"
        };
        if (dlg.ShowDialog(this) == DialogResult.OK)
            SetZip(dlg.FileName);
    }

    private void BtnBrowseDest_Click(object? sender, EventArgs e)
    {
        using var dlg = new FolderBrowserDialog { Description = "Choose where to extract the files" };
        if (dlg.ShowDialog(this) == DialogResult.OK)
            _txtDest.Text = dlg.SelectedPath;
    }

    // ---------- Unzip ----------
    private async void BtnUnzip_Click(object? sender, EventArgs e)
    {
        string zipPath = _txtZip.Text.Trim();
        string destination = _txtDest.Text.Trim();

        if (!File.Exists(zipPath))
        {
            MessageBox.Show(this, "Please choose a valid zip file.", "Missing file",
                MessageBoxButtons.OK, MessageBoxIcon.Warning);
            return;
        }
        if (string.IsNullOrWhiteSpace(destination))
        {
            MessageBox.Show(this, "Please choose a destination folder.", "Missing folder",
                MessageBoxButtons.OK, MessageBoxIcon.Warning);
            return;
        }

        destination = Path.GetFullPath(destination);

        var answer = MessageBox.Show(this,
            $"Do you want to unzip:\n\n{zipPath}\n\nto:\n\n{destination}?",
            "Confirm unzip",
            MessageBoxButtons.YesNo,
            MessageBoxIcon.Question);

        if (answer != DialogResult.Yes)
        {
            _lblStatus.Text = "Unzip cancelled.";
            return;
        }

        using var cts = new CancellationTokenSource();
        using var loading = new LoadingForm(cts);
        var progress = new Progress<(int Percent, string File)>(p => loading.SetProgress(p.Percent, p.File));

        SetBusy(true);
        _lblStatus.Text = "Unzipping...";
        loading.Show(this);

        try
        {
            await Task.Run(() => ZipService.Extract(zipPath, destination, progress, cts.Token));

            loading.Close();
            _lblStatus.Text = "Done!";

            if (_chkOpenFolder.Checked)
                OpenFolder(destination);
            else
                MessageBox.Show(this, "The file was unzipped successfully.", "Success",
                    MessageBoxButtons.OK, MessageBoxIcon.Information);
        }
        catch (OperationCanceledException)
        {
            loading.Close();
            _lblStatus.Text = "Unzip was cancelled.";
        }
        catch (Exception ex)
        {
            loading.Close();
            _lblStatus.Text = "Something went wrong.";
            MessageBox.Show(this, ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }
        finally
        {
            SetBusy(false);
        }
    }

    private void OpenFolder(string path)
    {
        try
        {
            Process.Start(new ProcessStartInfo
            {
                FileName = path,
                UseShellExecute = true   // opens in File Explorer
            });
        }
        catch (Exception ex)
        {
            MessageBox.Show(this, "Unzipped, but the folder could not be opened:\n" + ex.Message,
                "Open folder", MessageBoxButtons.OK, MessageBoxIcon.Warning);
        }
    }

    private void SetBusy(bool busy)
    {
        _busy = busy;
        _btnUnzip.Enabled = !busy;
        _btnBrowseZip.Enabled = !busy;
        _btnBrowseDest.Enabled = !busy;
        _btnExit.Enabled = !busy;
        _chkOpenFolder.Enabled = !busy;
    }
}