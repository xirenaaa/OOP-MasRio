import java.awt.*;

// Coin class
class Coin extends GameObject {
    private boolean collected = false;
    private int animationCounter = 0;
    private boolean shining = false;

    public Coin(int x, int y) {
        super(x, y, 10, 10, Color.YELLOW);
    }

    @Override
    public void draw(Graphics g) {
        if (!collected) {
            // Animate the coin to make it shine occasionally
            animationCounter++;
            if (animationCounter > 20) {
                shining = !shining;
                animationCounter = 0;
            }

            if (shining) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(new Color(255, 215, 0)); // Gold
            }

            // Draw a Mario-like coin
            g.fillOval(x + 5, y + 5, width, height);

            // Add shine effect
            g.setColor(new Color(255, 255, 200));
            g.fillOval(x + 7, y + 7, 3, 3);
        }
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }
}
