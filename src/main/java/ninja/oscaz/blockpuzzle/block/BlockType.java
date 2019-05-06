package ninja.oscaz.blockpuzzle.block;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;

import java.awt.image.BufferedImage;

public enum BlockType {

    IMMOVABLE(BlockPuzzle.getInstance().getResourceImage("block/Immovable")),
    MOVABLE(BlockPuzzle.getInstance().getResourceImage("block/Movable")),
    SPAWN(BlockPuzzle.getInstance().getResourceImage("block/Spawn")),
    GOAL(BlockPuzzle.getInstance().getResourceImage("block/Goal")),
    EMPTY(BlockPuzzle.getInstance().getResourceImage("block/Empty")),
    GOAL_COMPLETE(BlockPuzzle.getInstance().getResourceImage("block/Goal_Complete"));

    @Getter
    private final BufferedImage image;

    BlockType(BufferedImage image) {
        this.image = image;
    }
}
