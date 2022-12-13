package agh.ics.oop;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class AbstractWorldMap implements IWorldMap{//, IPositionChangeObserver{

    protected HashMap<Vector2d, AbstractWorldMapElement> mapElements = new HashMap<>();

    protected Parametrs parametrs;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected boolean isInScope(Vector2d position) {
        return (position.follows(this.lowerLeft) && position.precedes(upperRight));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return isInScope(position) && !(objectAt(position) instanceof Animal);
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (this.canMoveTo(animal.getPosition())) {
            this.mapElements.put(animal.getPosition(), animal);
            return true;
        }
        throw new IllegalArgumentException("Couldn't place animal on position " + animal.getPosition() + ".");
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return  this.mapElements.containsKey(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.mapElements.get(position);
    }



    public String toString() {
        return new MapVisualizer(this).draw(this.lowerLeft, this.upperRight);
    }

}
