package ninja.oscaz.blockpuzzle.menu;

import lombok.Getter;
import lombok.Setter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.logic.Direction;
import ninja.oscaz.blockpuzzle.input.click.ClickHandler;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import ninja.oscaz.blockpuzzle.level.GameLevel;
import processing.core.PImage;

import java.util.Arrays;

public class GameMenu extends Menu {

    public GameMenu() {
        super(MenuState.INGAME);
    }

    @Getter
    @Setter
    private GameLevel currentLevel;

    @Override
    public void drawInit() {
        this.doRedraw = true;
        try {
            KeyHandler.getInstance().registerListener(
                    this.getMenuState(), Arrays.asList('w', 'a', 's', 'd', '↑', '↓', '←', '→'), this.getClass().getMethod("moveKeyPressed", Character.class)
            );
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 480, 0, 640, 80, this.getClass().getMethod("backClicked")
            );
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 480, 100, 640, 180, this.getClass().getMethod("retryClicked")
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawKill() {
        ClickHandler.getInstance().deregisterListener(MenuState.INGAME);
        KeyHandler.getInstance().deregisterListener(MenuState.INGAME);
    }

    private boolean doRedraw = true;

    @Override
    public void drawMenu() {
        if (doRedraw) this.redrawGame();
    }

    private void redrawGame() {
        BlockPuzzle.getInstance().background(150f);
        BlockPuzzle.getInstance().fill(100.0f);
        BlockPuzzle.getInstance().noStroke();
        BlockPuzzle.getInstance().rect(0,0, 480, 480);
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("Back")), 480, 0);
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("Retry")), 480, 100);
        this.currentLevel.getEditedBlocks().forEach(block -> {
            BlockPuzzle.getInstance().image(new PImage(block.getBlockType().getImage()), block.getX() * 48, block.getY() * 48, 48, 48);
        });
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("Player")),
                this.currentLevel.getPlayerX() * 48, this.currentLevel.getPlayerY() * 48);
        BlockPuzzle.getInstance().stroke(0);
        this.doRedraw = false;
        if (this.currentLevel.isGameOver()) {
            BlockPuzzle.getInstance().switchMenu(MenuState.LEVELCOMPLETE);
        }
    }

    public void moveKeyPressed(Character character) {
        Direction direction = null;
        if (character.equals('w') || character.equals('↑')) direction = Direction.UP;
        if (character.equals('s') || character.equals('↓')) direction = Direction.DOWN;
        if (character.equals('a') || character.equals('←')) direction = Direction.LEFT;
        if (character.equals('d') || character.equals('→')) direction = Direction.RIGHT;
        doRedraw = this.currentLevel.executeMove(direction);
    }

    public void backClicked() {
        BlockPuzzle.getInstance().switchMenu(MenuState.LEVELSELECT);
    }

    public void retryClicked() {
        GameMenu gameMenu = (GameMenu) MenuState.INGAME.getMenu();
        gameMenu.setCurrentLevel(new GameLevel(gameMenu.getCurrentLevel().getLevel()));
        BlockPuzzle.getInstance().switchMenu(MenuState.INGAME);
    }

}
