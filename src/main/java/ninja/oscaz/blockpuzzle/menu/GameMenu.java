package ninja.oscaz.blockpuzzle.menu;

import lombok.Getter;
import lombok.Setter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.level.GameLevel;
import ninja.oscaz.blockpuzzle.level.Level;
import processing.core.PImage;

public class GameMenu extends Menu {

    public GameMenu() {
        super(MenuState.INGAME);
    }

    @Getter
    @Setter
    private GameLevel currentLevel;

    @Override
    public void drawInit() {

    }

    @Override
    public void drawKill() {

    }

    private boolean doRedraw = true;

    @Override
    public void drawMenu() {
        if (doRedraw) this.redrawGame();
    }

    private void redrawGame() {
        BlockPuzzle.getInstance().background(80.0f);
        System.out.println("level: " + this.currentLevel);
        this.currentLevel.getEditedBlocks().forEach(block -> {
            System.out.println("block inside");
            BlockPuzzle.getInstance().image(new PImage(block.getBlockType().getImage()), block.getX() * 16, block.getY() * 16);
        });
        this.doRedraw = false;
    }
}
