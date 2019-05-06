package ninja.oscaz.blockpuzzle.block;

import lombok.Getter;

@Getter
public class Block {

    private final BlockType blockType;
    private final int x;
    private final int y;

    public Block(BlockType blockType, int x, int y) {
        this.blockType = blockType;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return this.blockType.toString() + "|" + x + "|" + y;
    }

}
