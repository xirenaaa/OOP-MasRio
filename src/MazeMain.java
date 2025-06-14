import javax.swing.*;
import java.awt.event.*;

public class MazeMain extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MazeMain().setVisible(true);
        });
    }

    private final GamePanel gamePanel;

    public MazeMain() {
        setTitle("Maze Coin Collector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        add(gamePanel);
        pack();

        setLocationRelativeTo(null);

        setupKeyBindings();

        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gamePanel.isGameOver()) {
                    gamePanel.restartGame();
                }
            }
        });

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }

    private void setupKeyBindings() {
        KeyStroke wKey = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0);
        KeyStroke aKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
        KeyStroke sKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
        KeyStroke dKey = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0);

        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(wKey, "moveUp");
        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(aKey, "moveLeft");
        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(sKey, "moveDown");
        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(dKey, "moveRight");

        gamePanel.getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gamePanel.isGameOver()) {
                    gamePanel.moveBackgroundY("atas");
                    Player.setCurrentDirection(3);
                }
            }
        });

        gamePanel.getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gamePanel.isGameOver()) {
                    gamePanel.moveBackgroundX("kiri");
                    Player.setCurrentDirection(1);
                }
            }
        });

        gamePanel.getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gamePanel.isGameOver()) {
                    gamePanel.moveBackgroundY("bawah");
                    Player.setCurrentDirection(0);
                }
            }
        });

        gamePanel.getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gamePanel.isGameOver()) {
                    gamePanel.moveBackgroundX("kanan");
                    Player.setCurrentDirection(2);
                }
            }
        });
    }
}