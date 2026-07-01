using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Management;
using System.Net.NetworkInformation;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Win32;

namespace PrivacyMonitor
{
    public class PrivacyMonitorService
    {
        private List<PrivacyLog> privacyLogs;
        private bool isMonitoring;
        private Thread monitoringThread;
        private HashSet<string> trackedProcesses;
        private Dictionary<string, DateTime> processAccessCache;

        // List of known privacy-sensitive paths
        private readonly string[] sensitivePaths = new[]
        {
            Environment.GetFolderPath(Environment.SpecialFolder.Personal),
            Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments),
            Environment.GetFolderPath(Environment.SpecialFolder.MyPictures),
            Environment.GetFolderPath(Environment.SpecialFolder.MyMusic),
            Environment.GetFolderPath(Environment.SpecialFolder.MyVideos),
            Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData),
            Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Downloads"),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Desktop"),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Contacts"),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Favorites"),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Links"),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Searches"),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Videos"),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Music"),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "Pictures")
        };

        // Known privacy-sensitive registry keys
        private readonly string[] sensitiveRegistryKeys = new[]
        {
            @"HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer",
            @"HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Run",
            @"HKEY_CURRENT_USER\Software\Microsoft\Internet Explorer",
            @"HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Run",
            @"HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Internet Settings"
        };

        public PrivacyMonitorService()
        {
            privacyLogs = new List<PrivacyLog>();
            trackedProcesses = new HashSet<string>();
            processAccessCache = new Dictionary<string, DateTime>();
            isMonitoring = false;
        }

        public void StartMonitoring()
        {
            if (isMonitoring) return;

            isMonitoring = true;
            privacyLogs.Clear();
            trackedProcesses.Clear();
            processAccessCache.Clear();

            monitoringThread = new Thread(MonitorThread)
            {
                IsBackground = true,
                Name = "PrivacyMonitorThread"
            };
            monitoringThread.Start();
        }

        public void StopMonitoring()
        {
            isMonitoring = false;
            if (monitoringThread != null && monitoringThread.IsAlive)
            {
                monitoringThread.Join(3000);
            }
        }

        private void MonitorThread()
        {
            while (isMonitoring)
            {
                try
                {
                    // Monitor file system access
                    MonitorFileAccess();

                    // Monitor registry access
                    MonitorRegistryAccess();

                    // Monitor network connections
                    MonitorNetworkConnections();

                    // Monitor running processes
                    MonitorProcesses();

                    // Wait before next scan
                    Thread.Sleep(2000);
                }
                catch (Exception ex)
                {
                    Debug.WriteLine($"Monitoring error: {ex.Message}");
                }
            }
        }

        private void MonitorFileAccess()
        {
            try
            {
                // Get all running processes
                var processes = Process.GetProcesses();

                foreach (var process in processes)
                {
                    try
                    {
                        if (process.Id == Process.GetCurrentProcess().Id) continue;

                        // Check if process is accessing sensitive files
                        foreach (var sensitivePath in sensitivePaths)
                        {
                            if (Directory.Exists(sensitivePath))
                            {
                                CheckFileAccess(process, sensitivePath);
                            }
                        }

                        // Check for file handles
                        CheckProcessHandles(process);
                    }
                    catch
                    {
                        // Skip inaccessible processes
                    }
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"File monitoring error: {ex.Message}");
            }
        }

        private void CheckFileAccess(Process process, string directoryPath)
        {
            try
            {
                var files = Directory.GetFiles(directoryPath, "*.*", SearchOption.AllDirectories);
                var recentFiles = Directory.GetFiles(directoryPath, "*.*", SearchOption.TopDirectoryOnly);

                foreach (var file in recentFiles)
                {
                    try
                    {
                        var lastAccess = File.GetLastAccessTime(file);
                        var lastWrite = File.GetLastWriteTime(file);
                        var now = DateTime.Now;

                        // Check if file was accessed in the last 5 seconds
                        if ((now - lastAccess).TotalSeconds < 5 || (now - lastWrite).TotalSeconds < 5)
                        {
                            string processKey = $"{process.ProcessName}_{file}";
                            if (!processAccessCache.ContainsKey(processKey) ||
                                (now - processAccessCache[processKey]).TotalSeconds > 10)
                            {
                                AddPrivacyLog(
                                    process.ProcessName,
                                    Path.GetFileName(file),
                                    "File",
                                    $"File accessed: {file}",
                                    DetermineFileRisk(file)
                                );
                                processAccessCache[processKey] = now;
                            }
                        }
                    }
                    catch
                    {
                        // Skip inaccessible files
                    }
                }
            }
            catch
            {
                // Skip inaccessible directories
            }
        }

        private void CheckProcessHandles(Process process)
        {
            try
            {
                // Use ManagementObjectSearcher to check open files
                string query = $"SELECT * FROM Win32_Process WHERE ProcessId = {process.Id}";
                using (var searcher = new ManagementObjectSearcher(query))
                {
                    foreach (ManagementObject obj in searcher.Get())
                    {
                        using (obj)
                        {
                            // Check for file handles through performance counters
                            try
                            {
                                string processName = process.ProcessName;
                                var handles = Process.GetProcessById(process.Id).Handle;
                                // Check if any sensitive path is being accessed
                                foreach (var sensitivePath in sensitivePaths)
                                {
                                    if (sensitivePath.Length > 5) // Basic check
                                    {
                                        // Log potential file access
                                        string logKey = $"handle_{processName}_{sensitivePath}";
                                        if (!processAccessCache.ContainsKey(logKey) ||
                                            (DateTime.Now - processAccessCache[logKey]).TotalSeconds > 30)
                                        {
                                            AddPrivacyLog(
                                                processName,
                                                sensitivePath,
                                                "File",
                                                $"Process may be accessing: {sensitivePath}",
                                                "Medium"
                                            );
                                            processAccessCache[logKey] = DateTime.Now;
                                        }
                                    }
                                }
                            }
                            catch
                            {
                                // Skip if can't get handles
                            }
                        }
                    }
                }
            }
            catch
            {
                // Skip if can't query process info
            }
        }

        private void MonitorRegistryAccess()
        {
            try
            {
                foreach (var regKey in sensitiveRegistryKeys)
                {
                    try
                    {
                        // Check for recent registry access
                        string[] keyParts = regKey.Split('\\');
                        if (keyParts.Length > 1)
                        {
                            string rootKey = keyParts[0];
                            string subKey = string.Join("\\", keyParts, 1, keyParts.Length - 1);

                            // Simulate registry access detection
                            // In a real implementation, you would use Windows API hooks
                            var processes = Process.GetProcesses();
                            foreach (var process in processes)
                            {
                                try
                                {
                                    if (process.ProcessName == "explorer" ||
                                        process.ProcessName == "regedit" ||
                                        process.ProcessName == "rundll32")
                                    {
                                        string logKey = $"reg_{process.ProcessName}_{regKey}";
                                        if (!processAccessCache.ContainsKey(logKey) ||
                                            (DateTime.Now - processAccessCache[logKey]).TotalSeconds > 60)
                                        {
                                            AddPrivacyLog(
                                                process.ProcessName,
                                                regKey,
                                                "Registry",
                                                $"Registry access detected: {regKey}",
                                                "Medium"
                                            );
                                            processAccessCache[logKey] = DateTime.Now;
                                        }
                                    }
                                }
                                catch { }
                            }
                        }
                    }
                    catch { }
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"Registry monitoring error: {ex.Message}");
            }
        }

        private void MonitorNetworkConnections()
        {
            try
            {
                var tcpConnections = IPGlobalProperties.GetIPGlobalProperties().GetActiveTcpConnections();
                var processes = Process.GetProcesses();

                foreach (var connection in tcpConnections)
                {
                    // Check if connection is established to external addresses
                    if (!System.Net.IPAddress.IsLoopback(connection.RemoteEndPoint.Address))
                    {
                        // Try to find the process using this connection
                        foreach (var process in processes)
                        {
                            try
                            {
                                if (process.ProcessName == "chrome" ||
                                    process.ProcessName == "firefox" ||
                                    process.ProcessName == "edge" ||
                                    process.ProcessName == "iexplore" ||
                                    process.ProcessName == "msedge")
                                {
                                    string logKey = $"net_{process.ProcessName}_{connection.RemoteEndPoint.Address}";
                                    if (!processAccessCache.ContainsKey(logKey) ||
                                        (DateTime.Now - processAccessCache[logKey]).TotalSeconds > 30)
                                    {
                                        AddPrivacyLog(
                                            process.ProcessName,
                                            connection.RemoteEndPoint.Address.ToString(),
                                            "Network",
                                            $"Network connection to {connection.RemoteEndPoint.Address}:{connection.RemoteEndPoint.Port}",
                                            DetermineNetworkRisk(connection.RemoteEndPoint.Address.ToString())
                                        );
                                        processAccessCache[logKey] = DateTime.Now;
                                    }
                                }
                            }
                            catch { }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"Network monitoring error: {ex.Message}");
            }
        }

        private void MonitorProcesses()
        {
            try
            {
                var processes = Process.GetProcesses();

                // Track new processes that might access sensitive data
                foreach (var process in processes)
                {
                    try
                    {
                        if (!trackedProcesses.Contains(process.ProcessName) &&
                            process.ProcessName != "PrivacyMonitor")
                        {
                            // Check if process is known for data collection
                            if (IsDataCollectionProcess(process.ProcessName))
                            {
                                AddPrivacyLog(
                                    process.ProcessName,
                                    process.Id.ToString(),
                                    "Process",
                                    $"New process started that may collect data",
                                    "High"
                                );
                                trackedProcesses.Add(process.ProcessName);
                            }
                        }
                    }
                    catch { }
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine($"Process monitoring error: {ex.Message}");
            }
        }

        private bool IsDataCollectionProcess(string processName)
        {
            string[] dataCollectionProcesses = new[]
            {
                "chrome", "firefox", "edge", "iexplore", "msedge",
                "outlook", "thunderbird", "slack", "discord", "teams",
                "spotify", "netflix", "steam", "epicgameslauncher",
                "googledrive", "onedrive", "dropbox", "icloud",
                "whatsapp", "telegram", "signal", "zoom", "skype",
                "adobe", "photoshop", "premiere", "aftereffects",
                "visualstudio", "code", "intellij", "eclipse",
                "excel", "word", "powerpoint", "onenote"
            };

            return Array.Exists(dataCollectionProcesses,
                p => processName.ToLower().Contains(p.ToLower()));
        }

        private string DetermineFileRisk(string filePath)
        {
            string fileName = Path.GetFileName(filePath).ToLower();

            // High risk file types
            if (fileName.Contains("password") ||
                fileName.Contains("credit") ||
                fileName.Contains("card") ||
                fileName.Contains("bank") ||
                fileName.Contains("personal") ||
                fileName.Contains("social") ||
                fileName.Contains("ssn") ||
                fileName.Contains("passport") ||
                fileName.Contains("driver") ||
                fileName.Contains("license"))
            {
                return "High";
            }

            // Medium risk file types
            if (fileName.Contains("document") ||
                fileName.Contains("report") ||
                fileName.Contains("finance") ||
                fileName.Contains("account") ||
                fileName.Contains("contact") ||
                fileName.Contains("address") ||
                fileName.Contains("phone") ||
                fileName.Contains("email"))
            {
                return "Medium";
            }

            // File extensions
            string extension = Path.GetExtension(filePath).ToLower();
            if (extension == ".docx" || extension == ".xlsx" ||
                extension == ".pdf" || extension == ".txt" ||
                extension == ".csv" || extension == ".json" ||
                extension == ".xml")
            {
                return "Medium";
            }

            return "Low";
        }

        private string DetermineNetworkRisk(string address)
        {
            // Check for known tracking or data collection services
            string[] trackingDomains = new[]
            {
                "google", "facebook", "twitter", "instagram", "linkedin",
                "amazon", "microsoft", "apple", "netflix", "spotify",
                "doubleclick", "googleapis", "cloudflare", "akamai",
                "facebook", "twitter", "instagram", "pinterest", "snapchat",
                "tiktok", "reddit", "youtube", "vimeo", "dailymotion",
                "salesforce", "hubspot", "mailchimp", "sendgrid",
                "analytics", "tracking", "metrics", "pixel", "beacon"
            };

            foreach (var domain in trackingDomains)
            {
                if (address.ToLower().Contains(domain.ToLower()))
                {
                    return "High";
                }
            }

            // Check for known advertising/tracking IP ranges
            if (address.StartsWith("192.168.") ||
                address.StartsWith("10.") ||
                address.StartsWith("172.16.") ||
                address.StartsWith("127."))
            {
                return "Low";
            }

            return "Medium";
        }

        private void AddPrivacyLog(string processName, string resourceName,
            string accessType, string details, string riskLevel)
        {
            lock (privacyLogs)
            {
                var log = new PrivacyLog
                {
                    ProcessName = processName.Length > 50 ? processName.Substring(0, 47) + "..." : processName,
                    ResourceName = resourceName.Length > 50 ? resourceName.Substring(0, 47) + "..." : resourceName,
                    AccessType = accessType,
                    Details = details,
                    RiskLevel = riskLevel
                };

                privacyLogs.Insert(0, log);

                // Keep only last 1000 entries
                if (privacyLogs.Count > 1000)
                {
                    privacyLogs.RemoveRange(1000, privacyLogs.Count - 1000);
                }
            }
        }

        public List<PrivacyLog> GetPrivacyLogs()
        {
            lock (privacyLogs)
            {
                return new List<PrivacyLog>(privacyLogs);
            }
        }

        public void ClearLogs()
        {
            lock (privacyLogs)
            {
                privacyLogs.Clear();
                trackedProcesses.Clear();
                processAccessCache.Clear();
            }
        }
    }
}