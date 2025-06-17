import javax.swing.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class MazeMain extends JFrame {
    private Clip musicClip;
    private final GamePanel gamePanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MazeMain().setVisible(true));
    }

    public MazeMain() {
        setTitle("Maze Coin Collector");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel(this); // Berikan referensi frame ini ke panel
        add(gamePanel);
        pack();

        setLocationRelativeTo(null);

        setupMusic("/assets/music/fix_fix_banget.wav"); // Ganti jika nama file berbeda
        startMusic();

        setupKeyBindings();

        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gamePanel.isGameOver()) {
                    gamePanel.restartGame();
                    startMusic(); // Mulai lagi musik saat game restart
                }
            }
        });

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
    }

    private void setupMusic(String filepath) {
        try {
            URL musicURL = getClass().getResource(filepath);
            if (musicURL == null) {
                System.err.println("File musik tidak ditemukan di: " + filepath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicURL);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error saat memuat musik: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void startMusic() {
        if (musicClip != null && !musicClip.isRunning()) {
            musicClip.setFramePosition(0); // Putar dari awal
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
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
                    gamePanel.setPlayerDirection(3);
                }
            }
        });

        gamePanel.getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gamePanel.isGameOver()) {
                    gamePanel.moveBackgroundX("kiri");
                    gamePanel.setPlayerDirection(1);
                }
            }
        });

        gamePanel.getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gamePanel.isGameOver()) {
                    gamePanel.moveBackgroundY("bawah");
                    gamePanel.setPlayerDirection(0);
                }
            }
        });

        gamePanel.getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gamePanel.isGameOver()) {
                    gamePanel.moveBackgroundX("kanan");
                    gamePanel.setPlayerDirection(2);
                }
            }
        });
    }
}