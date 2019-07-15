import java.awt.*;
import java.util.ArrayList;


public class Figure extends Constants {

    private ArrayList<Block> figure = new ArrayList<Block>();
    private int[][] shape = new int[4][4];
    private int type, size, color;
    private int x = 3, y = 0;

    Figure() {
        type = Game.random.nextInt(FIGURES.length);
        size = FIGURES[type][4][0];
        color = FIGURES[type][4][1];
        if (size == 4) y = -1;
        for (int i = 0; i < size; i++) {
            System.arraycopy(FIGURES[type][i], 0, shape[i], 0, FIGURES[type][i].length);
            createFigure();
        }
    }

    void createFigure() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (shape[y][x] == 1) figure.add(new Block(x + this.x, y + this.y));
            }
        }
    }

    void move(int direction) {
        if (!hitsFrame(direction)) {
            int dx = direction - 38;
            for (Block block : figure) block.setX(block.getX() + dx);
            x += dx;
        }
    }

    private boolean hitsFrame(int direction) {
        for (Block block : figure) {
            if (direction == LEFT && (block.getX() == 0 || Game.field[block.getY()][block.getX() - 1] > 0))
                return true;
            if (direction == RIGHT && (block.getX() == Constants.FIELD_WIDTH - 1 || Game.field[block.getY()][block.getX() + 1] > 0))
                return true;
        }
        return false;
    }

    void drop() {
        while (!hasLanded()) descend();
    }

    void rotate() {
        for (int i = 0; i < size / 2; i++)
            for (int j = i; j < size - 1 - i; j++) {
                int tmp = shape[size - 1 - j][i];
                shape[size - 1 - j][i] = shape[size - 1 - i][size - 1 - j];
                shape[size - 1 - i][size - 1 - j] = shape[j][size - 1 - i];
                shape[j][size - 1 - i] = shape[i][j];
                shape[i][j] = tmp;
            }
        if (!isBlocked()) {
            figure.clear();
            createFigure();
        }
    }

    private boolean isBlocked() {
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                if (shape[y][x] == 1) {
                    if (y + this.y < 0) return true;
                    if (x + this.x < 0 || x + this.x > FIELD_WIDTH - 1) return true;
                    if (Game.field[y + this.y][x + this.x] > 0) return true;
                }
        return false;
    }

    boolean hasLanded() {
        for (Block block : figure)
            if (Game.field[block.getY() + 1][block.getX()] > 0)
                return true;
        return false;
    }

    void freeze() {
        for (Block block : figure) Game.field[block.getY()][block.getX()] = color;

    }

    static boolean linedUp() {
        int row = FIELD_HEIGHT - 1;
        int countFillRows = 0;
        while (row > 0) {
            int filled = 1;
            for (int col = 0; col < FIELD_WIDTH; col++)
                filled *= Integer.signum(Game.field[row][col]);
            if (filled > 0) {
                countFillRows++;
                for (int i = row; i > 0; i--) System.arraycopy(Game.field[i - 1], 0, Game.field[i], 0, FIELD_WIDTH);
            } else
                row--;
        }
        if (countFillRows > 0) {
            Game.gameScore += SCORES[countFillRows - 1];
            Game.frame.setTitle(TITLE + " : " + Game.gameScore);
        }
        return false;
    }

    void descend() {
        for (Block block : figure) block.setY(block.getY() + 1);
        y++;
    }

    void paint(Graphics g) {
        for (Block block : figure) block.paint(g, color);

    }

    boolean hitsLimit() {
        for (Block block : figure) if (Game.field[block.getY()][block.getX()] > 0) return true;
        return false;
    }
}
