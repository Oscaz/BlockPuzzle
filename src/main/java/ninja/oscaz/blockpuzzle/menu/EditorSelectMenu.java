package ninja.oscaz.blockpuzzle.menu;

import ninja.oscaz.blockpuzzle.BlockPuzzle;

public class EditorSelectMenu extends Menu {

    public EditorSelectMenu() {
        super(MenuState.EDITORSELECT);
    }

    @Override
    public void drawMenu() {
        BlockPuzzle.getInstance().background(80.0f);
    }
}
