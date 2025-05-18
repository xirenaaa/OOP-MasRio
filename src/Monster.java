import java.awt.*;

// Monster base class
abstract class Monster extends GameObject {
    protected int dx = 0;
    protected int dy = 0;
    protected int speed = 5; // Reduced speed for slower movement
    protected int moveCounter = 0;
    protected int moveDelay = 3; // Only move every few game ticks

    public Monster(int x, int y, Color color) {
        super(x, y, 20, 20, color);
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
        Rectangle newBounds = new Rectangle(newX, newY, width, height);
        for (Wall wall : walls) {
            if (newBounds.intersects(wall.getBounds())) {
                return true;
            }
        }
        return false;
    }
}
