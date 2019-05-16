package ninja.oscaz.blockpuzzle.menu;

import lombok.Getter;
import lombok.Setter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;
import ninja.oscaz.blockpuzzle.file.FileSaver;
import ninja.oscaz.blockpuzzle.input.click.ClickHandler;
import ninja.oscaz.blockpuzzle.input.click.ClickListener;
import ninja.oscaz.blockpuzzle.input.key.KeyHandler;
import ninja.oscaz.blockpuzzle.level.EditorLevel;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EditorMenu extends Menu {

    @Getter
    @Setter
    private EditorLevel level;

    public EditorMenu() {
        super(MenuState.EDITOR);
    }

    private final List<BlockType> editableBlockTypes = new ArrayList<>();

    @Override
    public void init() {
        editableBlockTypes.addAll(
                Arrays.stream(BlockType.values()).filter(BlockType::isEditUsable).collect(Collectors.toList())
        );
    }

    @Override
    public void drawInit() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                try {
                    ClickListener clickListener = new ClickListener(
                        this.getMenuState(), x * 48, y * 48, x * 48 + 48, y * 48 + 48,
                        this.getClass().getMethod("gameBlockClicked", String.class)
                    );
                    clickListener.setParameter(x + "|" + y);
                    ClickHandler.getInstance().registerListener(clickListener);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            int x = 0, y = 0;
            for (BlockType blockType : this.editableBlockTypes) {
                ClickListener clickListener = new ClickListener(
                        this.getMenuState(), 48 * x, (480 + (y * 48)), (48 * x) + 48, (528 + (y * 48)),
                        this.getClass().getMethod("blockTypeClicked", BlockType.class)
                );
                clickListener.setParameter(blockType);
                ClickHandler.getInstance().registerListener(clickListener);
                x++;
                if (x == 10) {
                    x = 0;
                    y++;
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        /*
        int x = 0, y = 0;
        for (BlockType blockType : this.editableBlockTypes) {
            System.out.println("blocktype: " + blockType);
            System.out.println("x: " + x);
            System.out.println("y: " + y);
            System.out.println("xstart: " + 48 * x);
            System.out.println("ystart: " + (480+(y*48)));
            System.out.println("xbound: " + (48*x + 48));
            System.out.println("ybound: " + (528+ (y*48)));
            try {
                ClickListener clickListener = new ClickListener(
                        this.getMenuState(), 48 * x, 480 + (y * 48), 48 * x + 48, 528 + (y * 48),
                        this.getClass().getMethod("blockTypeClicked", BlockType.class)
                );
                clickListener.setParameter(this.editableBlockTypes.get(x));
                ClickHandler.getInstance().registerListener(clickListener);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            x++;
            if (x == 10) {
                x = 0;
                y++;
            }
        }*/
        try {
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 480, 0, 640, 80, this.getClass().getMethod("switchMainMenu")
            );
            ClickHandler.getInstance().registerListener(
                    this.getMenuState(), 480, 100, 640, 180, this.getClass().getMethod("saveFile")
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.redrawMenu = true;
    }

    @Override
    public void drawKill() {
        ClickHandler.getInstance().deregisterListener(MenuState.EDITOR);
    }

    private boolean redrawMenu;
    private BlockType selectedBlockType = BlockType.EMPTY;

    @Override
    public void drawMenu() {
        if (!this.redrawMenu) return;
        BlockPuzzle.getInstance().background(100f);
        BlockPuzzle.getInstance().rect(0,0,480, 480);
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("Back")), 480, 0);
        BlockPuzzle.getInstance().image(new PImage(BlockPuzzle.getInstance().getResourceImage("Save")), 480, 100);
        this.level.getBlocks().forEach(
                block -> BlockPuzzle.getInstance().image(new PImage(block.getBlockType().getImage()), block.getX() * 48, block.getY() * 48, 48, 48)
        );
        int x = 0, y = 0;
        for (BlockType blockType : this.editableBlockTypes) {
            BlockPuzzle.getInstance().image(new PImage(blockType.getImage()), 48 * x,480 + (y * 48));
            x++;
            if (x == 10) {
                x = 0;
                y++;
            }
        }
        this.redrawMenu = false;
    }

    public void gameBlockClicked(String coordinates) {
        int x = Integer.valueOf(coordinates.split(Pattern.quote("|"))[0]);
        int y = Integer.valueOf(coordinates.split(Pattern.quote("|"))[1]);
        this.getLevel().setBlock(x, y, this.selectedBlockType);
        this.redrawMenu = true;
    }

    public void blockTypeClicked(BlockType blockType) {
        this.selectedBlockType = blockType == BlockType.REMOVE ? BlockType.EMPTY : blockType;
    }

    public void saveFile() {
        List<Block> blocks = this.level.getBlocks().stream().filter(block -> block.getBlockType() != BlockType.EMPTY).collect(Collectors.toList());
        List<String> lines = new ArrayList<>();
        lines.add(this.level.getName());
        blocks.forEach(block -> lines.add("$" + block.getX() + "|" + block.getY() + "|" + block.getBlockType().toString()));
        try {
            FileWriter fileWriter = new FileWriter(FileSaver.chooseFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchMainMenu() {
        BlockPuzzle.getInstance().switchMenu(MenuState.MAIN);
    }

}
