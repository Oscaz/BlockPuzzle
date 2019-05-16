package ninja.oscaz.blockpuzzle.listener;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.input.click.ClickHandler;

@Getter
public class ClickEventListener {

    @Getter public static ClickEventListener instance = new ClickEventListener();

    private boolean isMousePressed = false;

    public void fireClick() {
        if (this.isMousePressed) return;
        ClickHandler.getInstance().callClick();
        this.isMousePressed = true;
    }

    public void endClick() {
        this.isMousePressed = false;
    }

}
