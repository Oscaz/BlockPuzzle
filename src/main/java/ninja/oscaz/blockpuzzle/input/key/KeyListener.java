package ninja.oscaz.blockpuzzle.input.key;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.menu.MenuState;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
public class KeyListener {

    private final MenuState menuState;
    private final Method method;
    private final List<Character> listeningCharacters;

    public KeyListener(MenuState menuState, List<Character> listeningCharacters, Method method) {
        this.menuState = menuState;
        this.method = method;
        this.listeningCharacters = listeningCharacters;
    }

    public KeyListener(MenuState menuState, Character listeningCharacter, Method method) {
        this.menuState = menuState;
        this.method = method;
        this.listeningCharacters = new ArrayList<>();
        this.listeningCharacters.add(listeningCharacter);
    }

}
