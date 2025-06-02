import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

// Coin class
class Coin extends GameObject {
    private int originalX, originalY;
//    private int screenX, screenY;

    private boolean collected = false;
    private int animationCounter = 0;
    private boolean shining = false;

    private BufferedImage coinImage;

    public Coin(int x, int y) {
        super(originalX = x, originalY = y, 20 * 4, 20 * 4, Color.YELLOW);
//        this.screenX = x;
//        this.screenY = y;

        try {
            coinImage = ImageIO.read(getClass().getResourceAsStream("/assets/coin/coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateAnimation(){

    };

    @Override
    public void draw(Graphics g) {
        if (!collected) {
            // Animate the coin to make it shine occasionally
            animationCounter++;
            if (animationCounter > 20) {
                shining = !shining;
                animationCounter = 0;
            }

            if (coinImage != null) {
                g.drawImage(coinImage, originalX, originalY, width, height, null);
            } else {
                // fallback warna kalau gambar gagal dimuat
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
            collect(); // menandai koin sebagai dikumpulkan
            GamePanel.addScore();
            System.out.println("Koin di (240, 240) telah dihilangkan.");
        }
    }
}