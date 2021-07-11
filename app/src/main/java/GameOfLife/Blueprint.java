package GameOfLife;

public class Blueprint {
    private String name;
    private int[][] values;
    public Blueprint (String name, int[][] values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public int[][] getValues() {
        return values;
    }
}
