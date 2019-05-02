package ninja.oscaz.blockpuzzle.error;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameError {

    private static final List<String> queuedErrors = new ArrayList<>();

    public static void displayGameError(String message) {
        queuedErrors.add(message);
        BlockPuzzle.getInstance().setErrorHalt(true);
    }

    public static void drawError() {
        displayErrorBox(queuedErrors.get(0));
    }

    public static void mouseReleased() {
        if (!(BlockPuzzle.getInstance().mouseX >= 300)) return;
        if (!(BlockPuzzle.getInstance().mouseX <= 340)) return;
        if (!(BlockPuzzle.getInstance().mouseY >= 360)) return;
        if (!(BlockPuzzle.getInstance().mouseY <= 380)) return;
        queuedErrors.remove(0);
        if (!(queuedErrors.size() > 0)) BlockPuzzle.getInstance().setErrorHalt(false);
    }

    private static void displayErrorBox(String message) {
        BlockPuzzle.getInstance().fill(70.0f);
        BlockPuzzle.getInstance().rect(240, 240, 160, 160, 5);
        BlockPuzzle.getInstance().fill(Color.WHITE.getRGB());
        BlockPuzzle.getInstance().text(message, 280, 260, 100, 120);
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("ErrorSign")), 246, 260, 32, 32);
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("ErrorOk")), 300, 360, 40, 20);
    }

}
