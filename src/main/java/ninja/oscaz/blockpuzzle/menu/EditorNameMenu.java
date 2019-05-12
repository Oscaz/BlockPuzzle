package ninja.oscaz.blockpuzzle.menu;

import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import ninja.oscaz.blockpuzzle.level.EditorLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EditorNameMenu extends Menu {

    private final List<Character> alphabet;

    public EditorNameMenu() {
        super(MenuState.EDITORNAME);
        this.alphabet = new ArrayList<>();
        IntStream.range(0, 26).forEach(i -> {
            alphabet.add((char) (65 + i));
            alphabet.add((char) (97 + i));
        });
        IntStream.range(0, 10).forEach(i -> alphabet.add(Integer.toString(i).charAt(0)));
        this.alphabet.add(' ');
        this.alphabet.add('\b');
        this.alphabet.add('⇐');
        this.alphabet.add('⇒');
        this.alphabet.add('\n');
    }

    @Override
    public void drawInit() {
        this.inputCharacters = new StringBuilder();
        try {
            KeyHandler.getInstance().registerListener(
                    this.getMenuState(), this.alphabet, this.getClass().getMethod("keyInput", Character.class)
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawKill() {
        KeyHandler.getInstance().deregisterListener(this.getMenuState());
    }

    private StringBuilder inputCharacters;
    private boolean redraw = true;

    @Override
    public void drawMenu() {
        if (!this.redraw) return;
        BlockPuzzle.getInstance().background(100f);
        BlockPuzzle.getInstance().fill(200f);
        BlockPuzzle.getInstance().rect(0, 300, 640, 40);
        BlockPuzzle.getInstance().fill(50f);
        BlockPuzzle.getInstance().text("Enter level name",320, 240);
        BlockPuzzle.getInstance().text(this.inputCharacters.toString(),320, 320);
        BlockPuzzle.getInstance().text("Press enter when done", 320, 400);
        this.redraw = false;
    }

    public void keyInput(Character character) {
        this.redraw = true;
        if (character.equals('⇐') || character.equals('\b')) {
            try {
                this.inputCharacters.deleteCharAt(this.inputCharacters.length() - 1);
            } catch (StringIndexOutOfBoundsException e) {
                // ignored
            }
            return;
        }
        if (character.equals('⇒') || character.equals('\n')) {
            EditorMenu editorMenu = (EditorMenu) MenuState.EDITOR.getMenu();
            editorMenu.setLevel(new EditorLevel(this.inputCharacters.toString()));
            BlockPuzzle.getInstance().switchMenu(MenuState.EDITOR);
            return;
        }
        this.inputCharacters.append(character);
    }
}
