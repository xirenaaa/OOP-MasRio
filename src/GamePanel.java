import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener {

    private final MazeMain mainFrame;

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    private static final int TILE_SIZE = 80;

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

    private final int[][] mazeGrid = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };


    public GamePanel(MazeMain mainFrame) {
        this.mainFrame = mainFrame;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        initGame();
        gameTimer = new Timer(16, this);
        gameTimer.start();
        requestFocusInWindow();
    }

    public void setPlayerDirection(int direction) {
        if (player != null) {
            player.setCurrentDirection(direction);
        }
    }

    public static void addScore() {
        score += 1;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void restartGame() {
        if (score > topScore) {
            topScore = score;
        }
        score = 0;
        gameOver = false;
        wallx = 0;
        wally = 0;
        initGame();
    }

    private void initGame() {
        player = new Player(PLAYER_SCREEN_X, PLAYER_SCREEN_Y);
        coins = new ArrayList<>();
        monsters = new ArrayList<>();
        updateWalls();
        initializeCoins();
        initializeMonsters();
        gameOver = false;
    }

    public void setGameOver() {
        if (!gameOver) {
            gameOver = true;
            if (score > topScore) topScore = score;
            mainFrame.stopMusic();
        }
    }

    public boolean isWallAt(int checkX, int checkY) {
        if (walls == null) {
            return false;
        }
        for (Wall wall : walls) {
            if (wall != null && wall.isBlocking() && wall.isAtPosition(checkX, checkY)) {
                return true;
            }
        }
        return false;
    }

    public int moveBackgroundX(String arah) {
        boolean adaTembokKiri = isWallAt(160, 240);
        boolean adaTembokKanan = isWallAt(320, 240);

        if (Objects.equals(arah, "kiri")) {
            if (!adaTembokKiri) {
                wallx++;
                moveCoins("kiri");
                shiftAllMonsters(TILE_SIZE, 0);
            }
        } else if (Objects.equals(arah, "kanan")) {
            if (!adaTembokKanan) {
                wallx--;
                moveCoins("kanan");
                shiftAllMonsters(-TILE_SIZE, 0);
            }
        }
        updateWalls();
        return wallx;
    }

    public int moveBackgroundY(String arah) {
        boolean adaTembokBawah = isWallAt(240, 320);
        boolean adaTembokAtas = isWallAt(240, 160);

        if (Objects.equals(arah, "atas")) {
            if (!adaTembokAtas) {
                wally++;
                moveCoins("atas");
                shiftAllMonsters(0, TILE_SIZE);
            }
        } else if (Objects.equals(arah, "bawah")) {
            if (!adaTembokBawah) {
                wally--;
                moveCoins("bawah");
                shiftAllMonsters(0, -TILE_SIZE);
            }
        }
        updateWalls();
        return wally;
    }

    private void shiftAllMonsters(int dx, int dy) {
        if (monsters == null) return;
        for (Monster monster : monsters) {
            monster.shiftVisualPosition(dx, dy);
        }
    }

    void moveCoins(String arah) {
        if (coins == null) return;
        for (Coin coin : coins) {
            if (coin != null) {
                // Disesuaikan dengan nama method baru di Coin.java
                if (Objects.equals(arah, "atas")) coin.setScreenY(coin.getScreenY() + TILE_SIZE);
                else if (Objects.equals(arah, "bawah")) coin.setScreenY(coin.getScreenY() - TILE_SIZE);
                else if (Objects.equals(arah, "kanan")) coin.setScreenX(coin.getScreenX() - TILE_SIZE);
                else if (Objects.equals(arah, "kiri")) coin.setScreenX(coin.getScreenX() + TILE_SIZE);
            }
        }
    }

    private void updateWalls() {
        ArrayList<Wall> wallList = new ArrayList<>();
        int extraTilesX = (PANEL_WIDTH / TILE_SIZE) + 2;
        int extraTilesY = (PANEL_HEIGHT / TILE_SIZE) + 2;
        int startRow = -wally - extraTilesY;
        int endRow = -wally + extraTilesY;
        int startCol = -wallx - extraTilesX;
        int endCol = -wallx + extraTilesX;

        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                int x = (col + wallx) * TILE_SIZE;
                int y = (row + wally) * TILE_SIZE;
                if (x > -TILE_SIZE && x < PANEL_WIDTH + TILE_SIZE && y > -TILE_SIZE && y < PANEL_HEIGHT + TILE_SIZE) {
                    boolean isWallTile = (row < 0 || row >= mazeGrid.length || col < 0 || col >= mazeGrid[0].length) || (mazeGrid[row][col] == 1);
                    wallList.add(new Wall(x, y, false));
                    if (isWallTile) wallList.add(new Wall(x, y, true));
                }
            }
        }
        walls = wallList.toArray(new Wall[0]);
    }

    private void initializeCoins() {
        if (coins == null) coins = new ArrayList<>();
        coins.clear();
        int[][] coinPositions = {
                {1, 2}, {1, 8}, {1, 12}, {1, 20}, {1, 27}, {3, 1}, {3, 10}, {3, 18}, {3, 25},
                {5, 5}, {5, 14}, {5, 23}, {8, 1}, {8, 8}, {8, 16}, {8, 28}, {10, 3}, {10, 12},
                {10, 20}, {12, 6}, {12, 15}, {12, 25}, {14, 2}, {14, 22}, {17, 4}, {17, 13},
                {17, 26}, {20, 1}, {20, 10}, {20, 18}, {22, 5}, {22, 14}, {22, 23}, {25, 2},
                {25, 11}, {25, 28}, {28, 4}, {28, 13}, {28, 27}
        };
        for (int[] pos : coinPositions) {
            if (isValidPath(pos[1], pos[0])) { // Perhatikan: grid adalah [row][col], jadi y,x
                // Posisi coin adalah posisi grid * TILE_SIZE
                coins.add(new Coin(pos[0] * TILE_SIZE, pos[1] * TILE_SIZE));
            }
        }
    }

    private void initializeMonsters() {
        if (monsters == null) monsters = new ArrayList<>();
        monsters.clear();
        Random rand = new Random();

        int[][] monsterPositions = {
                {6, 3}, {23, 3}, {14, 11}, {8, 24}, {10, 5}, {20, 15}
        };
        boolean[] isVertical = {true, true, true, true, false, false};

        for (int i = 0; i < monsterPositions.length; i++) {
            int[] pos = monsterPositions[i];
            if (isValidPath(pos[1], pos[0])) { // Perhatikan: grid adalah [row][col], jadi y,x
                int patrolRange = rand.nextInt(4) + 3;
                int pixelSpeed = rand.nextInt(3) + 2;

                if (isVertical[i]) {
                    monsters.add(new VerticalMonster(pos[0], pos[1], patrolRange, pixelSpeed));
                } else {
                    monsters.add(new HorizontalMonster(pos[0], pos[1], patrolRange, pixelSpeed));
                }
            }
        }
    }

    private boolean isValidPath(int row, int col) {
        return row >= 0 && row < mazeGrid.length && col >= 0 && col < mazeGrid[0].length && mazeGrid[row][col] == 0;
    }

    private void checkCollisions() {
        if (player == null || monsters == null || coins == null) return;
        int playerCenterX = PLAYER_SCREEN_X + TILE_SIZE / 2;
        int playerCenterY = PLAYER_SCREEN_Y + TILE_SIZE / 2;
        int playerRadius = 35;

        for (Monster monster : monsters) {
            if (monster == null) continue;
            int monsterCenterX = monster.getX() + TILE_SIZE / 2;
            int monsterCenterY = monster.getY() + TILE_SIZE / 2;
            double distance = Math.hypot(playerCenterX - monsterCenterX, playerCenterY - monsterCenterY);
            if (distance < (playerRadius + 35)) {
                setGameOver();
                playDefeatSound();
                return;
            }
        }

        for (Coin coin : coins) {
            if (coin == null || coin.isCollected()) continue;
            // Disesuaikan dengan nama method baru di Coin.java
            int coinCenterX = coin.getScreenX() + TILE_SIZE / 2;
            int coinCenterY = coin.getScreenY() + TILE_SIZE / 2;
            double coinDistance = Math.hypot(playerCenterX - coinCenterX, playerCenterY - coinCenterY);
            if (coinDistance < 50) {
                coin.collect();
                addScore();
            }
        }

        boolean allCollected = coins.stream().allMatch(Coin::isCollected);
        if (!coins.isEmpty() && allCollected) {
            setGameOver();
            playVictorySound();
        }
    }

    private void playVictorySound() { System.out.println("Victory! All coins collected!"); }
    private void playDefeatSound() { System.out.println("Game over! Collision with monster!"); }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            if (monsters != null) {
                for (Monster monster : monsters) {
                    monster.decideMove(mazeGrid);
                    monster.updateVisualPosition();
                }
            }
            checkCollisions();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (walls != null) {
            for (Wall wall : walls) if (wall != null && wall.getWallType() == 0) wall.draw(g);
            for (Wall wall : walls) if (wall != null && wall.getWallType() == 1) wall.draw(g);
        }
        if (coins != null) for (Coin coin : coins) if (coin != null) coin.draw(g);
        if (monsters != null) for (Monster monster : monsters) if (monster != null) monster.draw(g);
        if (player != null) player.draw(g);
        drawUI(g);
    }

    private void drawUI(Graphics g) {
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(15, PANEL_HEIGHT - 50, 200, 35);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("COINS: " + score, 20, PANEL_HEIGHT - 40);
        g.drawString("TOP SCORE: " + topScore, 20, PANEL_HEIGHT - 20);

        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
            boolean allCollected = !coins.isEmpty() && coins.stream().allMatch(Coin::isCollected);
            String message = allCollected ? "You Win!" : "Game Over";
            Color messageColor = allCollected ? Color.GREEN : Color.RED;
            g.setColor(messageColor);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            FontMetrics metrics = g.getFontMetrics();
            int x = (PANEL_WIDTH - metrics.stringWidth(message)) / 2;
            int y = PANEL_HEIGHT / 2;
            g.drawString(message, x, y);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.setColor(Color.WHITE);
            String restartMessage = "Press any key to restart";
            metrics = g.getFontMetrics();
            x = (PANEL_WIDTH - metrics.stringWidth(restartMessage)) / 2;
            y = PANEL_HEIGHT / 2 + 40;
            g.drawString(restartMessage, x, y);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.YELLOW);
            String highScoreMessage = "High Score: " + topScore;
            metrics = g.getFontMetrics();
            x = (PANEL_WIDTH - metrics.stringWidth(highScoreMessage)) / 2;
            y = PANEL_HEIGHT / 2 + 70;
            g.drawString(highScoreMessage, x, y);
        }
    }
}