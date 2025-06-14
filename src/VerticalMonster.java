import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// VerticalMonster class with image
class VerticalMonster extends Monster {
    private static BufferedImage enemyImage;

    static {
        try {
            enemyImage = ImageIO.read(VerticalMonster.class.getResource("/assets/monster/Violet_1.png"));
            if (enemyImage == null) {
                System.out.println("❌ Gagal load image: null result");
            }
        } catch (IOException e) {
            System.err.println("Error loading monster image: " + e.getMessage());
        }
    }

    public VerticalMonster(int x, int y) {
        super(x, y, Color.GREEN);
        dy = 1; // Mulai bergerak ke bawah
    }

    @Override
    public void draw(Graphics g) {
        if (enemyImage != null) {
            // Draw monster dengan scaling 4x untuk konsistensi dengan sistem koordinat
            g.drawImage(enemyImage, x * 4, y * 4, width * 4, height * 4, null);
        } else {
            // Fallback: gambar default jika image gagal dimuat
            g.setColor(Color.GREEN);
            g.fillRect(x * 4, y * 4, width * 4, height * 4);
        }
    }

    @Override
    public void move(Wall[] walls) {
        if (!shouldMove()) {
            return;
        }

        int newY = y + dy * speed;

        // Check collision dengan walls
        if (willCollideWithWall(walls, x, newY)) {
            dy *= -1; // Balik arah jika menabrak tembok
            newY = y + dy * speed;
        }

        // Update posisi
        y = newY;

        // Batasi monster agar tidak keluar dari area game
        if (y < 0) {
            y = 0;
            dy = 1; // Paksakan bergerak ke bawah
        } else if (y > 600) { // Sesuaikan dengan tinggi maze
            y = 600;
            dy = -1; // Paksakan bergerak ke atas
        }
    }
}