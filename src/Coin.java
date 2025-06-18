import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

class Coin extends GameObject {
    // Variabel 'originalX/Y' diganti nama menjadi 'screenX/Y' untuk merefleksikan
    // fungsinya sebagai koordinat render di layar, yang berubah saat peta bergerak.
    private int screenX, screenY;
    private int worldX, worldY; // Posisi absolut di dunia game, tidak berubah.

    private boolean collected = false;
    private int animationCounter = 0;
    private boolean shining = false;
    private BufferedImage coinImage;

    public Coin(int worldX, int worldY) {
        // Posisi awal di layar sama dengan posisi dunia.
        super(worldX, worldY, 80, 80, Color.YELLOW);
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenX = worldX;
        this.screenY = worldY;

        try {
            coinImage = ImageIO.read(getClass().getResourceAsStream("/assets/coin/coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Semua method yang tidak terpakai seperti checkCollisionWithPlayer, getWorldX/Y,
    // drawAtPosition, updateAnimation, dll. telah dihapus.

    @Override
    public void draw(Graphics g) {
        if (!collected) {
            animationCounter++;
            if (animationCounter > 20) {
                shining = !shining;
                animationCounter = 0;
            }

            if (coinImage != null) {
                // Menggambar menggunakan screenX dan screenY
                g.drawImage(coinImage, screenX, screenY, width, height, null);
            } else {
                if (shining) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(new Color(255, 215, 0));
                }
                g.fillOval(screenX, screenY, width, height);
            }
        }
    }

    // Setter dan Getter untuk posisi layar (screenX/Y)
    public void setScreenX(int x) { this.screenX = x; }
    public void setScreenY(int y) { this.screenY = y; }
    public int getScreenX() { return screenX; }
    public int getScreenY() { return screenY; }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }
}