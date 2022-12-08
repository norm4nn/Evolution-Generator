package agh.ics.oop;


import java.util.ArrayList;

public class Animal extends AbstractWorldMapElement {
    private MapDirection orientation = MapDirection.NORTH;
    final private AbstractWorldMap map;
    final private ArrayList<IPositionChangeObserver> positionChangeObservers;

    public Animal(AbstractWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
        this.positionChangeObservers = new ArrayList<>();
        this.addObserver(this.map);
    }

    public Animal() {
        this.position = new Vector2d(2, 2);
        this.map = new RectangularMap(5, 5);
        this.positionChangeObservers = new ArrayList<>();
        this.addObserver(this.map);
    }

    public String toString() {
        return switch (this.orientation) {
            case NORTH -> "^";
            case SOUTH -> "V";
            case WEST -> "<";
            case EAST -> ">";
        };
    }

    public void move(MoveDirection direction) {
        if (direction.equals(MoveDirection.LEFT) || direction.equals(MoveDirection.RIGHT)){
            switch (direction) {
                case LEFT -> this.orientation = this.orientation.previous();
                case RIGHT -> this.orientation = this.orientation.next();
            }
            return;
        }

        Vector2d modifier = this.orientation.toUnitVector();
        if (direction.equals(MoveDirection.BACKWARD)) {
            modifier = this.orientation.toUnitVector().opposite();
        }

        final Vector2d newPosition = this.position.add(modifier);

        if (this.map.canMoveTo(newPosition)) {
            this.positionChanged(this.position, newPosition);
            this.position = newPosition;
        }
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.positionChangeObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.positionChangeObservers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : positionChangeObservers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    @Override
    public String imgAddress() {
        return switch (this.orientation) {
            case NORTH -> "src/main/resources/up.png";
            case EAST -> "src/main/resources/right.png";
            case SOUTH -> "src/main/resources/down.png";
            case WEST -> "src/main/resources/left.png";
        };
    }
}
