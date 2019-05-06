package ninja.oscaz.blockpuzzle.menu;

import lombok.Getter;
import lombok.Setter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.game.Direction;
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
        try {
            KeyHandler.getInstance().registerListener(
                    this.getMenuState(), Arrays.asList('w', 'a', 's', 'd', '↑', '↓', '←', '→'), this.getClass().getMethod("moveKeyPressed", Character.class)
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
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
        BlockPuzzle.getInstance().background(210.0f);
        BlockPuzzle.getInstance().fill(100.0f);
        BlockPuzzle.getInstance().rect(0,0, 480, 480);
        this.currentLevel.getEditedBlocks().forEach(block -> {
            BlockPuzzle.getInstance().image(new PImage(block.getBlockType().getImage()), block.getX() * 48, block.getY() * 48, 48, 48);
        });
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("Player")),
                this.currentLevel.getPlayerX() * 48, this.currentLevel.getPlayerY() * 48);
        this.doRedraw = false;
    }

    public void moveKeyPressed(Character character) {
        Direction direction = null;
        if (character.equals('w') || character.equals('↑')) direction = Direction.UP;
        if (character.equals('s') || character.equals('↓')) direction = Direction.DOWN;
        if (character.equals('a') || character.equals('←')) direction = Direction.LEFT;
        if (character.equals('d') || character.equals('→')) direction = Direction.RIGHT;
        doRedraw = this.currentLevel.executeMove(direction);
    }

}
