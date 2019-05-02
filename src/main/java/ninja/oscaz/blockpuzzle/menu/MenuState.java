package ninja.oscaz.blockpuzzle.menu;

import lombok.Getter;
import lombok.Setter;

public enum MenuState {

    MAIN(MainMenu.class),
    INGAME(GameMenu.class),
    EDITOR(EditorMenu.class),
    LEVELSELECT(LevelSelectMenu.class),
    EDITORSELECT(EditorSelectMenu.class);

    @Getter @Setter private Menu menu;
    @Getter private Class<? extends Menu> menuClass;

    MenuState(Class<? extends Menu> menuClass) {
        this.menuClass = menuClass;
    }

    public MenuState init() {
        for (MenuState value : MenuState.values()) {
            try {
                value.setMenu(value.getMenuClass().newInstance());
                value.getMenu().init();
            } catch (InstantiationException | IllegalAccessException e) {
                this.menu = null;
                throw new RuntimeException(e);
            }
        }
        return this;
    }
}
