import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

// Game panel class
class GamePanel extends JPanel implements ActionListener {

//    offset camera
    private int offsetX = 0;
    private int offsetY = 0;

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    private static final int TILE_SIZE = 20 * 4;

    private Player player;
    private ArrayList<Coin> coins;
    private ArrayList<Monster> monsters;
    private Wall[] walls;

    private Timer gameTimer;
    private static int score = 0;
    private int topScore = 0;
    private boolean gameOver = false;

    int wallx= 0;
    int wally = 0;

    public static void addScore(){
        score += 1;
    }

    // Grid representation of the maze (1 = wall, 0 = path)
    private int[][] mazeGrid = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
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
        requestFocusInWindow();
    }

    public void updateVerticalMonster(String arah){
        for (Monster monster : monsters) {
            if (Objects.equals(arah, "atas")){
                monster.addVerticalMonsterY();
            }
            if (Objects.equals(arah, "bawah")){
                monster.reduceVerticalMonsterY();
            }
            if (Objects.equals(arah, "kanan")){
                monster.addVerticalMonsterX();
            }
            if (Objects.equals(arah, "kiri")){
                monster.reduceVerticalMonsterX();
            }
        }
    };

    public int moveBackgroundX(String arah) {
        // Deteksi apakah ada tembok di kiri atau kanan
        boolean adaTembokKiri = isWallAt(160, 240);
        boolean adaTembokKanan = isWallAt(320, 240);

        if (Objects.equals(arah, "kiri")) {
            if (!adaTembokKiri) {
                wallx += 1; // Geser maze ke kanan, kesan player ke kiri
                moveCoins("kiri");
                updateVerticalMonster("kiri");
//                System.out.println("GERAK KE KIRI, wallx: " + wallx + ", wally: " + wally);
            }
        } else if (Objects.equals(arah, "kanan")) {
            if (!adaTembokKanan) {
                wallx -= 1; // Geser maze ke kiri, kesan player ke kanan
                moveCoins("kanan");
                updateVerticalMonster("kanan");
//                System.out.println("GERAK KE KANAN, wallx: " + wallx + ", wally: " + wally);
            }
        }

        updateWalls(); // Update posisi tembok
        player.updatePosition(); // Update posisi player di layar

//        System.out.println("Player Pos X: " + player.getX() + ", Y: " + player.getY());

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
//                System.out.println("atas, x " + wallx + ", y " + wally);
            }
        } else if (Objects.equals(arah, "bawah")) {
            if (!adaTembokBawah) {
                wally -= 1;
                moveCoins("bawah");
                updateVerticalMonster("bawah");
//                System.out.println("bawah, x " + wallx + ", y " + wally);
            }
        }

        updateWalls();
        player.updatePosition();

        // Debug posisi player
//        System.out.println("Player Pos X: " + player.getX() + ", Y: " + player.getY());

        return wally;
    }


    public boolean isWallAt(int checkX, int checkY) {
        for (Wall wall : walls) {
            if (wall.isAtPosition(checkX, checkY)) {
                return true;
            }
        }
        return false;
    }

    void moveCoins(String arah) {
//        int i = 0;

        for (Coin coin : coins) {
            coin.removeIfAt240_240();

            if (Objects.equals(arah, "atas")) {
//                System.out.println("harusnya ke atas");
                coin.setOriginalY(coin.getOriginalY() + 80);
//                System.out.println(coin.getOriginalY());
//                System.out.println(i);
//                i+=1;
            }
            else if (Objects.equals(arah, "bawah")) {
//                System.out.println("harusnya ke bawah");
                coin.setOriginalY(coin.getOriginalY() - 80);
//                System.out.println(coin.getOriginalY());
//                System.out.println(i);
//                i+=1;
            }
            else if (Objects.equals(arah, "kanan")) {
//                System.out.println("harusnya ke kanan");
                coin.setOriginalX(coin.getOriginalX() - 80);
//                System.out.println(coin.getOriginalX());
//                System.out.println(i);
//                i+=1;
            }
            else if (Objects.equals(arah, "kiri")) {
//                System.out.println("harusnya ke kiri");
                coin.setOriginalX(coin.getOriginalX() + 80);
//                System.out.println(coin.getOriginalY());
//                System.out.println(i);
//                i+=1;
            }
        }
    }

    private void initGame() {
        player = new Player(40 * 6, 40 * 6); // Buat sekali saja
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

    public void setGameOver(){
        gameOver = true;
    }

    private void updateWalls() {
        ArrayList<Wall> wallList = new ArrayList<>();
        for (int row = 0; row < mazeGrid.length; row++) {
            for (int col = 0; col < mazeGrid[0].length; col++) {
                if (mazeGrid[row][col] == 1) {
                    wallList.add(new Wall((col + wallx) * TILE_SIZE, (row + wally) * TILE_SIZE));
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
        // Add horizontal monsters

//        monsters.add(new HorizontalMonster(0, 0));
//        monsters.add(new HorizontalMonster(0, 0));
//        monsters.add(new HorizontalMonster(0, 0));
//        monsters.add(new HorizontalMonster(0, 0));

        // Add vertical monsters
        monsters.add(new VerticalMonster(20 * 5, 20 * 5));
        monsters.add(new VerticalMonster(20 * 10, 20 * 11));
        monsters.add(new VerticalMonster(20 * 6, 20 * 2));
        monsters.add(new VerticalMonster(20 * 22, 20 * 5));
    }

    public void movePlayer(int dx, int dy) {
        if (!gameOver) {
//            player.move(dx, dy, walls);
        } else {
            // Restart game if it's over and player presses a key
            initGame();
        }
    }

    private void checkCollisions() {
        // Check collision with coins
//        for (Coin coin : coins) {
//            if (!coin.isCollected() && player.collidesWith(coin)) {
//                coin.collect();
//                score++;
//                playCoinSound(); // Sound effect method (stub)
//
//                // Check if all coins are collected
//                boolean allCollected = true;
//                for (Coin c : coins) {
//                    if (!c.isCollected()) {
//                        allCollected = false;
//                        break;
//                    }
//                }
//
//                if (allCollected) {
//                    // Victory - all coins collected
//                    if (score > topScore) {
//                        topScore = score;
//                    }
//                    gameOver = true;
//                    playVictorySound(); // Victory sound method (stub)
//                }
//            }
//        }

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
            for (Monster monster : monsters) {
                monster.move(walls);
            }

            for (Coin coin : coins) {
                coin.updateAnimation();  // update posisi/animasi, tapi tidak repaint
            }

//            checkCollisions();  // cek tabrakan setelah update posisi

            repaint();  // repaint hanya sekali setelah semua update selesai
        }
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