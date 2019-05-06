package ninja.oscaz.blockpuzzle.input.click;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.menu.MenuState;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ClickHandler {

    @Getter
    public static ClickHandler instance = new ClickHandler();

    private final List<ClickListener> registeredListeners;

    public ClickHandler() {
        this.registeredListeners = new ArrayList<>();
    }

    public void callClick() {
        this.registeredListeners.forEach(listener -> {
            System.out.println(1);
            if (!(BlockPuzzle.getInstance().getMenuState() == listener.getMenuState())) return;
            System.out.println(2);
            if (!(BlockPuzzle.getInstance().mouseX > listener.getX())) return;
            System.out.println(3);
            if (!(BlockPuzzle.getInstance().mouseX < listener.getXBound())) return;
            System.out.println(4);
            if (!(BlockPuzzle.getInstance().mouseY > listener.getY())) return;
            System.out.println(5);
            if (!(BlockPuzzle.getInstance().mouseY < listener.getYBound())) return;
            System.out.println(6);
            try {
                System.out.println(7);
                if (listener.getParameter() == null) {
                    System.out.println(8);
                    listener.getMethod().invoke(listener.getMenuState().getMenu());
                    System.out.println(9);
                } else {
                    System.out.println(10);
                    listener.getMethod().invoke(listener.getMenuState().getMenu(), listener.getParameter());
                    System.out.println(11);
                }
            } catch (ReflectiveOperationException e) {
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
