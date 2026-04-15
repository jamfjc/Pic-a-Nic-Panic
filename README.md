# Pic-a-Nic-Panic
Built With Java (Swing / AWT) — UI and rendering  SQLite via JDBC — high score persistence  Apache Ant / NetBeans — build tooling


# Yogi Bear - Picnic Basket Collector

A 2D arcade-style Java game inspired by the classic Yogi Bear cartoon. Play as Yogi Bear and navigate through Jellystone Park collecting picnic baskets while avoiding the park rangers across 10 progressively challenging levels.

---

## Gameplay

- Move Yogi Bear around the park using the **W A S D** keys
- Collect all the **picnic baskets** on each level to advance to the next
- Avoid **park rangers** who patrol the park — getting too close costs you a life
- Navigate around **trees** and **mountains** which block your path
- You start with **3 lives** — lose them all and the game ends
- After the game ends, if you achieved a high score you can enter your name to the leaderboard
- The game cycles back to level 1 after completing all 10 levels, keeping your total basket count

---

## Features

- 10 hand-crafted levels loaded from external text files
- Smooth 50fps game loop using a Swing `Timer`
- Collision detection for obstacles, baskets, and rangers
- Rangers patrol horizontally or vertically between set boundaries
- Brief invulnerability period after being hit by a ranger
- In-game HUD showing lives, baskets collected, current level, time elapsed, and remaining baskets
- Persistent high score leaderboard stored in a local SQLite database (top 10 scores)
- Menu bar with New Game, High Scores, and Exit options

---

## Project Structure

YogiBearGame/
├── src/yogibeargame/
│ ├── YogiBearGame.java # Entry point, launches the game window
│ ├── GameWindow.java # JFrame window and menu bar
│ ├── GamePanel.java # Core game loop, rendering, and input handling
│ ├── GameEntity.java # Abstract base class for all game objects
│ ├── Player.java # Player character, movement, lives, invulnerability
│ ├── Ranger.java # Enemy ranger, patrol movement, player detection
│ ├── Basket.java # Collectible picnic basket
│ ├── Obstacle.java # Tree and mountain obstacles
│ ├── Level.java # Level loader, parses level text files
│ └── HighScoreManager.java # SQLite-backed high score persistence
├── level1.txt – level10.txt # Level definition files
├── highscores.db # SQLite database (auto-created on first run)
└── build.xml # Apache Ant build file (NetBeans project)


---

## Level File Format

Levels are defined in plain text files (`level1.txt` to `level10.txt`) located in the project root. Each file follows this format:
tree
mountain
ranger <h|v>
basket


- `h` = ranger patrols horizontally, `v` = vertically
- `min_bound` / `max_bound` define the ranger's patrol range
- If a level file fails to load, a built-in default level is used as a fallback

---

## Requirements

- Java 23 or later
- [SQLite JDBC Driver](https://github.com/xerial/sqlite-jdbc/releases/tag/3.47.1.0) (`sqlite-jdbc-3.47.1.0.jar`)

---

## How to Run

### In NetBeans
1. Open the project in NetBeans
2. Add `sqlite-jdbc-3.47.1.0.jar` to the project libraries (right-click project → Properties → Libraries)
3. Press **Run** (F6)
   
<img width="792" height="643" alt="image" src="https://github.com/user-attachments/assets/cfd946b3-5b96-440a-9833-beb162f255ae" />

<img width="792" height="639" alt="Screenshot 2026-04-15 at 18 25 04" src="https://github.com/user-attachments/assets/40c060e4-8257-4815-ae8b-9920b57a6de8" />


### From the command line
```bash
# Compile
javac -cp sqlite-jdbc-3.47.1.0.jar -d build/classes src/yogibeargame/*.java

# Run
java -cp build/classes:sqlite-jdbc-3.47.1.0.jar yogibeargame.YogiBearGame

On Windows, replace : with ; in the classpath separator.

Controls
Key	Action
W	Move Up
A	Move Left
S	Move Down
D	Move Right

