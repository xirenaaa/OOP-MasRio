import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

// Game panel class
class GamePanel extends JPanel implements ActionListener {

    // offset camera
    private int offsetX = 0;
    private int offsetY = 0;

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    private static final int TILE_SIZE = 20 * 4;

    // Player position - fixed at center of screen
    private static final int PLAYER_SCREEN_X = 240;
    private static final int PLAYER_SCREEN_Y = 240;

    private Player player;
    private ArrayList<Coin> coins;
    private ArrayList<Monster> monsters;
    private Wall[] walls;

    private Timer gameTimer;
    private static int score = 0;
    private int topScore = 0;
    private boolean gameOver = false;

    int wallx = 0;
    int wally = 0;

    public static void addScore() {
        score += 1;
    }

    // Grid representation of the maze (1 = wall, 0 = path)
    private int[][] mazeGrid = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1},
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
        requestFocusInWindow();
    }

    public void updateVerticalMonster(String arah) {
        for (Monster monster : monsters) {
            if (Objects.equals(arah, "atas")) {
                monster.addVerticalMonsterY();
            }
            if (Objects.equals(arah, "bawah")) {
                monster.reduceVerticalMonsterY();
            }
            if (Objects.equals(arah, "kanan")) {
                monster.addVerticalMonsterX();
            }
            if (Objects.equals(arah, "kiri")) {
                monster.reduceVerticalMonsterX();
            }
        }
    }

    public int moveBackgroundX(String arah) {
        // Deteksi apakah ada tembok di kiri atau kanan
        boolean adaTembokKiri = isWallAt(160, 240);
        boolean adaTembokKanan = isWallAt(320, 240);

        if (Objects.equals(arah, "kiri")) {
            if (!adaTembokKiri) {
                wallx += 1; // Geser maze ke kanan, kesan player ke kiri
                moveCoins("kiri");
                updateVerticalMonster("kiri");
            }
        } else if (Objects.equals(arah, "kanan")) {
            if (!adaTembokKanan) {
                wallx -= 1; // Geser maze ke kiri, kesan player ke kanan
                moveCoins("kanan");
                updateVerticalMonster("kanan");
            }
        }

        updateWalls(); // Update posisi tembok
        player.updatePosition(); // Update posisi player di layar

        return wallx;
    }

    public int moveBackgroundY(String arah) {
        // Cek apakah ada wall di arah tujuan sebelum mengubah posisi
        boolean adaTembokBawah = isWallAt(240, 320);
        boolean adaTembokAtas = isWallAt(240, 160);

        if (Objects.equals(arah, "atas")) {
            if (!adaTembokAtas) {
                wally += 1;
                moveCoins("atas");
                updateVerticalMonster("atas");
            }
        } else if (Objects.equals(arah, "bawah")) {
            if (!adaTembokBawah) {
                wally -= 1;
                moveCoins("bawah");
                updateVerticalMonster("bawah");
            }
        }

        updateWalls();
        player.updatePosition();

        return wally;
    }

    // UPDATED: isWallAt method - hanya check brick walls yang menghalangi
    public boolean isWallAt(int checkX, int checkY) {
        for (Wall wall : walls) {
            if (wall.isBlocking() && wall.isAtPosition(checkX, checkY)) {
                return true;
            }
        }
        return false;
    }

    void moveCoins(String arah) {
        for (Coin coin : coins) {
            coin.removeIfAt240_240();

            if (Objects.equals(arah, "atas")) {
                coin.setOriginalY(coin.getOriginalY() + 80);
            } else if (Objects.equals(arah, "bawah")) {
                coin.setOriginalY(coin.getOriginalY() - 80);
            } else if (Objects.equals(arah, "kanan")) {
                coin.setOriginalX(coin.getOriginalX() - 80);
            } else if (Objects.equals(arah, "kiri")) {
                coin.setOriginalX(coin.getOriginalX() + 80);
            }
        }
    }

    private void initGame() {
        player = new Player(PLAYER_SCREEN_X, PLAYER_SCREEN_Y); // Player tetap di tengah layar
        player.updatePosition();
        updateWalls(); // Buat metode khusus
        coins = new ArrayList<>();
        monsters = new ArrayList<>();
        score = 0;
        gameOver = false;
        initializeCoins();
        initializeMonsters();
        System.out.println(gameOver);
    }

    public void setGameOver() {
        gameOver = true;
    }

    // FIXED: updateWalls method - extended coverage to fill entire screen
    private void updateWalls() {
        ArrayList<Wall> wallList = new ArrayList<>();

        // Calculate how many extra tiles we need to cover the screen completely
        int extraTilesX = (PANEL_WIDTH / TILE_SIZE) + 2; // Extra tiles for horizontal coverage
        int extraTilesY = (PANEL_HEIGHT / TILE_SIZE) + 2; // Extra tiles for vertical coverage

        // Start from negative positions to ensure full coverage
        int startRow = -extraTilesY;
        int endRow = mazeGrid.length + extraTilesY;
        int startCol = -extraTilesX;
        int endCol = mazeGrid[0].length + extraTilesX;

        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                int x = (col + wallx) * TILE_SIZE;
                int y = (row + wally) * TILE_SIZE;

                // Check if this tile is within screen bounds (with some padding)
                if (x > -TILE_SIZE && x < PANEL_WIDTH + TILE_SIZE &&
                        y > -TILE_SIZE && y < PANEL_HEIGHT + TILE_SIZE) {

                    // Determine what type of tile this should be
                    boolean isWallTile = false;

                    // If we're within the maze bounds, use the maze data
                    if (row >= 0 && row < mazeGrid.length && col >= 0 && col < mazeGrid[0].length) {
                        isWallTile = (mazeGrid[row][col] == 1);
                    } else {
                        // Outside maze bounds - create walls around the perimeter
                        isWallTile = true;
                    }

                    // Add grass background for every tile
                    wallList.add(new Wall(x, y, false)); // false = grass background

                    // Add brick wall on top of grass if it's a wall tile
                    if (isWallTile) {
                        wallList.add(new Wall(x, y, true)); // true = brick wall
                    }
                }
            }
        }

        walls = wallList.toArray(new Wall[0]);
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
                int coinX = col * TILE_SIZE; // Offset to center coin
                int coinY = row * TILE_SIZE; // Offset to center coin

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
        // Place monsters in areas with good movement paths
        monsters.add(new VerticalMonster(20 * 6, 20 * 3));   // Top area with vertical corridor
        monsters.add(new VerticalMonster(20 * 23, 20 * 3));  // Top right area
        monsters.add(new VerticalMonster(20 * 14, 20 * 11)); // Center area
        monsters.add(new VerticalMonster(20 * 8, 20 * 24));  // Bottom area
    }

    public void movePlayer(int dx, int dy) {
        if (!gameOver) {
            // player movement logic here if needed
        } else {
            // Restart game if it's over and player presses a key
            initGame();
        }
    }

    // FIXED: Collision detection method
    private void checkCollisions() {
        // Player position (fixed at center of screen)
        int playerCenterX = PLAYER_SCREEN_X + 40; // Player center X (80/2 = 40)
        int playerCenterY = PLAYER_SCREEN_Y + 40; // Player center Y (80/2 = 40)
        int playerRadius = 35; // Collision radius for player (slightly smaller than 40 for better gameplay)

        // Check collision with monsters
        for (Monster monster : monsters) {
            // Convert monster's world coordinates to screen coordinates
            int monsterScreenX = monster.getX() * 4; // Monster position scaled
            int monsterScreenY = monster.getY() * 4; // Monster position scaled
            int monsterCenterX = monsterScreenX + 40; // Monster center X (80/2 = 40 after scaling)
            int monsterCenterY = monsterScreenY + 40; // Monster center Y (80/2 = 40 after scaling)
            int monsterRadius = 35; // Collision radius for monster

            // Calculate distance between centers
            double distance = Math.sqrt(
                    Math.pow(playerCenterX - monsterCenterX, 2) +
                            Math.pow(playerCenterY - monsterCenterY, 2)
            );

            // Check if collision occurred (distance less than sum of radii)
            if (distance < (playerRadius + monsterRadius)) {
                if (score > topScore) {
                    topScore = score;
                }
                gameOver = true;
                playDefeatSound();
                System.out.println("COLLISION DETECTED! Distance: " + distance +
                        ", Player: (" + playerCenterX + "," + playerCenterY + ")" +
                        ", Monster: (" + monsterCenterX + "," + monsterCenterY + ")");
                break;
            }
        }

        // Check collision with coins at player center
        for (Coin coin : coins) {
            if (!coin.isCollected()) {
                // Check if coin is at the center position where player is
                int coinCenterX = coin.getOriginalX() + 40;
                int coinCenterY = coin.getOriginalY() + 40;

                double coinDistance = Math.sqrt(
                        Math.pow(playerCenterX - coinCenterX, 2) +
                                Math.pow(playerCenterY - coinCenterY, 2)
                );

                if (coinDistance < 50) { // Coin collection radius
                    coin.collect();
                    addScore();
                    playCoinSound();
                }
            }
        }
    }

    // Stub methods for sound effects
    private void playCoinSound() {
        // System.out.println("Coin collected!");
    }

    private void playVictorySound() {
        // System.out.println("Victory!");
    }

    private void playDefeatSound() {
        System.out.println("Game over! Collision with monster!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            for (Monster monster : monsters) {
                monster.move(walls);
            }

            for (Coin coin : coins) {
                coin.updateAnimation();  // update posisi/animasi, tapi tidak repaint
            }

            // Check collisions every frame
            checkCollisions();

            repaint();  // repaint hanya sekali setelah semua update selesai
        }
    }

    // FIXED: paintComponent method - optimized rendering order
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grass background first (all tiles)
        for (Wall wall : walls) {
            if (wall.getWallType() == 0) { // Grass background
                wall.draw(g);
            }
        }

        // Draw brick walls on top of grass
        for (Wall wall : walls) {
            if (wall.getWallType() == 1) { // Brick walls
                wall.draw(g);
            }
        }

        // Draw coins
        for (Coin coin : coins) {
            coin.draw(g);
        }

        // Draw monsters
        for (Monster monster : monsters) {
            monster.draw(g);
        }

        // Draw player (always at center)
        player.draw(g);

        // DEBUG: Draw collision circles (remove after testing)
        if (false) { // Set to true for debugging
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(255, 0, 0, 100)); // Semi-transparent red

            // Player collision circle
            g2d.fillOval(PLAYER_SCREEN_X + 5, PLAYER_SCREEN_Y + 5, 70, 70);

            // Monster collision circles
            for (Monster monster : monsters) {
                int monsterScreenX = monster.getX() * 4;
                int monsterScreenY = monster.getY() * 4;
                g2d.fillOval(monsterScreenX + 5, monsterScreenY + 5, 70, 70);
            }
        }

        // Draw UI elements with better visibility
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        // Add semi-transparent background to text for better visibility
        g.setColor(new Color(0, 0, 0, 128)); // Semi-transparent black
        g.fillRect(15, PANEL_HEIGHT - 50, 200, 35);

        g.setColor(Color.WHITE);
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