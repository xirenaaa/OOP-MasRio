import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

class Coin extends GameObject {
    private int originalX, originalY;
    private int worldX, worldY; // World coordinates for the coin
    private boolean collected = false;
    private int animationCounter = 0;
    private boolean shining = false;
    private BufferedImage coinImage;

    public Coin(int worldX, int worldY) {
        super(worldX, worldY, 20 * 4, 20 * 4, Color.YELLOW);
        this.worldX = worldX;
        this.worldY = worldY;
        this.originalX = worldX;
        this.originalY = worldY;

        try {
            coinImage = ImageIO.read(getClass().getResourceAsStream("/assets/coin/coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check collision with player at world coordinates
    public void checkCollisionWithPlayer(int playerWorldX, int playerWorldY) {
        if (!collected) {
            // Check if player is close enough to collect the coin
            int distance = Math.abs(playerWorldX - worldX) + Math.abs(playerWorldY - worldY);
            if (distance < 40) { // Adjust collision distance as needed
                collected = true;
                GamePanel.addScore(); // Add to score when collected
            }
        }
    }

    // Get world coordinates
    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    // Method to draw coin at specific screen position (for camera system)
    public void drawAtPosition(Graphics2D g, int screenX, int screenY) {
        if (!collected) {
            animationCounter++;
            if (animationCounter > 20) {
                shining = !shining;
                animationCounter = 0;
            }

            if (coinImage != null) {
                g.drawImage(coinImage, screenX - width/2, screenY - height/2, width, height, null);
            } else {
                if (shining) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(new Color(255, 215, 0)); // Gold
                }
                g.fillOval(screenX - width/2, screenY - height/2, width, height);
            }
        }
    }

    public void updateAnimation() {
        // Animation logic can be added here if needed
        animationCounter++;
        if (animationCounter > 20) {
            shining = !shining;
            animationCounter = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!collected) {
            animationCounter++;
            if (animationCounter > 20) {
                shining = !shining;
                animationCounter = 0;
            }

            if (coinImage != null) {
                g.drawImage(coinImage, originalX, originalY, width, height, null);
            } else {
                if (shining) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(new Color(255, 215, 0)); // Gold
                }
                g.fillOval(originalX + 20, originalY + 20, width, height);
            }
        }
    }

    public void setOriginalX(int x) { this.originalX = x; }
    public void setOriginalY(int y) { this.originalY = y; }

    public int getOriginalX() { return originalX; }
    public int getOriginalY() { return originalY; }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    public void removeIfAt240_240() {
        if (!collected && originalX == 240 && originalY == 240) {
            collect();
            GamePanel.addScore();
            System.out.println("Koin di (240, 240) telah dihilangkan.");
        }
    }
}