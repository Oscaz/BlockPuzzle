package ninja.oscaz.blockpuzzle.input.click;

import lombok.Getter;
import lombok.Setter;
import ninja.oscaz.blockpuzzle.menu.MenuState;

import java.lang.reflect.Method;

@Getter
public class ClickListener {

    private final MenuState menuState;
    private final int x;
    private final int y;
    private final int xBound;
    private final int yBound;
    private final Method method;

    @Setter private Object parameter = null;

    public ClickListener(MenuState menuState, int x, int y, int xBound, int yBound, Method method) {
        this.menuState = menuState;
        this.x = x;
        this.y = y;
        this.xBound = xBound;
        this.yBound = yBound;
        this.method = method;
    }

    @Override
    public String toString() {
        return menuState + "|" + x + "|" + y + "|" + xBound + "|" + yBound + "|" + method.getName();
    }

}
