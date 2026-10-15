namespace ZipExtractor;

public class MainForm : Form
{
    private readonly TextBox _txtZip = new();
    private readonly TextBox _txtDest = new();
    private readonly Button _btnBrowseZip = new() { Text = "Browse..." };
    private readonly Button _btnBrowseDest = new() { Text = "Browse..." };
    private readonly Button _btnUnzip = new() { Text = "Unzip" };
    private readonly Button _btnExit = new() { Text = "Exit" };
    private readonly Label _lblStatus = new() { Text = "Select a zip file to begin." };

    public MainForm()
    {
        Text = "Zip Extractor";
        ClientSize = new Size(560, 230);
        FormBorderStyle = FormBorderStyle.FixedDialog;
        MaximizeBox = false;
        StartPosition = FormStartPosition.CenterScreen;
        Font = new Font("Segoe UI", 9.5f);

        var lblZip = new Label { Text = "Zip file:", Location = new Point(20, 22), AutoSize = true };
        _txtZip.SetBounds(20, 45, 420, 27);
        _btnBrowseZip.SetBounds(450, 44, 90, 29);

        var lblDest = new Label { Text = "Extract to:", Location = new Point(20, 82), AutoSize = true };
        _txtDest.SetBounds(20, 105, 420, 27);
        _btnBrowseDest.SetBounds(450, 104, 90, 29);

        _btnUnzip.SetBounds(20, 150, 120, 38);
        _btnExit.SetBounds(150, 150, 120, 38);
        _lblStatus.SetBounds(20, 198, 520, 22);

        Controls.AddRange(new Control[]
        {
            lblZip, _txtZip, _btnBrowseZip,
            lblDest, _txtDest, _btnBrowseDest,
            _btnUnzip, _btnExit, _lblStatus
        });

        _btnBrowseZip.Click += BtnBrowseZip_Click;
        _btnBrowseDest.Click += BtnBrowseDest_Click;
        _btnUnzip.Click += BtnUnzip_Click;
        _btnExit.Click += (_, _) => Close();
    }

    private void BtnBrowseZip_Click(object? sender, EventArgs e)
    {
        using var dlg = new OpenFileDialog
        {
            Title = "Choose a zip file",
            Filter = "Zip files (*.zip)|*.zip"
        };
        if (dlg.ShowDialog(this) != DialogResult.OK) return;

        _txtZip.Text = dlg.FileName;

        // Suggest a destination folder next to the zip
        if (string.IsNullOrWhiteSpace(_txtDest.Text))
        {
            _txtDest.Text = Path.Combine(
                Path.GetDirectoryName(dlg.FileName)!,
                Path.GetFileNameWithoutExtension(dlg.FileName));
        }
        _lblStatus.Text = "Ready.";
    }

    private void BtnBrowseDest_Click(object? sender, EventArgs e)
    {
        using var dlg = new FolderBrowserDialog { Description = "Choose where to extract the files" };
        if (dlg.ShowDialog(this) == DialogResult.OK)
            _txtDest.Text = dlg.SelectedPath;
    }

    private async void BtnUnzip_Click(object? sender, EventArgs e)
    {
        string zipPath = _txtZip.Text.Trim();
        string destination = _txtDest.Text.Trim();

        // 1. Validate input
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

        // 2. Ask for confirmation
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

        // 3. Unzip with the loading screen
        using var cts = new CancellationTokenSource();
        using var loading = new LoadingForm(cts);
        var progress = new Progress<(int Percent, string File)>(p => loading.SetProgress(p.Percent, p.File));

        SetButtonsEnabled(false);
        _lblStatus.Text = "Unzipping...";
        loading.Show(this);

        try
        {
            await Task.Run(() => ZipService.Extract(zipPath, destination, progress, cts.Token));

            loading.Close();
            _lblStatus.Text = "Done!";
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
            SetButtonsEnabled(true);
        }
    }

    private void SetButtonsEnabled(bool enabled)
    {
        _btnUnzip.Enabled = enabled;
        _btnBrowseZip.Enabled = enabled;
        _btnBrowseDest.Enabled = enabled;
        _btnExit.Enabled = enabled;
    }
}