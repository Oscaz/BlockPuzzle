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
    GOAL_COMPLETE(BlockPuzzle.getInstance().getResourceImage("block/Goal_Complete")),
    ORANGE_PORTAL(BlockPuzzle.getInstance().getResourceImage("block/Orange_Portal")),
    BLUE_PORTAL(BlockPuzzle.getInstance().getResourceImage("block/Blue_Portal")),
    GLASS(BlockPuzzle.getInstance().getResourceImage("block/Glass")),
    GLASS_BROKEN(BlockPuzzle.getInstance().getResourceImage("block/Glass_Broken")),
    PLANKS(BlockPuzzle.getInstance().getResourceImage("block/Planks")),
    KEY(BlockPuzzle.getInstance().getResourceImage("block/Key")),
    DOOR(BlockPuzzle.getInstance().getResourceImage("block/Door"));

    @Getter
    private final BufferedImage image;

    BlockType(BufferedImage image) {
        this.image = image;
    }
}
