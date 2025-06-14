import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// HorizontalMonster class with image
class HorizontalMonster extends Monster {
    private static BufferedImage enemyImage;

    static {
        try {
            // Ganti path sesuai dengan lokasi file gambar
            enemyImage = ImageIO.read(HorizontalMonster.class.getResource("/assets/monster/Violet_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HorizontalMonster(int x, int y) {
        super(x, y, Color.RED);
        dx = 1; // Start moving right
    }

    @Override
    public void draw(Graphics g) {
        if (enemyImage != null) {
            // Draw monster dengan scaling 4x untuk konsistensi dengan sistem koordinat
            g.drawImage(enemyImage, x * 4, y * 4, width * 4, height * 4, null);
        } else {
            // Fallback: gambar default jika image gagal dimuat
            g.setColor(Color.RED);
            g.fillRect(x * 4, y * 4, width * 4, height * 4);
        }
    }

    @Override
    public void move(Wall[] walls) {
        if (!shouldMove()) {
            return;
        }

        int newX = x + dx * speed;

        // Check collision dengan walls
        if (willCollideWithWall(walls, newX, y)) {
            dx *= -1; // Balik arah jika menabrak tembok
            newX = x + dx * speed;
        }

        // Update posisi
        x = newX;

        // Batasi monster agar tidak keluar dari area game
        if (x < 0) {
            x = 0;
            dx = 1; // Paksakan bergerak ke kanan
        } else if (x > 600) { // Sesuaikan dengan lebar maze
            x = 600;
            dx = -1; // Paksakan bergerak ke kiri
        }
    }
}