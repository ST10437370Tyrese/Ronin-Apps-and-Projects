🚀 Retro Space Shooter

A complete, retro-style space shooter game built with C# and Windows Forms. This project is part of my portfolio to demonstrate C# game development skills, featuring power-ups, boss battles, particle effects, and procedurally generated audio.
🎮 Game Overview

Retro Space Shooter is a classic arcade-style game where you control a spaceship defending against waves of enemies. The game features:

    Retro Pixel Graphics: Hand-drawn pixel-style sprites with a nostalgic feel

    Multiple Enemy Types: Basic, Fast, and Tank enemies with unique behaviors

    Boss Battles: Large boss enemies appear periodically with health bars and attack patterns

    Power-Up System: Collect power-ups for Double, Triple, or Rapid fire weapons

    Particle Effects: Thruster trails and explosion effects

    Sound System: Procedurally generated WAV audio files for all game sounds

    High Score Tracking: Persistent high score saved locally

    Dynamic Difficulty: Enemy spawn rate increases over time

🎯 Features
Gameplay

    Player Controls: Arrow keys to move, Spacebar to shoot

    Weapon System: Single, Double, Triple, and Rapid fire modes

    Enemy Types: 3 distinct enemy types with different behaviors

    Boss Enemy: Large boss with health bar and spread-shot attacks

    Power-Ups: Random drops from defeated enemies

    Scoring: 10 points per enemy, 100 points per boss

Visual Effects

    Animated Starfield: Scrolling stars for immersive space feel

    Thruster Particles: Orange particle trails when moving

    Explosion Effects: Expanding, fading explosions with color gradients

    Ship Design: Retro pixel-style player ship with cockpit and engine details

Audio System

    Procedural Sound Generation: All WAV files created programmatically

    Sound Effects: Shoot, explosion, power-up, and game over sounds

    Background Music: Simple chiptune-style looping background music

    No External Files: All audio generated on first run

Technical Features

    Double Buffered Rendering: Smooth 60 FPS gameplay

    Collision Detection: Rectangle-based collision system

    Object Pooling: Efficient management of game objects

    State Management: Game states (playing, game over, restart)

    High Score Persistence: Saves/loads from local file

🛠️ Technologies Used
Technology	Purpose
C#	Core game logic and programming language
.NET Framework 4.7.2	Application framework
Windows Forms	UI framework and rendering
System.Drawing	Graphics and rendering
System.Media	Audio playback
BinaryWriter	WAV file generation

🎮 How to Play

    Start the Game: Run the application from Visual Studio or the executable

    Controls:

        Arrow Keys: Move your ship

        Spacebar: Shoot

        R: Restart after game over

    Objective: Survive as long as possible while destroying enemies

    Scoring:

        Basic Enemy: 10 points

        Boss Enemy: 100 points

    Power-Ups (dropped by enemies):

        "2" (Blue): Double Shot - fires two bullets

        "3" (Magenta): Triple Shot - fires three bullets in a spread

        "R" (Yellow): Rapid Fire - increases firing rate

💻 Installation & Setup
Prerequisites

    Windows OS

    Visual Studio 2019 or later

    .NET Framework 4.7.2

Steps

    Clone the repository:
    bash

    git clone https://github.com/ST10437370Tyrese/Ronin-Apps-and-Projects.git

    Navigate to the project folder:
    bash

    cd Ronin-Apps-and-Projects/RetroSpaceShooter

    Open RetroSpaceShooter.sln in Visual Studio

    Build the solution (Ctrl+Shift+B)

    Run the application (F5)

First Run

    The game will automatically generate all sound files in a Sounds folder

    A highscore.txt file will be created to track high scores

    🔧 Future Enhancements

    Add more power-up types (Shield, Speed Boost, Extra Life)

    Implement multiple levels with increasing difficulty

    Add enemy formations and patterns

    Include more boss variations

    Add sound volume controls

    Implement pause functionality

    Add leaderboard with online ranking

    Create mobile/tablet support

🐛 Known Issues

    Sound Files: If sound files fail to generate, check write permissions in the application folder

    Performance: On very old hardware, reduce particle count in Game.cs

    Resolution: Game is fixed at 800x600 for consistent retro feel

🤝 Contributing

This is a personal portfolio project, but contributions are welcome!

    Fork the repository

    Create a feature branch (git checkout -b feature/AmazingFeature)

    Commit your changes (git commit -m 'Add some AmazingFeature')

    Push to the branch (git push origin feature/AmazingFeature)

    Open a Pull Request

📫 Contact

    Email: roninmauries@gmail.com

    LinkedIn: Ronin Mauries

    GitHub: ST10437370Tyrese

🙏 Acknowledgments

    Inspired by classic arcade shooters like Galaga and Space Invaders

    Built as part of my C# learning journey

    Thanks to the .NET community for excellent documentation

⭐ If you enjoy this game, please give it a star on GitHub!

Built with ❤️ by Ronin Mauries
