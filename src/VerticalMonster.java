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
            e.printStackTrace();
        }
    }

    public VerticalMonster(int x, int y) {
        super(x, y, Color.GREEN); // warna tidak terlalu penting sekarang
        dy = 1; // Mulai bergerak ke bawah
    }

    @Override
    public void draw(Graphics g) {
        if (enemyImage != null) {
            g.drawImage(enemyImage, x * 4, y * 4, width * 4, height * 4, null);
        } else {
            // Fallback jika gambar gagal dimuat, tetap pakai ukuran dan posisi sama
            g.setColor(Color.GREEN);
            g.fillRect(x * 4, y * 4, width * 4, height * 4);
            g.setColor(Color.BLACK);
            g.drawString("M", x * 4 + 5, y * 4 + 15); // Tulis huruf "M" biar jelas
        }

        if (enemyImage == null) {
            System.out.println("❌ Gagal load image: null result");
        }
    }

    public void addVerticalMonsterY(){
        y += 20;
    };

    public void reduceVerticalMonsterY() {
        y -= 20;
    }

    public void addVerticalMonsterX(){
        x -= 20;
    };

    public void reduceVerticalMonsterX() {
        x += 20;
    }



    @Override
    public void move(Wall[] walls) {
        if (!shouldMove()) {
            return;
        }

//        int newY = y + dy * speed;
//
//        if (willCollideWithWall(walls, x, newY)) {
//            dy *= -1;
//            newY = y + dy * speed;
//        }

//        y = newY;
    }
}
