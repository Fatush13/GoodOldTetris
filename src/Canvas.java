import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < Constants.FIELD_WIDTH; x++)
            for (int y = 0; y < Constants.FIELD_HEIGHT; y++)
                if (Game.field[y][x] > 0) {
                    g.setColor(new Color(Game.field[y][x]));
                    g.fill3DRect(x * Constants.BLOCK_SIZE + 1, y * Constants.BLOCK_SIZE + 1,
                            Constants.BLOCK_SIZE - 1, Constants.BLOCK_SIZE - 1, true);
                }
        if (Game.gameOver) {
            g.setColor(Color.white);
            for (int y = 0; y < Constants.GAME_OVER.length; y++)
                for (int x = 0; x < Constants.GAME_OVER[y].length; x++)
                    if (Constants.GAME_OVER[y][x] == 1) g.fill3DRect(x * 11 + 18, y * 11 + 160, 10, 10, true);
        } else
            Game.figure.paint(g);
    }
}
