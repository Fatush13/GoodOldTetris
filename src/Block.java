import java.awt.*;

public class Block extends Constants {

    private int x, y;

    public Block(int x, int y) {
        setX(x);
        setY(y);
    }

    void setY(int y) {
        this.y = y;
    }

    void setX(int x) {
        this.x = x;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void paint(Graphics g, int color) {
        g.setColor(new Color(color));
        g.drawRoundRect(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE - 2,
                BLOCK_SIZE - 2, ARC_RADIUS, ARC_RADIUS);
    }
}
