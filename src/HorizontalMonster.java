import java.awt.*;

// Horizontal monster class
class HorizontalMonster extends Monster {
    public HorizontalMonster(int x, int y) {
        super(x, y, Color.RED);
        dx = 1; // Start moving right
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // Add more Mario-like enemy features
        g.setColor(Color.WHITE);
        g.fillOval(x + 5, y + 5, 5, 5);
        g.fillOval(x + 12, y + 5, 5, 5);

        // Add simple legs
        g.setColor(Color.BLACK);
        g.fillRect(x + 3, y + height - 5, 5, 5);
        g.fillRect(x + width - 8, y + height - 5, 5, 5);
    }

    @Override
    public void move(Wall[] walls) {
        if (!shouldMove()) {
            return;
        }

        int newX = x + dx * speed;

        if (willCollideWithWall(walls, newX, y)) {
            // Change direction if hitting a wall
            dx *= -1;
            newX = x + dx * speed;
        }

        x = newX;
    }
}
