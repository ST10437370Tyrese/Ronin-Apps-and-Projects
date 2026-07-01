using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;

namespace PrivacyMonitor
{
    public partial class Form1 : Form
    {
        private PrivacyMonitorService monitorService;
        private DataGridView dataGridView;
        private Button startButton;
        private Button stopButton;
        private Button refreshButton;
        private ComboBox filterComboBox;
        private Label statusLabel;
        private Timer updateTimer;

        public Form1()
        {
            InitializeComponent();
            InitializeCustomComponents();
            monitorService = new PrivacyMonitorService();
            updateTimer = new Timer();
            updateTimer.Interval = 5000; // Update every 5 seconds
            updateTimer.Tick += UpdateTimer_Tick;
        }

        private void InitializeCustomComponents()
        {
            this.Text = "Privacy Monitor";
            this.Size = new Size(1000, 600);
            this.StartPosition = FormStartPosition.CenterScreen;

            // Create DataGridView
            dataGridView = new DataGridView
            {
                Location = new Point(12, 50),
                Size = new Size(960, 450),
                AllowUserToAddRows = false,
                AllowUserToDeleteRows = false,
                ReadOnly = true,
                AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill,
                BackgroundColor = Color.White
            };

            // Add columns
            dataGridView.Columns.Add("Timestamp", "Time");
            dataGridView.Columns.Add("Process", "Process");
            dataGridView.Columns.Add("Resource", "Resource");
            dataGridView.Columns.Add("AccessType", "Access Type");
            dataGridView.Columns.Add("Details", "Details");
            dataGridView.Columns.Add("RiskLevel", "Risk Level");

            // Buttons
            startButton = new Button
            {
                Text = "Start Monitoring",
                Location = new Point(12, 12),
                Size = new Size(120, 30)
            };
            startButton.Click += StartButton_Click;

            stopButton = new Button
            {
                Text = "Stop Monitoring",
                Location = new Point(138, 12),
                Size = new Size(120, 30),
                Enabled = false
            };
            stopButton.Click += StopButton_Click;

            refreshButton = new Button
            {
                Text = "Refresh",
                Location = new Point(264, 12),
                Size = new Size(100, 30)
            };
            refreshButton.Click += RefreshButton_Click;

            // Filter ComboBox
            filterComboBox = new ComboBox
            {
                Location = new Point(380, 14),
                Size = new Size(150, 25),
                DropDownStyle = ComboBoxStyle.DropDownList
            };
            filterComboBox.Items.AddRange(new object[] {
                "All",
                "File Access",
                "Registry Access",
                "Network Access",
                "High Risk",
                "Medium Risk",
                "Low Risk"
            });
            filterComboBox.SelectedIndex = 0;
            filterComboBox.SelectedIndexChanged += FilterComboBox_SelectedIndexChanged;

            // Status Label
            statusLabel = new Label
            {
                Text = "Status: Stopped",
                Location = new Point(550, 16),
                Size = new Size(400, 25),
                Font = new Font("Arial", 10, FontStyle.Bold),
                ForeColor = Color.Red
            };

            // Add controls to form
            this.Controls.AddRange(new Control[] {
                dataGridView,
                startButton,
                stopButton,
                refreshButton,
                filterComboBox,
                statusLabel
            });
        }

        private void StartButton_Click(object sender, EventArgs e)
        {
            monitorService.StartMonitoring();
            startButton.Enabled = false;
            stopButton.Enabled = true;
            statusLabel.Text = "Status: Monitoring Active";
            statusLabel.ForeColor = Color.Green;
            updateTimer.Start();
            RefreshData();
        }

        private void StopButton_Click(object sender, EventArgs e)
        {
            monitorService.StopMonitoring();
            startButton.Enabled = true;
            stopButton.Enabled = false;
            statusLabel.Text = "Status: Stopped";
            statusLabel.ForeColor = Color.Red;
            updateTimer.Stop();
        }

        private void RefreshButton_Click(object sender, EventArgs e)
        {
            RefreshData();
        }

        private void FilterComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            RefreshData();
        }

        private void UpdateTimer_Tick(object sender, EventArgs e)
        {
            RefreshData();
        }

        private void RefreshData()
        {
            var logs = monitorService.GetPrivacyLogs();
            string filter = filterComboBox.SelectedItem?.ToString() ?? "All";

            IEnumerable<PrivacyLog> filteredLogs;

            if (filter == "File Access")
                filteredLogs = logs.Where(l => l.AccessType == "File");
            else if (filter == "Registry Access")
                filteredLogs = logs.Where(l => l.AccessType == "Registry");
            else if (filter == "Network Access")
                filteredLogs = logs.Where(l => l.AccessType == "Network");
            else if (filter == "High Risk")
                filteredLogs = logs.Where(l => l.RiskLevel == "High");
            else if (filter == "Medium Risk")
                filteredLogs = logs.Where(l => l.RiskLevel == "Medium");
            else if (filter == "Low Risk")
                filteredLogs = logs.Where(l => l.RiskLevel == "Low");
            else
                filteredLogs = logs;

            dataGridView.Rows.Clear();

            foreach (var log in filteredLogs)
            {
                var rowIndex = dataGridView.Rows.Add(
                    log.Timestamp.ToString("HH:mm:ss"),
                    log.ProcessName,
                    log.ResourceName,
                    log.AccessType,
                    log.Details,
                    log.RiskLevel
                );

                var row = dataGridView.Rows[rowIndex];
                switch (log.RiskLevel)
                {
                    case "High":
                        row.DefaultCellStyle.BackColor = Color.LightPink;
                        break;
                    case "Medium":
                        row.DefaultCellStyle.BackColor = Color.LightYellow;
                        break;
                    case "Low":
                        row.DefaultCellStyle.BackColor = Color.LightGreen;
                        break;
                }
            }

            statusLabel.Text = $"Status: Monitoring Active - {logs.Count} events detected";
        }

        protected override void OnFormClosing(FormClosingEventArgs e)
        {
            monitorService.StopMonitoring();
            updateTimer.Stop();
            base.OnFormClosing(e);
        }
    }
}