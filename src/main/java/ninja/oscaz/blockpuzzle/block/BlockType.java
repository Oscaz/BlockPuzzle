package ninja.oscaz.blockpuzzle.block;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;

import java.awt.image.BufferedImage;

public enum BlockType {

    IMMOVABLE(BlockPuzzle.getInstance().getResourceImage("block/immovable.png")),
    MOVABLE(BlockPuzzle.getInstance().getResourceImage("block/movable.png")),
    SPAWN(BlockPuzzle.getInstance().getResourceImage("block/spawn.png")),
    GOAL(BlockPuzzle.getInstance().getResourceImage("block/goal.png")),
    MEDAL(BlockPuzzle.getInstance().getResourceImage("block/medal.png"));

    @Getter
    private final BufferedImage image;

    BlockType(BufferedImage image) {
        this.image = image;
    }
}
