import java.awt.*;

// Vertical monster class
class VerticalMonster extends Monster {
    public VerticalMonster(int x, int y) {
        super(x, y, Color.GREEN);
        dy = 1; // Start moving down
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // Add more Mario-like enemy features
        g.setColor(Color.WHITE);
        g.fillOval(x + 5, y + 5, 5, 5);
        g.fillOval(x + 12, y + 5, 5, 5);

        // Add shell-like pattern
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x + 5, y + 10, width - 10, height - 15);
    }

    @Override
    public void move(Wall[] walls) {
        if (!shouldMove()) {
            return;
        }

        int newY = y + dy * speed;

        if (willCollideWithWall(walls, x, newY)) {
            // Change direction if hitting a wall
            dy *= -1;
            newY = y + dy * speed;
        }

        y = newY;
    }
}