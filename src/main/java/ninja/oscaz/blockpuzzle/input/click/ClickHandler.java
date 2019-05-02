package ninja.oscaz.blockpuzzle.input.click;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.menu.MenuState;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClickHandler {

    @Getter
    public static ClickHandler instance = new ClickHandler();

    private final List<ClickListener> registeredListeners;

    public ClickHandler() {
        this.registeredListeners = new ArrayList<>();
    }

    public void callClick() {
        this.registeredListeners.forEach(listener -> {
            if (!(BlockPuzzle.getInstance().getMenuState() == listener.getMenuState())) return;
            if (!(BlockPuzzle.getInstance().mouseX > listener.getX())) return;
            if (!(BlockPuzzle.getInstance().mouseX < listener.getXBound())) return;
            if (!(BlockPuzzle.getInstance().mouseY > listener.getY())) return;
            if (!(BlockPuzzle.getInstance().mouseY < listener.getYBound())) return;
            try {
                listener.getMethod().invoke(listener.getMenuState().getMenu());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void registerListener(MenuState menuState, int x, int y, int xBound, int yBound, Method method) {
        ClickListener clickListener = new ClickListener(menuState, x, y, xBound,  yBound, method);
        this.registeredListeners.add(clickListener);
    }

    public void registerListener(ClickListener clickListener) {
        this.registeredListeners.add(clickListener);
    }

    public void deregisterListener(MenuState menuState) {
        this.registeredListeners.forEach(listener -> {
            if (listener.getMenuState() == menuState) registeredListeners.remove(listener);
        });
    }

    public void deregisterListener(MenuState menuState, int x, int y, int xBound, int yBound, Method method) {
        this.registeredListeners.remove(new ClickListener(menuState, x, y, xBound,  yBound, method));
    }

    public void deregisterListener(ClickListener clickListener) {
        this.registeredListeners.remove(clickListener);
    }



}
