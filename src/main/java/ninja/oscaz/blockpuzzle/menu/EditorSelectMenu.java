package ninja.oscaz.blockpuzzle.menu;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.error.GameError;
import ninja.oscaz.blockpuzzle.file.FileChooser;
import ninja.oscaz.blockpuzzle.input.click.ClickHandler;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import ninja.oscaz.blockpuzzle.level.EditorLevel;
import ninja.oscaz.blockpuzzle.level.Level;
import ninja.oscaz.blockpuzzle.level.LevelLoader;
import processing.core.PImage;

public class EditorSelectMenu extends Menu {

    public EditorSelectMenu() {
        super(MenuState.EDITORSELECT);
    }

    @Override
    public void init() {
        try {
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 100, 270, 280, 370, this.getClass().getMethod("loadLevel")
            );
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 380, 270, 560, 370, this.getClass().getMethod("newLevel")
            );
            KeyHandler.getInstance().registerListener(
                    this.getMenuState(), 'b', this.getClass().getMethod("switchMainMenu")
            );
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawInit() {
        BlockPuzzle.getInstance().background(100f);
    }

    @Override
    public void drawMenu() {
        BlockPuzzle.getInstance().background(100f);
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("Load")), 100, 270);
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("New")), 380, 270);
    }

    public void loadLevel() {
        EditorMenu editorMenu = (EditorMenu) MenuState.EDITOR.getMenu();
        try {
            Level level = LevelLoader.returnLevel(FileChooser.chooseFile());
            if (level == null) {
                GameError.displayGameError("Error loading level!");
                return;
            }
            editorMenu.setLevel(new EditorLevel(level));
            BlockPuzzle.getInstance().switchMenu(MenuState.EDITOR);
        } catch (NullPointerException e) {
            GameError.displayGameError("Error loading level!");
        }
    }

    public void newLevel() {
        BlockPuzzle.getInstance().switchMenu(MenuState.EDITORNAME);
    }

    public void switchMainMenu() {
        BlockPuzzle.getInstance().switchMenu(MenuState.MAIN);
    }
}
