import java.awt.*;
import java.util.Random;

// Wall class
class Wall extends GameObject {
    private boolean isBrick;

    public Wall(int x, int y) {
        super(x, y, 20, 20, Color.BLUE);
        // Randomly determine if this is a brick wall or a solid wall
        Random rand = new Random();
        isBrick = rand.nextBoolean();
    }

    @Override
    public void draw(Graphics g) {
        if (isBrick) {
            // Draw Mario-style brick
            g.setColor(new Color(205, 133, 63)); // Brown
            g.fillRect(x, y, width, height);

            // Add brick pattern
            g.setColor(new Color(165, 93, 23)); // Darker brown
            g.drawLine(x, y + height/2, x + width, y + height/2);
            g.drawLine(x + width/2, y, x + width/2, y + height);
        } else {
            // Draw solid block
            g.setColor(new Color(0, 0, 139)); // Dark blue
            g.fillRect(x, y, width, height);

            // Add block details
            g.setColor(new Color(0, 0, 225)); // Brighter blue
            g.fillRect(x + 2, y + 2, width - 4, height - 4);
        }
    }
}
