package ninja.oscaz.blockpuzzle.game;

public enum Direction {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int xChange;
    private final int yChange;

    Direction(int xChange, int yChange) {
        this.xChange = xChange;
        this.yChange = yChange;
    }

    public int returnX(int x) {
        return x + this.xChange;
    }

    public int returnY(int y) {
        return y + this.yChange;
    }

}
