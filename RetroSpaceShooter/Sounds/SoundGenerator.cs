using System;
using System.IO;
using System.Media;
using System.Windows.Forms;

namespace RetroSpaceShooter
{
    public static class SoundGenerator
    {
        public static void GenerateAllSounds()
        {
            string soundDir = Path.Combine(Application.StartupPath, "Sounds");
            if (!Directory.Exists(soundDir))
            {
                Directory.CreateDirectory(soundDir);
            }

            GenerateShootSound(Path.Combine(soundDir, "shoot.wav"));
            GenerateExplosionSound(Path.Combine(soundDir, "explosion.wav"));
            GeneratePowerUpSound(Path.Combine(soundDir, "powerup.wav"));
            GenerateGameOverSound(Path.Combine(soundDir, "gameover.wav"));
            GenerateBackgroundMusic(Path.Combine(soundDir, "background.wav"));
        }

        private static void GenerateShootSound(string filename)
        {
            // Create a simple laser/pew sound
            int sampleRate = 44100;
            int duration = 150; // milliseconds
            int sampleCount = sampleRate * duration / 1000;

            using (MemoryStream ms = new MemoryStream())
            using (BinaryWriter writer = new BinaryWriter(ms))
            {
                // WAV header
                WriteWavHeader(writer, sampleRate, 1, sampleCount);

                // Generate audio data (high frequency descending tone)
                for (int i = 0; i < sampleCount; i++)
                {
                    double t = (double)i / sampleRate;
                    double freq = 800.0 - (t * duration / 1000.0) * 600.0; // Descending frequency
                    double value = Math.Sin(2 * Math.PI * freq * t) * 0.5;
                    // Add some harmonic content
                    value += Math.Sin(2 * Math.PI * freq * 2.0 * t) * 0.2;
                    short sample = (short)(value * 30000);
                    writer.Write(sample);
                }

                // Update WAV header with actual data length
                UpdateWavHeader(ms, sampleCount);

                // Save to file
                File.WriteAllBytes(filename, ms.ToArray());
            }
        }

        private static void GenerateExplosionSound(string filename)
        {
            int sampleRate = 44100;
            int duration = 500;
            int sampleCount = sampleRate * duration / 1000;

            using (MemoryStream ms = new MemoryStream())
            using (BinaryWriter writer = new BinaryWriter(ms))
            {
                WriteWavHeader(writer, sampleRate, 1, sampleCount);

                Random random = new Random();
                for (int i = 0; i < sampleCount; i++)
                {
                    double t = (double)i / sampleRate;
                    // Noise with envelope
                    double envelope = Math.Exp(-t * 8.0);
                    double noise = (random.NextDouble() * 2 - 1) * envelope;

                    // Add some low frequency rumble
                    double rumble = Math.Sin(2 * Math.PI * 60 * t) * envelope * 0.3;
                    double value = noise * 0.7 + rumble * 0.3;

                    short sample = (short)(value * 30000);
                    writer.Write(sample);
                }

                UpdateWavHeader(ms, sampleCount);
                File.WriteAllBytes(filename, ms.ToArray());
            }
        }

        private static void GeneratePowerUpSound(string filename)
        {
            int sampleRate = 44100;
            int duration = 300;
            int sampleCount = sampleRate * duration / 1000;

            using (MemoryStream ms = new MemoryStream())
            using (BinaryWriter writer = new BinaryWriter(ms))
            {
                WriteWavHeader(writer, sampleRate, 1, sampleCount);

                double freq = 440;
                for (int i = 0; i < sampleCount; i++)
                {
                    double t = (double)i / sampleRate;
                    // Ascending tone
                    double freqMultiplier = 1.0 + t * 2.0;
                    double value = Math.Sin(2 * Math.PI * freq * freqMultiplier * t) * 0.5;
                    // Add harmonic
                    value += Math.Sin(2 * Math.PI * freq * 2 * freqMultiplier * t) * 0.2;
                    // Envelope
                    double envelope = Math.Min(1.0, t * 5.0) * Math.Exp(-t * 5.0);
                    value *= envelope;

                    short sample = (short)(value * 30000);
                    writer.Write(sample);
                }

                UpdateWavHeader(ms, sampleCount);
                File.WriteAllBytes(filename, ms.ToArray());
            }
        }

        private static void GenerateGameOverSound(string filename)
        {
            int sampleRate = 44100;
            int duration = 800;
            int sampleCount = sampleRate * duration / 1000;

            using (MemoryStream ms = new MemoryStream())
            using (BinaryWriter writer = new BinaryWriter(ms))
            {
                WriteWavHeader(writer, sampleRate, 1, sampleCount);

                for (int i = 0; i < sampleCount; i++)
                {
                    double t = (double)i / sampleRate;
                    // Descending minor chord
                    double freq1 = 523.25; // C5
                    double freq2 = 392.00; // G4
                    double freq3 = 311.13; // D#4

                    double envelope = Math.Exp(-t * 2.0);
                    double value = (Math.Sin(2 * Math.PI * freq1 * t) * 0.3 +
                                  Math.Sin(2 * Math.PI * freq2 * t) * 0.3 +
                                  Math.Sin(2 * Math.PI * freq3 * t) * 0.4) * envelope;

                    short sample = (short)(value * 30000);
                    writer.Write(sample);
                }

                UpdateWavHeader(ms, sampleCount);
                File.WriteAllBytes(filename, ms.ToArray());
            }
        }

        private static void GenerateBackgroundMusic(string filename)
        {
            int sampleRate = 44100;
            int duration = 10000; // 10 seconds loop
            int sampleCount = sampleRate * duration / 1000;

            using (MemoryStream ms = new MemoryStream())
            using (BinaryWriter writer = new BinaryWriter(ms))
            {
                WriteWavHeader(writer, sampleRate, 1, sampleCount);

                // Simple retro chiptune background music
                double[] notes = { 262, 294, 330, 349, 392, 440, 494, 523 };
                int noteIndex = 0;
                int noteDuration = sampleRate / 2; // Half second per note

                for (int i = 0; i < sampleCount; i++)
                {
                    double t = (double)i / sampleRate;
                    int currentNote = (int)(i / noteDuration) % notes.Length;
                    double freq = notes[currentNote];

                    // Main melody
                    double mainMelody = Math.Sin(2 * Math.PI * freq * t) * 0.3;

                    // Arpeggio (higher octave, faster)
                    double arpeggioFreq = notes[(int)(i / (noteDuration / 4)) % notes.Length] * 2;
                    double arpeggio = Math.Sin(2 * Math.PI * arpeggioFreq * t) * 0.15;

                    // Bass line (lower octave, slower)
                    int bassNote = (int)(i / (noteDuration * 2)) % notes.Length;
                    double bassFreq = notes[bassNote] * 0.5;
                    double bass = Math.Sin(2 * Math.PI * bassFreq * t) * 0.25;

                    // Envelope
                    double envelope = 0.5 + 0.5 * Math.Sin(2 * Math.PI * 0.1 * t);

                    double value = (mainMelody + arpeggio + bass) * envelope;

                    // Limit to prevent clipping
                    value = Math.Min(1.0, Math.Max(-1.0, value));

                    short sample = (short)(value * 30000);
                    writer.Write(sample);
                }

                UpdateWavHeader(ms, sampleCount);
                File.WriteAllBytes(filename, ms.ToArray());
            }
        }

        private static void WriteWavHeader(BinaryWriter writer, int sampleRate, int channels, int sampleCount)
        {
            // RIFF header
            writer.Write(System.Text.Encoding.ASCII.GetBytes("RIFF"));
            writer.Write(36 + sampleCount * 2); // File size - 8
            writer.Write(System.Text.Encoding.ASCII.GetBytes("WAVE"));

            // Format chunk
            writer.Write(System.Text.Encoding.ASCII.GetBytes("fmt "));
            writer.Write(16); // Chunk size
            writer.Write((short)1); // Audio format (PCM)
            writer.Write((short)channels); // Number of channels
            writer.Write(sampleRate); // Sample rate
            writer.Write(sampleRate * channels * 2); // Byte rate
            writer.Write((short)(channels * 2)); // Block align
            writer.Write((short)16); // Bits per sample

            // Data chunk
            writer.Write(System.Text.Encoding.ASCII.GetBytes("data"));
            writer.Write(sampleCount * channels * 2); // Data size
        }

        private static void UpdateWavHeader(MemoryStream ms, int sampleCount)
        {
            byte[] wavData = ms.ToArray();
            using (MemoryStream newMs = new MemoryStream())
            using (BinaryWriter writer = new BinaryWriter(newMs))
            {
                // Copy header and update sizes
                writer.Write(wavData, 0, 4); // "RIFF"
                writer.Write(36 + sampleCount * 2); // File size - 8
                writer.Write(wavData, 8, 24); // "WAVEfmt " + chunk sizes
                writer.Write(sampleCount * 2); // Data size
                writer.Write(wavData, 44, wavData.Length - 44); // Rest of data

                // Update original stream
                ms.SetLength(0);
                ms.Write(newMs.ToArray(), 0, (int)newMs.Length);
            }
        }

        public static void PlaySoundFromFile(string filename)
        {
            try
            {
                if (File.Exists(filename))
                {
                    using (SoundPlayer player = new SoundPlayer(filename))
                    {
                        player.Play();
                    }
                }
            }
            catch { }
        }

        public static void PlaySoundLoopingFromFile(string filename)
        {
            try
            {
                if (File.Exists(filename))
                {
                    using (SoundPlayer player = new SoundPlayer(filename))
                    {
                        player.PlayLooping();
                    }
                }
            }
            catch { }
        }

        public static SoundPlayer GetSoundPlayer(string filename)
        {
            try
            {
                if (File.Exists(filename))
                {
                    return new SoundPlayer(filename);
                }
            }
            catch { }
            return null;
        }
    }
}