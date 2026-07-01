using System;
using System.Collections.Generic;
using System.IO;
using System.Security.Cryptography;
using System.Text;

namespace PrivacyMonitor
{
    public static class PrivacyHelper
    {
        public static string HashString(string input)
        {
            using (SHA256 sha256 = SHA256.Create())
            {
                byte[] bytes = Encoding.UTF8.GetBytes(input);
                byte[] hash = sha256.ComputeHash(bytes);
                return Convert.ToBase64String(hash).Substring(0, 8);
            }
        }

        public static bool IsSensitivePath(string path)
        {
            string[] sensitiveKeywords = new[]
            {
                "password", "credit", "card", "bank", "personal",
                "social", "ssn", "passport", "driver", "license",
                "secret", "private", "confidential"
            };

            string lowerPath = path.ToLower();
            foreach (var keyword in sensitiveKeywords)
            {
                if (lowerPath.Contains(keyword))
                {
                    return true;
                }
            }
            return false;
        }

        public static string GetProcessDescription(string processName)
        {
            // Add known descriptions for common processes
            Dictionary<string, string> processDescriptions = new Dictionary<string, string>
            {
                { "chrome", "Google Chrome - May collect browsing history, cookies, and personal data" },
                { "firefox", "Firefox Browser - May collect browsing history and personal data" },
                { "edge", "Microsoft Edge - May collect browsing history and personal data" },
                { "outlook", "Microsoft Outlook - Accesses emails, contacts, and calendar data" },
                { "slack", "Slack - Accesses messages, files, and personal information" },
                { "discord", "Discord - Accesses messages, voice data, and personal information" },
                { "spotify", "Spotify - Accesses listening history and personal preferences" },
                { "onedrive", "OneDrive - Synchronizes personal files and documents" },
                { "dropbox", "Dropbox - Synchronizes personal files and documents" },
                { "googledrive", "Google Drive - Synchronizes personal files and documents" }
            };

            foreach (var desc in processDescriptions)
            {
                if (processName.ToLower().Contains(desc.Key))
                {
                    return desc.Value;
                }
            }

            return "Process may collect personal data";
        }
    }
}