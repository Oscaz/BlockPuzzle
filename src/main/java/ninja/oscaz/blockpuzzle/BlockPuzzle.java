package ninja.oscaz.blockpuzzle;

import lombok.Getter;
import lombok.Setter;
import ninja.oscaz.blockpuzzle.error.GameError;
import ninja.oscaz.blockpuzzle.level.Level;
import ninja.oscaz.blockpuzzle.level.LevelLoader;
import ninja.oscaz.blockpuzzle.listener.ClickEventListener;
import ninja.oscaz.blockpuzzle.listener.KeyEventListener;
import ninja.oscaz.blockpuzzle.menu.MenuState;
import processing.core.PApplet;
import processing.event.KeyEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockPuzzle extends PApplet {

    private final static String mainClass = "ninja.oscaz.blockpuzzle.BlockPuzzle";

    @Getter private static BlockPuzzle instance;

    public BlockPuzzle() {
        instance = this;
    }

    @Getter @Setter
    private MenuState menuState;

    @Getter
    public List<Level> levels;

    @Getter @Setter
    private boolean errorHalt = false;

    public void settings() {
        this.menuState = MenuState.MAIN.init();
        this.levels = new ArrayList<>();
        System.out.println(this.getClass().getClassLoader().getResourceAsStream("levels/1.txt"));
        LevelLoader.loadLevel(this.getClass().getClassLoader().getResourceAsStream("levels/1.txt"));
        this.size(640,640);
    }

    public void draw() {
        if (errorHalt) {
            GameError.drawError();
            return;
        }
        this.menuState.getMenu().drawMenu();
    }

    public void switchMenu(MenuState menuState) {
        this.menuState = menuState;
        this.menuState.getMenu().drawInit();
    }

    private final Map<String, BufferedImage> storedImages = new HashMap<>();

    public BufferedImage getResourceImage(String name) {
        if (!this.storedImages.containsKey(name)) {
            try {
                BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResource(name + ".png"));
                this.storedImages.put(name, image);
                return image;
            } catch (IOException e) {
                GameError.displayGameError("Fatal error: Error loading resource image, restart your game.");
            }
        } else return this.storedImages.get(name);
        throw new IllegalStateException("Illegal state reached");
    }

    @Override
    public void mousePressed() {
        if (this.isErrorHalt()) return;
        ClickEventListener.getInstance().fireClick();
    }

    @Override
    public void mouseReleased() {
        if (this.isErrorHalt()) {
            GameError.mouseReleased();
            return;
        }
        ClickEventListener.getInstance().endClick();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (this.isErrorHalt()) return;
        KeyEventListener.getInstance().fireKey(event);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (this.isErrorHalt()) return;
        KeyEventListener.getInstance().endKey(event);
    }

    public static void main(String[] args) {
        PApplet.main(BlockPuzzle.mainClass);
    }
}
