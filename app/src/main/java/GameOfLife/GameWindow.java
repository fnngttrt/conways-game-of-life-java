package GameOfLife;

import controlP5.*;
import processing.core.PApplet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GameWindow extends PApplet {
    int x = 50;
    int y = 50;
    int width = 900;
    int height = 900;
    int sidebarwidth = 200;
    int wpc = width / x;
    int hpc = height / y;
    boolean simulationActive = false;
    int[][][] boundaries = new int[x][y][4];
    Core core = new Core(x, y);
    ControlP5 cp5;
    DropdownList selectedBlueprint;
    Textarea console;
    List<String> console_log = new ArrayList<>();
    long renderedFrames = 0;

    public static void main(String[] args) {
        String[] appArgs = {"Game Of Life"};
        GameWindow mySketch = new GameWindow();
        PApplet.runSketch(appArgs, mySketch);
    }

    public void setup() {
        cp5 = new ControlP5(this);
        generateBoundaries();
        surface.setTitle("John Conways Game of Life by Fynn Goettert");
        addUIElements();
        console_log.add("Window setup complete");
    }
    public void settings() {
        size(width + sidebarwidth, height);
    }

    public void addUIElements() {
        cp5.addButton("Start Simulation").setPosition(920, 50).setSize(160, 30);
        cp5.addButton("Stop Simulation").setPosition(920, 100).setSize(160, 30);
        cp5.addSlider("Speed").setPosition(920, 150).setSize(130, 30).setRange(60, 1).setNumberOfTickMarks(60).setValue(10).setTriggerEvent(Slider.RELEASE);
        cp5.addButton("Next Step").setPosition(920, 200).setSize(160, 30);
        cp5.addButton("Reset Board").setPosition(920, 250).setSize(160, 30);
        selectedBlueprint = cp5.addDropdownList("Available blueprints").setItems(core.getBluePrintNames()).setPosition(920, 440).setSize(160, 100).setOpen(false);
        cp5.addButton("Random").setPosition(920, 300).setSize(160, 30);
        cp5.addButton("Save blueprint").setPosition(920, 350).setSize(160, 30);
        cp5.addButton("Load blueprint").setPosition(920, 400).setSize(160, 30);
        console = cp5.addTextarea("console").setPosition(900, 570).setSize(200,380).setFont(createFont("arial",12)).setLineHeight(14).setText(String.join("\n", console_log.toArray(new String[0]))).setColorBackground(color(1));
    }

    public void draw() {
        background(color(150, 150, 150));
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                fill(core.getSingleCell(i, j).getStatus() ? color(0, 0, 0) : color(255, 255, 255));
                rect(wpc * i, hpc * j, wpc, hpc);
            }
        }
        if (simulationActive && renderedFrames % cp5.get("Speed").getValue() == 0) {
            core.calculateStep();
        }
        fill(1);
        text("Generation: " + core.getGeneration(), 920, 20);
        text("Rendered Frames: " + renderedFrames, 920, 40);
        text("Blueprints: ", 920, 310);
        renderedFrames++;
    }

    public void logThis(String a) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
        console_log.add(0, "[" + ft.format(dNow) + "] " + a);
        console.setText(String.join("\n", console_log.toArray(new String[0])));
    }

    public void mousePressed() {
        checkIfBoxClicked(mouseX, mouseY);
    }

    public void mouseDragged() {
        checkIfBoxClicked(mouseX, mouseY);
    }

    private void checkIfBoxClicked(int x, int y) {
        for (int i = 0; i < boundaries.length; i++) {
            for (int j = 0; j < boundaries[i].length; j++) {
                if (y > boundaries[i][j][0] && y < boundaries[i][j][1] && x > boundaries[i][j][2] && x < boundaries[i][j][3]) {
                    core.switchCell(j, i);
                }
            }
        }
    }

    public void controlEvent(CallbackEvent event) {
        if (event.getAction() == ControlP5.ACTION_CLICK) {
            switch (event.getController().getAddress()) {
                case "/Start Simulation" -> startSimulation();
                case "/Stop Simulation" -> stopSimulation();
                case "/Reset Board" -> resetBoard();
                case "/Save blueprint" -> newBlueprint();
                case "/Next Step" -> core.calculateStep();
                case "/Load blueprint" -> applyBlueprint();
                case "/Random" -> noise();
                case "/Speed" -> logThis("Speed set to ~" + Math.round(60 / cp5.get("Speed").getValue()) + " GpS");
            }
        }
    }

    private void noise() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (Math.random() <= 0.5d) {
                    core.switchCell(i, j);
                }
            }
        }
    }
    private void startSimulation() {
        simulationActive = true;
        logThis("Simulation started with " + (60 / cp5.get("Speed").getValue()) + " Generations per Second");
    }

    private void stopSimulation() {
        simulationActive = false;
        logThis("Simulation stopped");
    }

    private void newBlueprint() {
        String name = core.writeBlueprint();
        if (name == null) {
            logThis("Board is empty!");
            return;
        }
        logThis("New Blueprint saved as: " + name);
        selectedBlueprint.setItems(core.getBluePrintNames());
    }

    private void applyBlueprint() {
        String name = selectedBlueprint.getLabel();
        if (name.equals("Available blueprints")) {
            return;
        }
        logThis("Applied Blueprint: \"" + name + "\"");
        core.applyBlueprint(Arrays.asList(core.getBluePrintNames()).indexOf(name));
    }

    private void resetBoard() {
        logThis("Board resetted");
        core.resetBoard();
    }

    private void generateBoundaries() {
        int ymin, ymax, xmin, xmax;
        for (int i = 0; i < x; i++) {
            ymin = i * hpc;
            ymax = (int) (i + 1) * hpc;
            for (int j = 0; j < y; j++) {
                xmin = j * wpc;
                xmax = (j + 1) * wpc;
                boundaries[i][j] = new int[] { ymin, ymax, xmin, xmax};
            }
        }
    }

}
