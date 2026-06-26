using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;

namespace EasyEggs
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void btnCalculate_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                // Validate and parse input values
                if (!ValidateInputs())
                    return;

                int eggsBought = int.Parse(txtEggsBought.Text);
                int eggsBroken = int.Parse(txtEggsBroken.Text);
                double costPricePerEgg = double.Parse(txtCostPricePerEgg.Text);
                int packetsSold = int.Parse(txtPacketsSold.Text);

                // Validate business logic
                if (eggsBroken > eggsBought)
                {
                    MessageBox.Show("Number of broken eggs cannot exceed total eggs bought!",
                                    "Validation Error",
                                    MessageBoxButton.OK,
                                    MessageBoxImage.Warning);
                    return;
                }

                int eggsInSoldPackets = packetsSold * 6;
                int availableEggs = eggsBought - eggsBroken;

                if (eggsInSoldPackets > availableEggs)
                {
                    MessageBox.Show($"You cannot sell {packetsSold} packets because you only have {availableEggs} good eggs!\n" +
                                    $"Maximum packets possible: {availableEggs / 6}",
                                    "Validation Error",
                                    MessageBoxButton.OK,
                                    MessageBoxImage.Warning);
                    return;
                }

                // Create instance of the back-end class
                EggBusiness business = new EggBusiness(eggsBought, eggsBroken, costPricePerEgg, packetsSold);

                // Calculate and display results
                double breakagePercentage = business.CalculateBreakagePercentage();
                int personalUseEggs = business.CalculatePersonalUseEggs();
                double totalCost = business.CalculateTotalCost();
                double totalRevenue = business.CalculateTotalRevenue();
                double profit = business.CalculateProfit();
                int packetsPossible = business.CalculateTotalPacketsPossible();
                int looseEggs = business.CalculateLooseEggsRemaining();

                // Display results with formatting
                lblBreakagePercentage.Text = breakagePercentage.ToString("0.00") + "%";
                lblPersonalUseEggs.Text = personalUseEggs.ToString();
                lblTotalCost.Text = totalCost.ToString("C2"); // Currency format
                lblTotalRevenue.Text = totalRevenue.ToString("C2");
                lblProfit.Text = profit.ToString("C2");
                lblPacketsPossible.Text = packetsPossible.ToString();
                lblLooseEggs.Text = looseEggs.ToString();

                // Update status
                lblStatus.Text = $"Calculation completed at {DateTime.Now:HH:mm:ss}";

                // Optional: Show profit warning if negative
                if (profit < 0)
                {
                    lblProfit.Foreground = System.Windows.Media.Brushes.Red;
                    MessageBox.Show("Warning: You are operating at a loss!",
                                    "Profit Warning",
                                    MessageBoxButton.OK,
                                    MessageBoxImage.Warning);
                }
                else
                {
                    lblProfit.Foreground = System.Windows.Media.Brushes.Green;
                }
            }
            catch (FormatException)
            {
                MessageBox.Show("Please enter valid numeric values in all fields!",
                                "Input Error",
                                MessageBoxButton.OK,
                                MessageBoxImage.Error);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"An unexpected error occurred: {ex.Message}",
                                "Error",
                                MessageBoxButton.OK,
                                MessageBoxImage.Error);
            }
        }

        private bool ValidateInputs()
        {
            // Check for empty fields
            if (string.IsNullOrWhiteSpace(txtEggsBought.Text) ||
                string.IsNullOrWhiteSpace(txtEggsBroken.Text) ||
                string.IsNullOrWhiteSpace(txtCostPricePerEgg.Text) ||
                string.IsNullOrWhiteSpace(txtPacketsSold.Text))
            {
                MessageBox.Show("Please fill in all input fields!",
                                "Missing Information",
                                MessageBoxButton.OK,
                                MessageBoxImage.Warning);
                return false;
            }

            // Validate non-negative numbers
            if (int.Parse(txtEggsBought.Text) < 0 ||
                int.Parse(txtEggsBroken.Text) < 0 ||
                double.Parse(txtCostPricePerEgg.Text) < 0 ||
                int.Parse(txtPacketsSold.Text) < 0)
            {
                MessageBox.Show("All values must be non-negative!",
                                "Invalid Input",
                                MessageBoxButton.OK,
                                MessageBoxImage.Warning);
                return false;
            }

            return true;
        }

        private void btnClear_Click(object sender, RoutedEventArgs e)
        {
            // Clear all input fields
            txtEggsBought.Clear();
            txtEggsBroken.Clear();
            txtCostPricePerEgg.Clear();
            txtPacketsSold.Clear();

            // Clear all result labels
            lblBreakagePercentage.Text = "0%";
            lblPersonalUseEggs.Text = "0";
            lblTotalCost.Text = "$0.00";
            lblTotalRevenue.Text = "$0.00";
            lblProfit.Text = "$0.00";
            lblPacketsPossible.Text = "0";
            lblLooseEggs.Text = "0";

            // Reset status
            lblStatus.Text = "Cleared - Ready for new input";

            // Set default values (optional)
            txtEggsBought.Text = "130";
            txtEggsBroken.Text = "1";
            txtCostPricePerEgg.Text = "1.10";
            txtPacketsSold.Text = "21";

            // Set focus to first field
            txtEggsBought.Focus();
        }

        private void btnExit_Click(object sender, RoutedEventArgs e)
        {
            // Confirm exit
            MessageBoxResult result = MessageBox.Show("Are you sure you want to exit?",
                                                      "Exit Confirmation",
                                                      MessageBoxButton.YesNo,
                                                      MessageBoxImage.Question);

            if (result == MessageBoxResult.Yes)
            {
                Application.Current.Shutdown();
            }
        }
    }
}