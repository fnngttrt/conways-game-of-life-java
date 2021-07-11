package GameOfLife;

import java.util.List;

public interface GameLogic {
    String toString();
    void resetBoard();
    Cell getSingleCell(int x, int y);
    int getGeneration();
    void switchCell(int x, int y);
    void switchCell(int[] x);
    String[] getBluePrintNames();
    void applyBlueprint(int blueprintId);
    String writeBlueprint();
    void calculateStep();
    int getNeighborCount(int x, int y);
    List<Cell> getAliveCells();
}
