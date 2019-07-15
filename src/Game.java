import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;


public class Game extends Constants {

    static int gameScore = 0;
    static int[][] field = new int[FIELD_HEIGHT + 1][FIELD_WIDTH];
    static JFrame frame = new JFrame(TITLE);
    Canvas canvasPanel = new Canvas();
    static Random random = new Random();
    static Figure figure = new Figure();
    static boolean gameOver = false;

    Game() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + FIELD_DY);
        frame.setLocation(START_LOCATION, START_LOCATION);
        frame.setResizable(false);
        canvasPanel.setBackground(Color.BLACK);

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    if (e.getKeyCode() == DOWN) figure.drop();
                    if (e.getKeyCode() == UP) figure.rotate();
                    if (e.getKeyCode() == LEFT || e.getKeyCode() == RIGHT)
                        figure.move(e.getKeyCode());
                }
                canvasPanel.repaint();
            }
        });
        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        frame.setVisible(true);

        Arrays.fill(field[FIELD_HEIGHT], 1);
    }

    void go() {
        while (!gameOver) {
            try {
                Thread.sleep(ANIMATION_DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            canvasPanel.repaint();
            if (figure.hasLanded()) {
                figure.freeze();
                figure.linedUp();
                figure = new Figure();
                gameOver = figure.hitsLimit();
            } else {
                figure.descend();
            }
        }
    }
}
