package ninja.oscaz.blockpuzzle.menu;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.file.FileChooser;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import ninja.oscaz.blockpuzzle.level.LevelLoader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditorSelectMenu extends Menu {

    public EditorSelectMenu() {
        super(MenuState.EDITORSELECT);
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
        BlockPuzzle.getInstance().background(100f);
        LevelLoader.loadLevel(FileChooser.chooseFile());
    }

    @Override
    public void drawMenu() {
        BlockPuzzle.getInstance().background(100f);
    }

    public void switchMainMenu() {
        BlockPuzzle.getInstance().switchMenu(MenuState.MAIN);
    }
}
