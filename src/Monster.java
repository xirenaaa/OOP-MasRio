import java.awt.*;

// Monster base class
abstract class Monster extends GameObject {
    protected int dx = 0;
    protected int dy = 0;
    protected int speed = 2; // Reduced speed for better gameplay
    protected int moveCounter = 0;
    protected int moveDelay = 5; // Slower movement for better collision detection

    public Monster(int x, int y, Color color) {
        super(x, y, 20, 20, color); // Base size 20x20, will be scaled 4x when drawn
    }

    public abstract void move(Wall[] walls);

    public boolean shouldMove() {
        moveCounter++;
        if (moveCounter >= moveDelay) {
            moveCounter = 0;
            return true;
        }
        return false;
    }

    protected boolean willCollideWithWall(Wall[] walls, int newX, int newY) {
        // Check collision dengan koordinat yang tepat
        // Monster berukuran 20x20 tapi di-scale 4x saat digambar
        Rectangle newBounds = new Rectangle(newX * 4, newY * 4, width * 4, height * 4);

        for (Wall wall : walls) {
            if (wall.isBlocking() && newBounds.intersects(wall.getBounds())) {
                return true;
            }
        }
        return false;
    }

    // Methods for world movement (when camera moves)
    public void addVerticalMonsterY() {
        y += 20; // Move down in world coordinates
    }

    public void addVerticalMonsterX() {
        x -= 20; // Move left in world coordinates (opposite of camera)
    }

    public void reduceVerticalMonsterY() {
        y -= 20; // Move up in world coordinates
    }

    public void reduceVerticalMonsterX() {
        x += 20; // Move right in world coordinates (opposite of camera)
    }

    // Override getBounds to return scaled bounds for proper collision detection
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x * 4, y * 4, width * 4, height * 4);
    }

    // Method untuk mendapatkan center position dalam screen coordinates
    public Point getCenterPosition() {
        int centerX = (x * 4) + (width * 4) / 2;
        int centerY = (y * 4) + (height * 4) / 2;
        return new Point(centerX, centerY);
    }

    // Method untuk debugging
    public void printPosition() {
        System.out.println("Monster position - World: (" + x + "," + y + "), Screen: (" + (x*4) + "," + (y*4) + ")");
    }
}