package ninja.oscaz.blockpuzzle.block;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;

import java.awt.image.BufferedImage;

public enum BlockType {

    IMMOVABLE(BlockPuzzle.getInstance().getResourceImage("block/Immovable"), true),
    MOVABLE(BlockPuzzle.getInstance().getResourceImage("block/Movable"), true),
    SPAWN(BlockPuzzle.getInstance().getResourceImage("block/Spawn"), true),
    GOAL(BlockPuzzle.getInstance().getResourceImage("block/Goal"), true),
    EMPTY(BlockPuzzle.getInstance().getResourceImage("block/Empty"), false),
    GOAL_COMPLETE(BlockPuzzle.getInstance().getResourceImage("block/Goal_Complete"), false),
    ORANGE_PORTAL(BlockPuzzle.getInstance().getResourceImage("block/Orange_Portal"), true),
    BLUE_PORTAL(BlockPuzzle.getInstance().getResourceImage("block/Blue_Portal"), true),
    GLASS(BlockPuzzle.getInstance().getResourceImage("block/Glass"), true),
    GLASS_BROKEN(BlockPuzzle.getInstance().getResourceImage("block/Glass_Broken"), false),
    PLANKS(BlockPuzzle.getInstance().getResourceImage("block/Planks"), true),
    KEY(BlockPuzzle.getInstance().getResourceImage("block/Key"), true),
    DOOR(BlockPuzzle.getInstance().getResourceImage("block/Door"), true),
    REMOVE(BlockPuzzle.getInstance().getResourceImage("block/Remove"), true);

    @Getter private final BufferedImage image;
    @Getter private final boolean editUsable;

    BlockType(BufferedImage image, boolean editUsable) {
        this.image = image;
        this.editUsable = editUsable;
    }
}
