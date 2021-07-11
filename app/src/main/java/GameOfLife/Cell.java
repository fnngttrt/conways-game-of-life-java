package GameOfLife;

import java.awt.*;

public class Cell {
    private boolean status;
    private Point pos;

    public Cell(int x, int y) {
        status = false;
        pos = new Point(x, y);
    }

    public boolean switchStatus() {
        status = !status;
        return status;
    }

    public Point getPos() {
        return pos;
    }

    public boolean getStatus() {
        return status;
    }

    public void kill() {
        this.status = false;
    }

    public Cell revive() {
        this.status = true;
        return this;
    }

    public Cell setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public String getAsString() {
        return this.getStatus() ? "X" : " ";
    }

    public int getAsInt() {
        return this.getStatus() ? 1 : 0;
    }

}
