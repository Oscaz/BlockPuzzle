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
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class BlockPuzzle extends PApplet {

    private final static String mainClass = "ninja.oscaz.blockpuzzle.BlockPuzzle";

    @Getter private static BlockPuzzle instance;

    public BlockPuzzle() {
        instance = this;
    }

    private boolean drawInit = true;
    @Getter @Setter
    private MenuState menuState;

    @Getter
    public List<Level> levels;

    @Getter @Setter
    private boolean errorHalt = false;

    @Override
    public void settings() {
        this.menuState = MenuState.MAIN.init();
        this.levels = new ArrayList<>();
        Arrays.asList("levels/Level1.level",
                      "levels/Level2.level",
                      "levels/Level3.level",
                      "levels/Level4.level",
                      "levels/Level5.level",
                      "levels/Level6.level",
                      "levels/Level7.level",
                      "levels/Level8.level",
                      "levels/Level9.level",
                      "levels/Level10.level")
                .forEach(levelString -> LevelLoader.loadLevel(this.getClass().getClassLoader().getResourceAsStream(levelString)));
        this.size(640,640);
    }

    @Override
    public void setup() {
        this.surface.setIcon(loadImage("Player.png"));
    }

    @Override
    public void draw() {
        if (errorHalt) {
            GameError.drawError();
            return;
        }
        if (drawInit) {
            this.menuState.getMenu().drawInit();
            this.drawInit = false;
        }
        this.menuState.getMenu().drawMenu();
    }

    public void switchMenu(MenuState menuState) {
        this.menuState.getMenu().drawKill();
        this.menuState = menuState;
        this.drawInit = true;
    }

    private final Map<String, BufferedImage> storedImages = new HashMap<>();

    public BufferedImage getResourceImage(String name) {
        if (!this.storedImages.containsKey(name)) {
            try {
                BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResource(name + ".png"));
                this.storedImages.put(name, image);
                return image;
            } catch (IOException e) {
                GameError.displayGameError("Fatal error: Error loading resource image, restart your logic.");
                throw new RuntimeException(e);
            }
        } else return this.storedImages.get(name);
    }

    public synchronized void playSound(final String name) {
        // DISABLED -- WINDOWS CRASHEY
        /*new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(this.getAudioFile(name));
                clip.start();
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }).start();*/
    }

    private AudioInputStream getAudioFile(String name) {
        try {
            return AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream("sound/" + name + ".wav"));
        } catch (UnsupportedAudioFileException | IOException e) {
            GameError.displayGameError("Error: Could not load audio file: " + name);
            throw new RuntimeException(e);
        }
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
