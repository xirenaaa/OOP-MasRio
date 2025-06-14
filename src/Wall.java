import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

class Wall extends GameObject {
    private static BufferedImage brickImage;
    private static BufferedImage grassImage;
    private int wallType; // 0 = grass background, 1 = brick wall

    static {
        try {
            brickImage = ImageIO.read(Wall.class.getResource("/assets/brick_wall.png"));
            grassImage = ImageIO.read(Wall.class.getResource("/assets/grass_background.png"));

            System.out.println("Wall textures loaded successfully");
        } catch (IOException e) {
            System.err.println("Error loading wall textures: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error loading textures: " + e.getMessage());
        }
    }

    public Wall(int x, int y, boolean isWall) {
        super(x, y, 20 * 4, 20 * 4, isWall ? Color.GRAY : Color.GREEN);
        this.wallType = isWall ? 1 : 0;
    }

    public Wall(int x, int y) {
        this(x, y, true);
    }

    @Override
    public void draw(Graphics g) {
        if (wallType == 1) {
            if (brickImage != null) {
                g.drawImage(brickImage, x, y, width, height, null);
            } else {
                drawBrickPattern(g);
            }
        } else {
            if (grassImage != null) {
                g.drawImage(grassImage, x, y, width, height, null);
            } else {
                drawGrassPattern(g);
            }
        }
    }

    private void drawBrickPattern(Graphics g) {
        g.setColor(new Color(139, 69, 19));
        g.fillRect(x, y, width, height);
        g.setColor(new Color(160, 82, 45));
        g.drawLine(x, y + height/3, x + width, y + height/3);
        g.drawLine(x, y + 2*height/3, x + width, y + 2*height/3);
        g.drawLine(x + width/2, y, x + width/2, y + height/3);
        g.drawLine(x + width/4, y + height/3, x + width/4, y + 2*height/3);
        g.drawLine(x + 3*width/4, y + height/3, x + 3*width/4, y + 2*height/3);
        g.drawLine(x + width/2, y + 2*height/3, x + width/2, y + height);
        g.setColor(new Color(101, 67, 33));
        g.drawRect(x, y, width-1, height-1);
    }

    private void drawGrassPattern(Graphics g) {
        g.setColor(new Color(34, 139, 34));
        g.fillRect(x, y, width, height);
        Random rand = new Random(x + y);
        g.setColor(new Color(50, 205, 50));

        for (int i = 0; i < 8; i++) {
            int dotX = x + rand.nextInt(width);
            int dotY = y + rand.nextInt(height);
            g.fillOval(dotX, dotY, 2, 2);
        }

        g.setColor(new Color(0, 100, 0));
        for (int i = 0; i < 5; i++) {
            int dotX = x + rand.nextInt(width);
            int dotY = y + rand.nextInt(height);
            g.fillOval(dotX, dotY, 1, 1);
        }
    }

    public boolean isAtPosition(int checkX, int checkY) {
        return wallType == 1 &&
                (checkX >= x) && (checkX < x + width) &&
                (checkY >= y) && (checkY < y + height);
    }

    public boolean isBlocking() {
        return wallType == 1;
    }

    public int getWallType() {
        return wallType;
    }

    public void setWallType(int type) {
        this.wallType = type;
        this.color = (type == 1) ? Color.GRAY : Color.GREEN;
    }
}