package ninja.oscaz.blockpuzzle.listener;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import processing.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KeyEventListener {

    @Getter public static KeyEventListener instance = new KeyEventListener();

    private List<Character> pressedKeys = new ArrayList<>();

    public void fireKey(KeyEvent event) {
        if (this.pressedKeys.contains(event.getKey())) return;
        KeyHandler.getInstance().callKey(event.getKey());
        this.pressedKeys.add(event.getKey());
    }

    public void endKey(KeyEvent event) {
        if (this.pressedKeys.contains(event.getKey())) {
            this.pressedKeys.remove((Character) event.getKey());
        }
    }

}
