import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class VerticalMonster extends Monster {
    private static BufferedImage enemyImage;

    static {
        try {
            enemyImage = ImageIO.read(VerticalMonster.class.getResource("/assets/monster/Violet_1.png"));
        } catch (IOException e) {
            System.err.println("Error loading monster image: " + e.getMessage());
        }
    }

    public VerticalMonster(int gridX, int gridY, int patrolRange, int pixelSpeed) {
        super(gridX, gridY, patrolRange, pixelSpeed, Color.GREEN);
        dy = 1; // Mulai bergerak ke bawah
    }

    @Override
    public void draw(Graphics g) {
        if (enemyImage != null) {
            g.drawImage(enemyImage, x, y, width, height, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(x, y, width, height);
        }
    }

    @Override
    public void decideMove(int[][] mazeGrid) {
        if (currentState == State.IDLE) {
            int nextGridY = gridY + dy;

            if (willCollide(gridX, nextGridY, mazeGrid) || patrolCounter >= patrolRange) {
                dy *= -1;
                patrolCounter = 0;
                nextGridY = gridY + dy;
            }

            if (!willCollide(gridX, nextGridY, mazeGrid)) {
                startMovingTo(gridX, nextGridY);
                patrolCounter++;
            }
        }
    }
}