package ninja.oscaz.blockpuzzle.menu;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.input.click.ClickHandler;
import processing.core.PConstants;
import processing.core.PImage;

public class MainMenu extends Menu {

    public MainMenu() {
        super(MenuState.MAIN);
    }

    @Override
    public void drawInit() {
        try {
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 80, 400, 280, 500, this.getClass().getMethod("selectButtonClicked")
            );
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 360, 400, 560, 500, this.getClass().getMethod("editorButtonClicked")
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        BlockPuzzle.getInstance().textAlign(PConstants.CENTER, PConstants.CENTER);
        BlockPuzzle.getInstance().textSize(20);
        BlockPuzzle.getInstance().fill(50);
    }

    @Override
    public void drawKill() {
        ClickHandler.getInstance().deregisterListener(this.getMenuState());
    }

    @Override
    public void drawMenu() {
        BlockPuzzle.getInstance().background(100f);
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

}
