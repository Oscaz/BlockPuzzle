package ninja.oscaz.blockpuzzle.menu;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.error.GameError;
import ninja.oscaz.blockpuzzle.input.click.ClickHandler;
import ninja.oscaz.blockpuzzle.input.click.ClickListener;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import ninja.oscaz.blockpuzzle.level.GameLevel;
import ninja.oscaz.blockpuzzle.level.Level;

import java.awt.*;

public class LevelSelectMenu extends Menu {

    public LevelSelectMenu() {
        super(MenuState.LEVELSELECT);
    }

    @Override
    public void init() {
        try {
            KeyHandler.getInstance().registerListener(
                    this.getMenuState(), 'b', this.getClass().getMethod("switchMainMenu")
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawInit() {
        if (BlockPuzzle.getInstance().getLevels().size() == 0) {
            GameError.displayGameError("No existing levels!");
            return;
        }
        int x = 0, y = 0;
        for (Level level : BlockPuzzle.getInstance().getLevels()) {
            ClickListener clickListener;
            try {
                clickListener = new ClickListener(
                        MenuState.INGAME, 40 + (x * 160), 40 + (y * 160), 160 + (x * 160), 160 + (y * 160),
                        this.getClass().getMethod("selectLevel", Level.class)
                );
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            clickListener.setParameter(new GameLevel(level));
            ClickHandler.getInstance().registerListener(clickListener);
            x++;
            if (x == 4) {
                y++;
                x = 0;
            }
        }
    }

    @Override
    public void drawMenu() {
        BlockPuzzle.getInstance().background(80.0f);
        int x = 0, y = 0;
        for (Level level : BlockPuzzle.getInstance().getLevels()) {
            BlockPuzzle.getInstance().fill(Color.LIGHT_GRAY.getRGB());
            BlockPuzzle.getInstance().rect(40 + (x * 160), 40 + (y * 160), 120, 120, 5);
            BlockPuzzle.getInstance().fill(Color.BLACK.getRGB());
            BlockPuzzle.getInstance().text(level.getName(), 40 + (x * 160) + 10, 40 + (y * 160) + 10, 100, 100);
            x++;
            if (x == 4) {
                y++;
                x = 0;
            }
        }
    }

    public void switchMainMenu() {
        BlockPuzzle.getInstance().switchMenu(MenuState.MAIN);
    }

    public void selectLevel(Level level) {
        System.out.println("select level called");
        GameMenu gameMenu = (GameMenu) MenuState.INGAME.getMenu();
        gameMenu.setCurrentLevel(new GameLevel(level));
        BlockPuzzle.getInstance().switchMenu(MenuState.INGAME);
    }
}
