using System;
using System.Windows;

namespace TempConvertor
{
    public partial class MainWindow : Window
    {
        private Convertor convertor;

        public MainWindow()
        {
            InitializeComponent();
            convertor = new Convertor();
        }

        private void btnConvert_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                // Input validation: Check if the input is empty or whitespace
                if (string.IsNullOrWhiteSpace(txtCelsius.Text))
                {
                    MessageBox.Show("Please enter a temperature in Celsius.",
                                    "Input Error",
                                    MessageBoxButton.OK,
                                    MessageBoxImage.Warning);
                    return;
                }

                // Center the input text for better user experience
                if (!double.TryParse(txtCelsius.Text, out double celsius))
                {
                    MessageBox.Show("Please enter a valid numeric value.",
                                    "Invalid Input",
                                    MessageBoxButton.OK,
                                    MessageBoxImage.Error);
                    return;
                }

                // Calculate Fahrenheit
                double fahrenheit = convertor.CalculateFahrenheit(celsius);

                // Result formatting: Display the result with 2 decimal places
                lblResult.Text = fahrenheit.ToString();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"An error occurred: {ex.Message}",
                                "Error",
                                MessageBoxButton.OK,
                                MessageBoxImage.Error);
            }
        }

        private void btnClear_Click(object sender, RoutedEventArgs e)
        {
            // Input validation: Clear the input and result fields
            txtCelsius.Clear();
            lblResult.Text = string.Empty;

            // Set focus back to input field
            txtCelsius.Focus();
        }
    }
}