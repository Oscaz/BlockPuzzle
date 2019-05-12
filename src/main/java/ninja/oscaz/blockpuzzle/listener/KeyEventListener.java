package ninja.oscaz.blockpuzzle.listener;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KeyEventListener {

    @Getter public static KeyEventListener instance = new KeyEventListener();

    private List<Character> pressedKeys = new ArrayList<>();

    public void fireKey(KeyEvent event) {
        char key = event.getKey() == PApplet.CODED ? this.getCodedKey(event.getKeyCode()) : event.getKey();
        if (this.pressedKeys.contains(key)) return;
        KeyHandler.getInstance().callKey(key);
        this.pressedKeys.add(key);
    }

    public void endKey(KeyEvent event) {
        char key = event.getKey() == PApplet.CODED ? this.getCodedKey(event.getKeyCode()) : event.getKey();
        if (this.pressedKeys.contains(key)) {
            this.pressedKeys.remove((Character) key);
        }
    }

    private Character getCodedKey(int keyCode) {
        if (keyCode == PApplet.UP) return '↑';
        if (keyCode == PApplet.DOWN) return '↓';
        if (keyCode == PApplet.LEFT) return '←';
        if (keyCode == PApplet.RIGHT) return '→';
        if (keyCode == PApplet.BACKSPACE) return '⇐';
        if (keyCode == PApplet.ENTER) return '⇒';
        return '↺';
    }

}
