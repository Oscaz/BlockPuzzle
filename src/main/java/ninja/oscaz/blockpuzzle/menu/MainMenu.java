package ninja.oscaz.blockpuzzle.menu;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.input.click.ClickHandler;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import processing.core.PImage;

import java.util.Arrays;

public class MainMenu extends Menu {

    public MainMenu() {
        super(MenuState.MAIN);
    }

    @Override
    public void init() {
        try {
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 80, 400, 280, 500, this.getClass().getMethod("selectButtonClicked")
            );
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 360, 400, 560, 500, this.getClass().getMethod("editorButtonClicked")
            );
            KeyHandler.getInstance().registerListener(
                    this.getMenuState(), Arrays.asList('b', 'c'), this.getClass().getMethod("characterClicked", Character.class)
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawMenu() {
        BlockPuzzle.getInstance().background(80.0f);
        BlockPuzzle.getInstance().image(
                new PImage(BlockPuzzle.getInstance().getResourceImage("MainLogo")), 120, 120, 400, 33
        );
        BlockPuzzle.getInstance().image(
                new PImage(BlockPuzzle.getInstance().getResourceImage("SelectLevelButton")), 80, 400, 200, 100
        );
        BlockPuzzle.getInstance().image(
                new PImage(BlockPuzzle.getInstance().getResourceImage("LevelEditorButton")), 360, 400, 200, 100
        );
    }

    public void selectButtonClicked() {
        BlockPuzzle.getInstance().switchMenu(MenuState.LEVELSELECT);
    }

    public void editorButtonClicked() {
        BlockPuzzle.getInstance().switchMenu(MenuState.EDITORSELECT);
    }

    public void characterClicked(Character key) {
        System.out.println("key clicked! " + key);
    }
}