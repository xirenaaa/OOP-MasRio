import java.awt.*;

// Base class for all game objects
abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected Color color;

    public GameObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void draw(Graphics g);

    public boolean collidesWith(GameObject other) {
        return getBounds().intersects(other.getBounds());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}