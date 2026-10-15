using System;
using System.IO;
using System.IO.Compression;

namespace ZipExtractor;

public static class ZipService
{
    public static void Extract(
        string zipPath,
        string destinationFolder,
        IProgress<(int Percent, string File)> progress,
        CancellationToken token)
    {
        Directory.CreateDirectory(destinationFolder);

        string root = Path.GetFullPath(destinationFolder);
        if (!root.EndsWith(Path.DirectorySeparatorChar))
            root += Path.DirectorySeparatorChar;

        using var archive = ZipFile.OpenRead(zipPath);
        int total = Math.Max(archive.Entries.Count, 1);
        int done = 0;

        foreach (var entry in archive.Entries)
        {
            token.ThrowIfCancellationRequested();

            string target = Path.GetFullPath(Path.Combine(root, entry.FullName));

            // Zip-slip protection
            if (!target.StartsWith(root, StringComparison.OrdinalIgnoreCase))
                throw new InvalidDataException($"Unsafe path in archive: {entry.FullName}");

            if (string.IsNullOrEmpty(entry.Name))
            {
                // Entry is a folder
                Directory.CreateDirectory(target);
            }
            else
            {
                Directory.CreateDirectory(Path.GetDirectoryName(target)!);
                entry.ExtractToFile(target, overwrite: true);
            }

            done++;
            progress.Report((done * 100 / total, entry.FullName));

            // Optional: uncomment so small zips still show the animation
            // Thread.Sleep(40);
        }
    }
}