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
            g.drawImage(enemyImage, x*4, y*4, width*4, height*4, null);
        } else {
            // Fallback: gambar default jika image gagal dimuat
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    @Override
    public void move(Wall[] walls) {
        if (!shouldMove()) {
            return;
        }

//        int newX = x + dx * speed;

//        if (willCollideWithWall(walls, newX, y)) {
//            dx *= -1;
//            newX = x + dx * speed;
//        }

//        x = newX;
    }
}
