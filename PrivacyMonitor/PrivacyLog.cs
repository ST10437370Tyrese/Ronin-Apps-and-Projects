using System;

namespace PrivacyMonitor
{
    public class PrivacyLog
    {
        public DateTime Timestamp { get; set; }
        public string ProcessName { get; set; }
        public string ResourceName { get; set; }
        public string AccessType { get; set; }
        public string Details { get; set; }
        public string RiskLevel { get; set; }

        public PrivacyLog()
        {
            Timestamp = DateTime.Now;
        }

        public override string ToString()
        {
            return $"{Timestamp:HH:mm:ss} | {ProcessName} | {AccessType} | {ResourceName}";
        }
    }
}