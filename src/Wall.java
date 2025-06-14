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
            // Load brick texture for walls
            brickImage = ImageIO.read(Wall.class.getResource("/assets/brick_wall.png"));
            // Load grass texture for background
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
        this.wallType = isWall ? 1 : 0; // 1 for brick wall, 0 for grass background
    }

    // Constructor for backward compatibility
    public Wall(int x, int y) {
        this(x, y, true); // Default to wall
    }

    @Override
    public void draw(Graphics g) {
        if (wallType == 1) {
            // Draw brick wall
            if (brickImage != null) {
                g.drawImage(brickImage, x, y, width, height, null);
            } else {
                // Fallback: draw textured brick pattern
                drawBrickPattern(g);
            }
        } else {
            // Draw grass background
            if (grassImage != null) {
                g.drawImage(grassImage, x, y, width, height, null);
            } else {
                // Fallback: draw simple grass color with texture
                drawGrassPattern(g);
            }
        }
    }

    private void drawBrickPattern(Graphics g) {
        // Create a brick-like pattern as fallback
        g.setColor(new Color(139, 69, 19)); // Brown base
        g.fillRect(x, y, width, height);

        // Add brick lines
        g.setColor(new Color(160, 82, 45));
        // Horizontal lines
        g.drawLine(x, y + height/3, x + width, y + height/3);
        g.drawLine(x, y + 2*height/3, x + width, y + 2*height/3);

        // Vertical lines (offset pattern)
        g.drawLine(x + width/2, y, x + width/2, y + height/3);
        g.drawLine(x + width/4, y + height/3, x + width/4, y + 2*height/3);
        g.drawLine(x + 3*width/4, y + height/3, x + 3*width/4, y + 2*height/3);
        g.drawLine(x + width/2, y + 2*height/3, x + width/2, y + height);

        // Add shadow effect
        g.setColor(new Color(101, 67, 33));
        g.drawRect(x, y, width-1, height-1);
    }

    private void drawGrassPattern(Graphics g) {
        // Create grass-like pattern as fallback
        g.setColor(new Color(34, 139, 34)); // Forest green base
        g.fillRect(x, y, width, height);

        // Add grass texture with random dots
        Random rand = new Random(x + y); // Seed based on position for consistency
        g.setColor(new Color(50, 205, 50)); // Lime green highlights

        for (int i = 0; i < 8; i++) {
            int dotX = x + rand.nextInt(width);
            int dotY = y + rand.nextInt(height);
            g.fillOval(dotX, dotY, 2, 2);
        }

        // Add darker grass shadows
        g.setColor(new Color(0, 100, 0));
        for (int i = 0; i < 5; i++) {
            int dotX = x + rand.nextInt(width);
            int dotY = y + rand.nextInt(height);
            g.fillOval(dotX, dotY, 1, 1);
        }
    }

    /**
     * Mengecek apakah koordinat (checkX, checkY) berada di dalam area Wall ini.
     * Hanya brick walls yang bisa menghalangi movement.
     */
    public boolean isAtPosition(int checkX, int checkY) {
        return wallType == 1 && // Only brick walls block movement
                (checkX >= x) && (checkX < x + width) &&
                (checkY >= y) && (checkY < y + height);
    }

    /**
     * Check if this wall blocks movement (only brick walls do)
     */
    public boolean isBlocking() {
        return wallType == 1;
    }

    /**
     * Get wall type (0 = grass, 1 = brick)
     */
    public int getWallType() {
        return wallType;
    }

    /**
     * Set wall type
     */
    public void setWallType(int type) {
        this.wallType = type;
        this.color = (type == 1) ? Color.GRAY : Color.GREEN;
    }
}