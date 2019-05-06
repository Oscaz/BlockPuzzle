package ninja.oscaz.blockpuzzle.menu;

import lombok.Getter;

public abstract class Menu {

    @Getter private final MenuState menuState;

    public Menu(MenuState menuState) {
        this.menuState = menuState;
    }

    public void init() {

    }

    public void drawInit() {

    }

    public void drawKill() {

    }

    public abstract void drawMenu();

}
