package agh.ics.oop;

public class MoveDirection {
    private int numberOfDirections = 8;
    public int random() {
        return (int) (Math.random() * this.numberOfDirections);
    }
}
