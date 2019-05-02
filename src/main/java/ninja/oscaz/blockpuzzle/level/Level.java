package ninja.oscaz.blockpuzzle.level;

import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;

import java.util.List;

public class Level {

    private final List<Block> blocks;

    public Level(List<Block> blocks) {
        this.blocks = blocks;
        for (int x = 0; x < 15; x++) {
            yLoop: for (int y = 0; y < 15; y++) {
                for (Block block : blocks) {
                    if (block.getX() == x && block.getY() == y) continue yLoop;
                }
                blocks.add(new Block(BlockType.IMMOVABLE, x, y));
            }
        }
    }

    public boolean isMoveLegal() {
        return true;
    }

}
