package ninja.oscaz.blockpuzzle.menu;

import ninja.oscaz.blockpuzzle.BlockPuzzle;

public class LevelSelectMenu extends Menu {

    public LevelSelectMenu() {
        super(MenuState.LEVELSELECT);
    }

    @Override
    public void drawMenu() {
        BlockPuzzle.getInstance().background(80.0f);
    }
}
