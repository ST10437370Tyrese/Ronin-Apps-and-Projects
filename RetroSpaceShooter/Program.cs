using System;
using System.Windows.Forms;

namespace RetroSpaceShooter
{
    internal static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            // Generate sounds on startup if needed
            try
            {
                SoundGenerator.GenerateAllSounds();
            }
            catch { }

            Application.Run(new Form1());
        }
    }
}