using System;
using System.Windows;

namespace GetWellHospital
{
    public partial class MainWindow : Window
    {
        private BillCalculator _calculator;

        public MainWindow()
        {
            InitializeComponent();
            _calculator = new BillCalculator();
        }

        private void RbPatientType_Checked(object sender, RoutedEventArgs e)
        {
            // Show/hide In-Patient section based on selection
            if (rbInPatient.IsChecked == true)
            {
                borderInPatient.Visibility = Visibility.Visible;
                _calculator.IsInPatient = true;
            }
            else
            {
                borderInPatient.Visibility = Visibility.Collapsed;
                _calculator.IsInPatient = false;
            }
        }

        private void BtnCalculate_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                // Get values from UI
                _calculator.PatientName = txtPatientName.Text;

                // Parse medical charges
                if (!double.TryParse(txtMedicalCharges.Text, out double medicalCharges))
                {
                    MessageBox.Show("Please enter valid medical charges.", "Input Error",
                                    MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }
                _calculator.MedicalCharges = medicalCharges;

                // Parse hospital services
                if (!double.TryParse(txtHospitalServices.Text, out double hospitalServices))
                {
                    MessageBox.Show("Please enter valid hospital services amount.", "Input Error",
                                    MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }
                _calculator.HospitalServices = hospitalServices;

                // Parse in-patient data if applicable
                if (rbInPatient.IsChecked == true)
                {
                    if (!int.TryParse(txtNumberOfDays.Text, out int numberOfDays))
                    {
                        MessageBox.Show("Please enter valid number of days.", "Input Error",
                                        MessageBoxButton.OK, MessageBoxImage.Warning);
                        return;
                    }
                    _calculator.NumberOfDays = numberOfDays;

                    if (rbPrivateRoom.IsChecked == true)
                        _calculator.SelectedRoomType = BillCalculator.RoomType.Private;
                    else if (rbThreeBedRoom.IsChecked == true)
                        _calculator.SelectedRoomType = BillCalculator.RoomType.ThreeBed;
                    else if (rbSixBedRoom.IsChecked == true)
                        _calculator.SelectedRoomType = BillCalculator.RoomType.SixBed;
                }

                // Validate all inputs
                if (_calculator.ValidateInputs(out string errorMessage))
                {
                    double totalBill = _calculator.CalculateTotalBill();
                    txtResult.Text = $"R {totalBill:N2}";
                }
                else
                {
                    MessageBox.Show(errorMessage, "Validation Error",
                                    MessageBoxButton.OK, MessageBoxImage.Warning);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"An error occurred: {ex.Message}", "Error",
                                MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void BtnClear_Click(object sender, RoutedEventArgs e)
        {
            // Clear UI elements
            txtPatientName.Clear();
            txtMedicalCharges.Clear();
            txtHospitalServices.Clear();
            txtNumberOfDays.Clear();
            txtResult.Text = string.Empty;

            // Reset radio buttons
            rbOutPatient.IsChecked = true;
            rbThreeBedRoom.IsChecked = true;

            // Clear backend calculator
            _calculator.Clear();

            // Hide in-patient section
            borderInPatient.Visibility = Visibility.Collapsed;
        }
    }
}