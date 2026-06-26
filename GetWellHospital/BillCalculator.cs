using System;

namespace GetWellHospital
{
    public class BillCalculator
    {
        // Constants for room rates per day
        private const double PrivateRoomRate = 2500.00;
        private const double ThreeBedRoomRate = 1200.00;
        private const double SixBedRoomRate = 800.00;

        // Properties
        public string PatientName { get; set; }
        public bool IsInPatient { get; set; }
        public double MedicalCharges { get; set; }
        public double HospitalServices { get; set; } 
        public int NumberOfDays { get; set; }
        public RoomType SelectedRoomType { get; set; }

        public enum RoomType
        {
            Private,
            ThreeBed,
            SixBed
        }

        // Calculate total bill based on inputs
        public double CalculateTotalBill() 
        {
            double total = MedicalCharges + HospitalServices;

            // Add room charges only for In-Patients
            if (IsInPatient && NumberOfDays > 0)
            {
                double dailyRate = GetDailyRoomRate();
                total += dailyRate * NumberOfDays;
            }

            return Math.Round(total, 2);
        }

        private double GetDailyRoomRate()
        {
            switch (SelectedRoomType)
            {
                case RoomType.Private:
                    return PrivateRoomRate;
                case RoomType.ThreeBed:
                    return ThreeBedRoomRate;
                case RoomType.SixBed:
                    return SixBedRoomRate;
                default:
                    return 0;
            }
        }

        // Validate inputs and return error message if invalid
        public bool ValidateInputs(out string errorMessage)
        {
            errorMessage = string.Empty;

            if (string.IsNullOrWhiteSpace(PatientName))
            {
                errorMessage = "Please enter patient name.";
                return false;
            }

            if (MedicalCharges < 0)
            {
                errorMessage = "Medical charges cannot be negative.";
                return false;
            }

            if (HospitalServices < 0)
            {
                errorMessage = "Hospital services cannot be negative.";
                return false;
            }

            if (IsInPatient)
            {
                if (NumberOfDays <= 0)
                {
                    errorMessage = "Please enter valid number of days for In-Patient.";
                    return false;
                }
            }

            return true;
        }

        // Clear all data
        public void Clear()
        {
            PatientName = string.Empty;
            IsInPatient = false;
            MedicalCharges = 0;
            HospitalServices = 0;
            NumberOfDays = 0;
            SelectedRoomType = RoomType.ThreeBed;
        }
    }
}