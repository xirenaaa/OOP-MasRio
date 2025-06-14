import java.awt.*;

abstract class Monster extends GameObject {
    protected enum State {
        IDLE,
        MOVING
    }
    protected State currentState = State.IDLE;

    // Posisi Logis (di grid mana)
    protected int gridX, gridY;

    // Arah pergerakan dalam grid
    protected int dx = 0;
    protected int dy = 0;

    // Posisi Visual (koordinat piksel tujuan) dan Kecepatan
    protected int targetX, targetY;
    protected int pixelSpeed;

    // Variabel untuk patroli
    protected int patrolRange;
    protected int patrolCounter = 0;

    // PERBAIKAN: Variabel baru untuk menyimpan tujuan grid
    protected int targetGridX, targetGridY;

    public Monster(int gridX, int gridY, int patrolRange, int pixelSpeed, Color color) {
        // Inisialisasi posisi visual awal berdasarkan posisi grid
        super(gridX * 80, gridY * 80, 80, 80, color);
        this.gridX = gridX;
        this.gridY = gridY;

        // Inisialisasi target awal
        this.targetX = this.x;
        this.targetY = this.y;
        this.targetGridX = this.gridX;
        this.targetGridY = this.gridY;

        this.patrolRange = patrolRange;
        this.pixelSpeed = pixelSpeed;
    }

    public abstract void decideMove(int[][] mazeGrid);

    public void updateVisualPosition() {
        if (currentState == State.MOVING) {
            if (x < targetX) x = Math.min(x + pixelSpeed, targetX);
            else if (x > targetX) x = Math.max(x - pixelSpeed, targetX);

            if (y < targetY) y = Math.min(y + pixelSpeed, targetY);
            else if (y > targetY) y = Math.max(y - pixelSpeed, targetY);

            if (x == targetX && y == targetY) {
                currentState = State.IDLE;
                // PERBAIKAN: Update posisi logis dari target yang tersimpan, bukan dari perhitungan piksel.
                gridX = targetGridX;
                gridY = targetGridY;
            }
        }
    }

    protected void startMovingTo(int nextGridX, int nextGridY) {
        // Simpan tujuan grid
        this.targetGridX = nextGridX;
        this.targetGridY = nextGridY;

        // Hitung posisi piksel tujuan dengan mempertahankan offset saat ini
        int currentOffsetX = x - (gridX * 80);
        int currentOffsetY = y - (gridY * 80);
        targetX = (nextGridX * 80) + currentOffsetX;
        targetY = (nextGridY * 80) + currentOffsetY;

        currentState = State.MOVING;
    }

    protected boolean willCollide(int nextGridX, int nextGridY, int[][] mazeGrid) {
        if (nextGridY < 0 || nextGridY >= mazeGrid.length || nextGridX < 0 || nextGridX >= mazeGrid[0].length) {
            return true;
        }
        return mazeGrid[nextGridY][nextGridX] == 1;
    }

    public void shiftVisualPosition(int moveX, int moveY) {
        this.x += moveX;
        this.y += moveY;
        this.targetX += moveX;
        this.targetY += moveY;
    }

    @Override
    public abstract void draw(Graphics g);
}