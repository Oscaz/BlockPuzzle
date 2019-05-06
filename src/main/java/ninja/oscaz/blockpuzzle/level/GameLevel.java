package ninja.oscaz.blockpuzzle.level;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;
import ninja.oscaz.blockpuzzle.game.Direction;

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

    public Block getBlock(int x, int y) {
        for (Block block : this.editedBlocks) {
            if (block.getX() == x && block.getY() == y) return block;
        }
        return null;
    }

    public void setBlock(int x, int y, BlockType blockType) {
        this.setBlock(this.getBlock(x, y), blockType);
    }

    public void setBlock(Block block, BlockType blockType) {
        this.editedBlocks.remove(block);
        this.editedBlocks.add(new Block(blockType, block.getX(), block.getY()));
    }

    public boolean executeMove(Direction direction) {
        if (!this.isMoveLegal(this.playerX, this.playerY, direction)) {
            BlockPuzzle.getInstance().playSound("WallBump");
            return false;
        }
        int changedX = direction.xChanged(this.playerX), changedY = direction.yChanged(this.playerY);
        Block block = this.getBlock(changedX, changedY);
        if (block.getBlockType() == BlockType.EMPTY ||
            block.getBlockType() == BlockType.SPAWN) {
            this.playerX = changedX;
            this.playerY = changedY;
        }
        if (block.getBlockType() == BlockType.MOVABLE) {
            Block movableBlockPath = this.getBlock(direction.xChanged(changedX), direction.yChanged(changedY));
            if (movableBlockPath.getBlockType() == BlockType.EMPTY ||
                movableBlockPath.getBlockType() == BlockType.SPAWN) {
                this.setBlock(block, BlockType.EMPTY);
                this.setBlock(movableBlockPath, BlockType.MOVABLE);
            }
            if (movableBlockPath.getBlockType() == BlockType.GOAL) {
                this.setBlock(block, BlockType.EMPTY);
                this.setBlock(movableBlockPath, BlockType.GOAL_COMPLETE);
            }
        }
        return true;
    }

    private boolean isMoveLegal(int x, int y, Direction direction) {
        int changedX = direction.xChanged(x), changedY = direction.yChanged(y);
        if (changedX < 0 || changedX > 9 || changedY < 0 || changedY > 9) return false;
        Block block = this.getBlock(changedX, changedY);
        if (block.getBlockType() == BlockType.SPAWN) return true;
        if (block.getBlockType() == BlockType.EMPTY) return true;
        if (block.getBlockType() == BlockType.MOVABLE) {
            Block movableBlockPath = this.getBlock(direction.xChanged(changedX), direction.yChanged(changedY));
            if (movableBlockPath.getBlockType() == BlockType.EMPTY) return true;
            if (movableBlockPath.getBlockType() == BlockType.GOAL) return true;
            if (movableBlockPath.getBlockType() == BlockType.SPAWN) return true;
        }
        return false;
    }

}
