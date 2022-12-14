package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{//, IPositionChangeObserver{

    protected HashMap<Vector2d, AbstractWorldMapElement> mapElements = new HashMap<>();
    protected HashMap<Vector2d, Plant> plants = new HashMap<>();
    protected Parametrs parametrs;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected HashSet<Vector2d> favouritesPlacesForPlants;
    protected HashSet<Vector2d> notFavouritePlacesForPlants;

    protected int day;


    public void growPlants() {
        ArrayList<Vector2d> favList = new ArrayList<>(this.favouritesPlacesForPlants);
        ArrayList<Vector2d> notFavList = new ArrayList<>(this.notFavouritePlacesForPlants);
        float whichPlace;
        int randInt;
        for(int i=0;i<this.parametrs.getNumberOfGrowingPlants();++i) {
            whichPlace = (float) Math.random();
            if (whichPlace <= 0.8f && favList.size() > 0) {
                randInt = (int) (Math.random() * favList.size());

                this.plants.put(favList.get(randInt), new Plant(favList.get(randInt), this.parametrs.getEnergyFromPlant()));
            }

        }
    }

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


    public Vector2d getLowerLeft() { return this.lowerLeft; }
    public Vector2d getUpperRight() { return this.upperRight; }

    public void dayPassed() {
        this.day += 1;
    }

    public int getDay() {
        return this.day;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = (Animal)this.mapElements.get(oldPosition);
        this.mapElements.remove(oldPosition);
        this.mapElements.put(newPosition, animal);
    }


}
