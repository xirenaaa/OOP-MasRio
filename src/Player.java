import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class Player extends GameObject {
    private BufferedImage[][] animations;
    private int currentDirection = 0; // TIDAK LAGI STATIC
    private int frame = 0;

    private long lastFrameTime = 0;
    private final long frameDuration = 100_000_000;

    private int targetX, targetY;

    public Player(int x, int y) {
        super(x, y, 80, 80, Color.RED);
        animations = new BufferedImage[4][4];
        loadAnimations();
        targetX = x;
        targetY = y;
    }

    private void loadAnimations() {
        String[] directions = {"bawah", "kiri", "kanan", "atas"};
        try {
            for (int d = 0; d < 4; d++) {
                for (int f = 1; f <= 4; f++) {
                    String path = "/assets/paladin/" + directions[d] + f + ".png";
                    animations[d][f - 1] = ImageIO.read(getClass().getResource(path));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentDirection(int number){ // TIDAK LAGI STATIC
        this.currentDirection = number;
    }

    @Override
    public void draw(Graphics g) {
        long currentTime = System.nanoTime();
        if (currentTime - lastFrameTime > frameDuration) {
            frame = (frame + 1) % 4;
            lastFrameTime = currentTime;
        }

        BufferedImage img = animations[currentDirection][frame];
        if (img != null) {
            g.drawImage(img, x, y, width, height, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }
    }


    public Point getCenterPosition() {
        return new Point(x + width/2, y + height/2);
    }

    public Rectangle getCollisionBounds() {
        int offset = 10;
        return new Rectangle(x + offset, y + offset, width - 2*offset, height - 2*offset);
    }
}