package ninja.oscaz.blockpuzzle.level;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;
import ninja.oscaz.blockpuzzle.menu.EditorMenu;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EditorLevel {

    private final String name;
    private final List<Block> blocks;

    private Block getBlock(int x, int y) {
        for (Block block : this.blocks) {
            if (block.getX() == x && block.getY() == y) return block;
        }
        return null;
    }

    public void setBlock(int x, int y, BlockType blockType) {
        this.setBlock(this.getBlock(x, y), blockType);
    }

    public void setBlock(Block block, BlockType blockType) {
        this.blocks.remove(block);
        this.blocks.add(new Block(blockType, block.getX(), block.getY()));
    }


    public EditorLevel(Level level) {
        this.name = level.getName();
        this.blocks = level.getBlocks();
        this.populateBlocks();
    }

    public EditorLevel(String name, List<Block> blocks) {
        this.name = name;
        this.blocks = blocks;
        this.populateBlocks();
    }

    public EditorLevel(String name) {
        this.name = name;
        this.blocks = new ArrayList<>();
        this.populateBlocks();
    }

    private void populateBlocks() {
        for (int x = 0; x < 10; x++) {
            yLoop: for (int y = 0; y < 10; y++) {
                for (Block block : blocks) {
                    if (block.getX() == x && block.getY() == y) continue yLoop;
                }
                this.blocks.add(new Block(BlockType.EMPTY, x, y));
            }
        }
    }

}
