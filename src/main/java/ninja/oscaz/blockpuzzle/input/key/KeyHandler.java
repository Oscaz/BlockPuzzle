package ninja.oscaz.blockpuzzle.input.key;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.menu.MenuState;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class KeyHandler {

    @Getter
    public static KeyHandler instance = new KeyHandler();

    private final List<KeyListener> registeredListeners;

    public KeyHandler() {
        this.registeredListeners = new ArrayList<>();
    }

    public void registerListener(MenuState menuState, List<Character> characters, Method method) {
        this.registeredListeners.add(new KeyListener(menuState, characters, method));
    }

    public void registerListener(MenuState menuState, Character character, Method method) {
        this.registeredListeners.add(new KeyListener(menuState, character, method));
    }

    public void registerListener(KeyListener keyListener) {
        this.registeredListeners.add(keyListener);
    }

    public void callKey(Character keyPressed) {
        this.registeredListeners.forEach(listener -> {
            if (!(BlockPuzzle.getInstance().getMenuState() == listener.getMenuState())) return;
            if (!(listener.getListeningCharacters().contains(keyPressed))) return;
            try {
                if (listener.getMethod().getParameterCount() > 0) {
                    listener.getMethod().invoke(listener.getMenuState().getMenu(), keyPressed);
                } else {
                    listener.getMethod().invoke(listener.getMenuState().getMenu());
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
