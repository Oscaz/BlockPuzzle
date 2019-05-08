package ninja.oscaz.blockpuzzle.menu;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.input.click.ClickHandler;
import ninja.oscaz.blockpuzzle.level.GameLevel;
import processing.core.PConstants;
import processing.core.PImage;

import java.time.Duration;
import java.time.Instant;

public class LevelCompleteMenu extends Menu {

    public LevelCompleteMenu() {
        super(MenuState.LEVELCOMPLETE);
    }

    @Override
    public void drawInit() {
        this.doRedraw = true;
        try {
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 400, 460, 560, 560, this.getClass().getMethod("continueButtonClicked")
            );
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawKill() {
        ClickHandler.getInstance().deregisterListener(this.getMenuState());
    }

    boolean doRedraw = true;

    @Override
    public void drawMenu() {
        if (!doRedraw) return;
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("LevelComplete")), 80, 80, 480, 480);
        BlockPuzzle.getInstance().textSize(25);
        BlockPuzzle.getInstance().fill(50f);
        BlockPuzzle.getInstance().text("Stats:", 280, 190, 80, 40);
        BlockPuzzle.getInstance().textSize(15);
        GameMenu gameMenu = (GameMenu) MenuState.INGAME.getMenu();
        GameLevel lastLevel = gameMenu.getCurrentLevel();
        Duration duration = Duration.between(lastLevel.getGameStart(), Instant.now());
        BlockPuzzle.getInstance().text("Time: " + this.getTimeString(duration), 260,  240,120, 20);
        BlockPuzzle.getInstance().text("Moves: " + gameMenu.getCurrentLevel().getMovesMade(), 260, 270, 120, 20);
        doRedraw = false;
    }

    private String getTimeString(Duration duration) {
        String timeString = "";
        if (duration.toMinutes() == 0) timeString += "00:";
        else if (duration.toMinutes() > 0 && duration.toMinutes() < 10) timeString += "0" + duration.toMinutes() + ":";
        else timeString += duration.toMinutes() + ":";
        if (duration.getSeconds() < 10) timeString += "0" + duration.getSeconds();
        else timeString += duration.getSeconds();
        return timeString;
    }

    public void continueButtonClicked() {
        BlockPuzzle.getInstance().switchMenu(MenuState.LEVELSELECT);
    }
}
