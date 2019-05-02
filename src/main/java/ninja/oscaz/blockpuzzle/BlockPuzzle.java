package ninja.oscaz.blockpuzzle;

import lombok.Getter;
import lombok.Setter;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import ninja.oscaz.blockpuzzle.menu.MenuState;
import ninja.oscaz.blockpuzzle.input.click.ClickHandler;
import processing.core.PApplet;
import processing.event.KeyEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockPuzzle extends PApplet {

    private final static String mainClass = "ninja.oscaz.blockpuzzle.BlockPuzzle";

    @Getter private static BlockPuzzle instance;

    public BlockPuzzle() {
        instance = this;
        try {
            System.out.println(this.frameRate);
            this.frameRate(20);
            System.out.println(this.frameRate);
        } catch (Exception e) {
            // ignored
        }
        System.out.println(this.frameRate);
    }

    @Getter @Setter
    private MenuState menuState;

    public void settings() {
        this.menuState = MenuState.MAIN.init();
        this.size(640,640);
    }

    public void draw() {
//        if (this.keyPressed) menuState.getMenu().handleKeyPress();
        this.menuState.getMenu().drawMenu();
    }

    public void switchMenu(MenuState menuState) {
        this.menuState = menuState;
    }

    private boolean isMousePressed = false;

    @Override
    public void mousePressed() {
        if (this.isMousePressed) return;
        ClickHandler.getInstance().callClick();
        this.isMousePressed = true;
    }

    @Override
    public void mouseReleased() {
        this.isMousePressed = false;
    }

    private List<Character> pressedKeys = new ArrayList<>();

    @Override
    public void keyPressed(KeyEvent event) {
        if (this.pressedKeys.contains(event.getKey())) return;
        KeyHandler.getInstance().callKey(event.getKey());
        this.pressedKeys.add(event.getKey());
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (this.pressedKeys.contains(event.getKey())) {
            this.pressedKeys.remove((Character) event.getKey());
        }
    }


    private final Map<String, BufferedImage> storedImages = new HashMap<>();

    public BufferedImage getResourceImage(String name) {
        if (!this.storedImages.containsKey(name)) {
            try {
                BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResource(name + ".png"));
                this.storedImages.put(name, image);
                return image;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else return this.storedImages.get(name);
        throw new IllegalStateException("Illegal state reached");
    }

    public static void main(String[] args) {
        PApplet.main(BlockPuzzle.mainClass);
    }
}
