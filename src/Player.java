import java.awt.*;

// Player class
class Player extends GameObject {
    private boolean facingRight = true;
    private int animationCounter = 0;

    public Player(int x, int y) {
        super(x, y, 20, 20, Color.RED);
    }

    @Override
    public void draw(Graphics g) {
        // Mario-like character
        g.setColor(color);
        g.fillRect(x, y, width, height);

        // Hat
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height/3);

        // Face
        g.setColor(new Color(255, 200, 150));
        g.fillRect(x, y + height/3, width, height/3);

        // Eyes
        g.setColor(Color.WHITE);
        if (facingRight) {
            g.fillOval(x + width/2, y + height/3, width/4, height/4);
        } else {
            g.fillOval(x + width/4, y + height/3, width/4, height/4);
        }

        // Overall straps
        g.setColor(Color.BLUE);
        g.fillRect(x + width/4, y + 2*height/3, width/6, height/3);
        g.fillRect(x + 2*width/3, y + 2*height/3, width/6, height/3);

        // Update animation counter
        animationCounter++;
    }

    public void move(int dx, int dy, Wall[] walls) {
        // Update facing direction
        if (dx > 0) facingRight = true;
        else if (dx < 0) facingRight = false;

        int newX = x + dx * width;
        int newY = y + dy * height;

        // Create a temporary player at the new position to check for collisions
        Player temp = new Player(newX, newY);

        // Check if the new position collides with any wall
        boolean collided = false;
        for (Wall wall : walls) {
            if (temp.collidesWith(wall)) {
                collided = true;
                break;
            }
        }

        // Only update position if there's no collision
        if (!collided) {
            x = newX;
            y = newY;
        }
    }
}
