package ninja.oscaz.blockpuzzle.level;

import com.sun.javafx.scene.traversal.Direction;
import lombok.Getter;
import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;

import java.util.List;

@Getter
public class GameLevel {

    private final Level level;
    private final List<Block> editedBlocks;
    private int playerX;
    private int playerY;

    public GameLevel(Level level) {
        this.level = level;
        this.editedBlocks = level.getBlocks();
        level.getBlocks().forEach(block -> {
            if (block.getBlockType() == BlockType.SPAWN) {
                this.playerX = block.getX();
                this.playerY = block.getY();
            }
        });
    }

    public boolean executeMove(Direction direction) {
        if (this.isMoveLegal(playerX, playerY, direction)) {
            return true;
        }
        return false;
    }

    private boolean isMoveLegal(int x, int y, Direction direction) {
        return true;
    }

}
