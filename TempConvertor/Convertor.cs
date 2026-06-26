using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows; 

namespace TempConvertor
{
    public class Convertor
    {
        /// <summary>
        /// Calculates Fahrenheit from Celsius using the formula: C/5 = (F-32)/9
        /// Which simplifies to: F = (C * 9/5) + 32
        /// </summary>
        /// <param name="celsius">Temperature in Celsius</param>
        /// <returns>Temperature in Fahrenheit</returns>
        public double CalculateFahrenheit(double celsius)
        {
            // Using the formula: C/5 = (F-32)/9
            // Cross multiply: 9C = 5(F-32)
            // 9C = 5F - 160
            // 5F = 9C + 160
            // F = (9C + 160) / 5
            // F = (9 * C + 160) / 5
            // Simplified to: F = (C * 9/5) + 32

            double fahrenheit = (celsius * 9 / 5) + 32;
            return Math.Round(fahrenheit, 2); // Round to 2 decimal places
        }
    }
}