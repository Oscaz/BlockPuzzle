package ninja.oscaz.blockpuzzle.level;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;

import java.util.List;

@Getter
public class Level {

    private final String name;
    private final List<Block> blocks;

    public Level(String name, List<Block> blocks) {
        this.name = name;
        this.blocks = blocks;
        for (int x = 0; x < 10; x++) {
            yLoop: for (int y = 0; y < 10; y++) {
                for (Block block : blocks) {
                    if (block.getX() == x && block.getY() == y) continue yLoop;
                }
                this.blocks.add(new Block(BlockType.EMPTY, x, y));
            }
        }
    }

    @Override
    public String toString() {
        return this.name + "|" + this.blocks.size();
    }

}
