import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

class Wall extends GameObject {
    private boolean isBrick;
    private static BufferedImage brickImage;
    private static BufferedImage solidImage;

    static {
        try {
            brickImage = ImageIO.read(Wall.class.getResource("/assets/bener2.png"));
            solidImage = ImageIO.read(Wall.class.getResource("/assets/bener3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Wall(int x, int y) {
        super(x, y, 20 * 4, 20 * 4, Color.BLUE);
        Random rand = new Random();
        isBrick = rand.nextBoolean();
    }

    @Override
    public void draw(Graphics g) {
        if (isBrick && brickImage != null) {
            g.drawImage(brickImage, x, y, width, height, null);
        } else if (!isBrick && solidImage != null) {
            g.drawImage(brickImage, x, y, width, height, null);
        } else {
            // Fallback if image not loaded
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    /**
     * Mengecek apakah koordinat (checkX, checkY) berada di dalam area Wall ini.
     * Misal, untuk deteksi tabrakan.
     */
    public boolean isAtPosition(int checkX, int checkY) {
        return (checkX >= x) && (checkX < x + width) &&
                (checkY >= y) && (checkY < y + height);
    }
}