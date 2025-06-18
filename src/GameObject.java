import java.awt.*;

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

    // Method collidesWith(GameObject other) dihapus karena tidak pernah digunakan.
    // Logika kolisi diimplementasikan secara spesifik di GamePanel.

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}