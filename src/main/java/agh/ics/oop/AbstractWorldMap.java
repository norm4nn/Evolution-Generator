package agh.ics.oop;

import java.util.HashMap;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected HashMap<Vector2d, AbstractWorldMapElement> mapElements = new HashMap<>();
    protected abstract boolean isInScope(Vector2d position);
    public abstract Vector2d getLowerLeft();
    public abstract Vector2d getUpperRight();

    @Override
    public void  positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = (Animal)this.mapElements.get(oldPosition);
        this.mapElements.remove(oldPosition);
        this.mapElements.put(newPosition, animal);
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
        return new MapVisualizer(this).draw(this.getLowerLeft(), this.getUpperRight());
    }

}
