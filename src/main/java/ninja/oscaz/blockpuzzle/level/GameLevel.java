package ninja.oscaz.blockpuzzle.level;

import lombok.Getter;
import ninja.oscaz.blockpuzzle.BlockPuzzle;
import ninja.oscaz.blockpuzzle.block.Block;
import ninja.oscaz.blockpuzzle.block.BlockType;
import ninja.oscaz.blockpuzzle.logic.Direction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GameLevel {

    private final Level level;
    private final List<Block> editedBlocks;
    private int playerX;
    private int playerY;
    private final Instant gameStart;
    private int movesMade;
    private int keys;

    public GameLevel(Level level) {
        this.level = level;
        this.gameStart = Instant.now();
        this.editedBlocks = new ArrayList<>(level.getBlocks());
        this.movesMade = 0;
        this.keys = 0;
        level.getBlocks().forEach(block -> {
            if (block.getBlockType() == BlockType.SPAWN) {
                this.playerX = block.getX();
                this.playerY = block.getY();
            }
        });
    }

    private Block getBlock(int x, int y) {
        for (Block block : this.editedBlocks) {
            if (block.getX() == x && block.getY() == y) return block;
        }
        return null;
    }

    private Block getBlock(BlockType blockType) {
        return this.editedBlocks.stream().filter(block -> block.getBlockType() == blockType).findFirst().get();
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
        this.movesMade++;
        int changedX = direction.xChanged(this.playerX), changedY = direction.yChanged(this.playerY);
        if (this.getBlock(this.playerX, this.playerY).getBlockType() == BlockType.GLASS) {
            this.setBlock(this.playerX, this.playerY, BlockType.GLASS_BROKEN);
        }
        Block block = this.getBlock(changedX, changedY);
        if (block.getBlockType() == BlockType.EMPTY ||
            block.getBlockType() == BlockType.SPAWN ||
            block.getBlockType() == BlockType.GLASS ||
            block.getBlockType() == BlockType.PLANKS) {
            this.playerX = changedX;
            this.playerY = changedY;
        }
        if (block.getBlockType() == BlockType.KEY) {
            this.keys++;
            this.setBlock(block, BlockType.EMPTY);
            this.playerX = changedX;
            this.playerY = changedY;
        }
        if (block.getBlockType() == BlockType.DOOR) {
            if (this.keys > 0) {
                this.keys--;
                this.setBlock(block, BlockType.EMPTY);
                this.playerX = changedX;
                this.playerY = changedY;
            }
        }
        if (block.getBlockType() == BlockType.ORANGE_PORTAL || block.getBlockType() == BlockType.BLUE_PORTAL) {
            Block portal = this.getBlock(block.getBlockType() == BlockType.ORANGE_PORTAL ? BlockType.BLUE_PORTAL : BlockType.ORANGE_PORTAL);
            this.playerX = portal.getX();
            this.playerY = portal.getY();
        }
        if (block.getBlockType() == BlockType.MOVABLE) {
            Block movableBlockPath = this.getBlock(direction.xChanged(changedX), direction.yChanged(changedY));
            if (movableBlockPath.getBlockType() == BlockType.EMPTY ||
                movableBlockPath.getBlockType() == BlockType.SPAWN) {
                this.setBlock(block, BlockType.EMPTY);
                this.setBlock(movableBlockPath, BlockType.MOVABLE);
                this.playerX = changedX;
                this.playerY = changedY;
            }
            if (movableBlockPath.getBlockType() == BlockType.GOAL) {
                this.setBlock(block, BlockType.EMPTY);
                this.setBlock(movableBlockPath, BlockType.GOAL_COMPLETE);
                this.playerX = changedX;
                this.playerY = changedY;
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
        if (block.getBlockType() == BlockType.ORANGE_PORTAL) return true;
        if (block.getBlockType() == BlockType.BLUE_PORTAL) return true;
        if (block.getBlockType() == BlockType.GLASS) return true;
        if (block.getBlockType() == BlockType.PLANKS) return true;
        if (block.getBlockType() == BlockType.KEY) return true;
        if (block.getBlockType() == BlockType.DOOR && this.keys > 0) return true;
        if (block.getBlockType() == BlockType.MOVABLE) {
            int pathX = direction.xChanged(changedX), pathY = direction.yChanged(changedY);
            if (pathX < 0 || pathX > 9 || pathY < 0 || pathY > 9) return false;
            Block movableBlockPath = this.getBlock(pathX, pathY);
            if (movableBlockPath.getBlockType() == BlockType.EMPTY) return true;
            if (movableBlockPath.getBlockType() == BlockType.GOAL) return true;
            if (movableBlockPath.getBlockType() == BlockType.SPAWN) return true;
        }
        return false;
    }

    public boolean isGameOver() {
        for (Block block : editedBlocks) {
            if (block.getBlockType() == BlockType.GOAL) {
                return false;
            }
        }
        return true;
    }

}
