package agh.ics.oop;

public class MoveDirection {
    private int numberOfDirections = 8;
    private int value;

    public MoveDirection() {
        this.value = (int) (Math.random() * this.numberOfDirections);
    }

    public void add(int direction) {
        this.value += direction;
        this.value %= numberOfDirections;
    }

    public Vector2d parseToVector2d() {
        return switch (value) {
            case 0 -> new Vector2d(0, 1);
            case 1 -> new Vector2d(1, 1);
            case 2 -> new Vector2d(1, 0);
            case 3 -> new Vector2d(1, -1);
            case 4 -> new Vector2d(0, -1);
            case 5 -> new Vector2d(-1, -1);
            case 6 -> new Vector2d(-1, 0);
            case 7 -> new Vector2d(-1, 1);
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

}
