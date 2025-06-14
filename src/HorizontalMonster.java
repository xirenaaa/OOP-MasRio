import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class HorizontalMonster extends Monster {
    private static BufferedImage enemyImage;

    static {
        try {
            enemyImage = ImageIO.read(HorizontalMonster.class.getResource("/assets/monster/Violet_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HorizontalMonster(int gridX, int gridY, int patrolRange, int pixelSpeed) {
        super(gridX, gridY, patrolRange, pixelSpeed, Color.RED);
        dx = 1; // Mulai bergerak ke kanan
    }

    @Override
    public void draw(Graphics g) {
        if (enemyImage != null) {
            g.drawImage(enemyImage, x, y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    @Override
    public void decideMove(int[][] mazeGrid) {
        if (currentState == State.IDLE) {
            int nextGridX = gridX + dx;

            if (willCollide(nextGridX, gridY, mazeGrid) || patrolCounter >= patrolRange) {
                dx *= -1;
                patrolCounter = 0;
                nextGridX = gridX + dx;
            }

            if (!willCollide(nextGridX, gridY, mazeGrid)) {
                startMovingTo(nextGridX, gridY);
                patrolCounter++;
            }
        }
    }
}