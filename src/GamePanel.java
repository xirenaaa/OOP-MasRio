import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

// Game panel class
class GamePanel extends JPanel implements ActionListener {
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    private static final int TILE_SIZE = 20;

    private Player player;
    private ArrayList<Coin> coins;
    private ArrayList<Monster> monsters;
    private Wall[] walls;

    private Timer gameTimer;
    private int score = 0;
    private int topScore = 0;
    private boolean gameOver = false;

    // Grid representation of the maze (1 = wall, 0 = path)
    private int[][] mazeGrid = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        initGame();

        gameTimer = new Timer(100, this);
        gameTimer.start();
    }

    private void initGame() {
        // Initialize player at a fixed position
        player = new Player(40, 40);

        // Initialize walls based on the maze grid
        ArrayList<Wall> wallList = new ArrayList<>();
        for (int row = 0; row < mazeGrid.length; row++) {
            for (int col = 0; col < mazeGrid[0].length; col++) {
                if (mazeGrid[row][col] == 1) {
                    wallList.add(new Wall(col * TILE_SIZE, row * TILE_SIZE));
                }
            }
        }
        walls = wallList.toArray(new Wall[0]);

        // Initialize coins
        coins = new ArrayList<>();
        initializeCoins();

        // Initialize monsters
        monsters = new ArrayList<>();
        initializeMonsters();

        score = 0;
        gameOver = false;
    }

    private void initializeCoins() {
        Random rand = new Random();
        int coinCount = 0;
        int maxCoins = 30; // Maximum number of coins to place

        while (coinCount < maxCoins) {
            int row = rand.nextInt(mazeGrid.length);
            int col = rand.nextInt(mazeGrid[0].length);

            // Place coin only on paths (not walls)
            if (mazeGrid[row][col] == 0) {
                int coinX = col * TILE_SIZE + 5; // Offset to center coin
                int coinY = row * TILE_SIZE + 5; // Offset to center coin

                // Check if there's already a coin at this position
                boolean coinExists = false;
                for (Coin coin : coins) {
                    if (coin.getX() == coinX && coin.getY() == coinY) {
                        coinExists = true;
                        break;
                    }
                }

                // Add new coin if position is free
                if (!coinExists) {
                    coins.add(new Coin(coinX, coinY));
                    coinCount++;
                }
            }
        }
    }

    private void initializeMonsters() {
        // Add horizontal monsters
        monsters.add(new HorizontalMonster(120, 140));
        monsters.add(new HorizontalMonster(320, 240));
        monsters.add(new HorizontalMonster(120, 340));
        monsters.add(new HorizontalMonster(400, 440));

        // Add vertical monsters
        monsters.add(new VerticalMonster(180, 100));
        monsters.add(new VerticalMonster(420, 200));
        monsters.add(new VerticalMonster(280, 300));
        monsters.add(new VerticalMonster(380, 380));
    }

    public void movePlayer(int dx, int dy) {
        if (!gameOver) {
            player.move(dx, dy, walls);
            checkCollisions();
        } else {
            // Restart game if it's over and player presses a key
            initGame();
        }
    }

    private void checkCollisions() {
        // Check collision with coins
        for (Coin coin : coins) {
            if (!coin.isCollected() && player.collidesWith(coin)) {
                coin.collect();
                score++;
                playCoinSound(); // Sound effect method (stub)

                // Check if all coins are collected
                boolean allCollected = true;
                for (Coin c : coins) {
                    if (!c.isCollected()) {
                        allCollected = false;
                        break;
                    }
                }

                if (allCollected) {
                    // Victory - all coins collected
                    if (score > topScore) {
                        topScore = score;
                    }
                    gameOver = true;
                    playVictorySound(); // Victory sound method (stub)
                }
            }
        }

        // Check collision with monsters
        for (Monster monster : monsters) {
            if (player.collidesWith(monster)) {
                if (score > topScore) {
                    topScore = score;
                }
                gameOver = true;
                playDefeatSound(); // Defeat sound method (stub)
                break;
            }
        }
    }

    // Stub methods for sound effects - these would be implemented with proper sound libraries
    private void playCoinSound() {
        // In a full implementation, this would play a coin collection sound
        // System.out.println("Coin collected!");
    }

    private void playVictorySound() {
        // Would play victory sound
        // System.out.println("Victory!");
    }

    private void playDefeatSound() {
        // Would play defeat sound
        // System.out.println("Game over!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            // Move monsters (they have their own speed control now)
            for (Monster monster : monsters) {
                monster.move(walls);
            }

            // Animate coins
            for (Coin coin : coins) {
                if (coin instanceof Coin) {
                    // This will update the animation state
                    repaint();
                }
            }

            // Check collisions after monster movement
            checkCollisions();
        }

        // Repaint the game panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Create a background that looks more like Mario world
        // Sky background
        g.setColor(new Color(107, 140, 255));
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        // Draw walls
        for (Wall wall : walls) {
            wall.draw(g);
        }

        // Draw coins
        for (Coin coin : coins) {
            coin.draw(g);
        }

        // Draw monsters
        for (Monster monster : monsters) {
            monster.draw(g);
        }

        // Draw player
        player.draw(g);

        // Draw score in Mario style
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("COINS: " + score, 20, PANEL_HEIGHT - 40);
        g.drawString("TOP SCORE: " + topScore, 20, PANEL_HEIGHT - 20);

        // Draw game over message
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 180)); // Semi-transparent background
            g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String message = "Game Over";

            // Check if player won (all coins collected)
            boolean allCollected = true;
            for (Coin c : coins) {
                if (!c.isCollected()) {
                    allCollected = false;
                    break;
                }
            }

            if (allCollected) {
                message = "You Win!";
                g.setColor(Color.GREEN);
            }

            FontMetrics metrics = g.getFontMetrics();
            int x = (PANEL_WIDTH - metrics.stringWidth(message)) / 2;
            int y = PANEL_HEIGHT / 2;

            g.drawString(message, x, y);

            g.setFont(new Font("Arial", Font.PLAIN, 18));
            String restartMessage = "Press any key to restart";
            metrics = g.getFontMetrics();
            x = (PANEL_WIDTH - metrics.stringWidth(restartMessage)) / 2;
            y = PANEL_HEIGHT / 2 + 40;

            g.drawString(restartMessage, x, y);
        }
    }
}