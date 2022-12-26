package agh.ics.oop;
import java.awt.*;
import java.util.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected HashMap<Vector2d, AbstractWorldMapElement> mapElements = new HashMap<>();
    public HashMap<Vector2d, Plant> plants = new HashMap<>();
    protected HashMap<Vector2d, IMapTile> tiles;
    protected HashMap<Vector2d, IMapTile> jungleTiles;
    protected HashMap<Vector2d, IMapTile> plainTiles;
    protected Parametrs parametrs;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected int day;
    protected int width;
    protected int height;

    protected boolean isInScope(Vector2d position) {
        return (position.follows(this.lowerLeft) && position.precedes(upperRight));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return isInScope(position); //&& !(objectAt(position) instanceof Animal);
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

    abstract public void reactToGoingOut(Animal a);

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
    public Object isTherePlant(Vector2d pos){
        return this.plants.get(pos);
    }
    abstract ArrayList<HashMap<Vector2d, IMapTile>> generateTiles();
    public IMapTile getTileAtPosition(Vector2d pos){
        return tiles.get(pos);
    }
    public void growNPlants(int n) {
//        int n = this.parametrs.getNumberOfGrowingPlants();
        for (int i = 0; i < n; i++) {
            float randF = (float) (Math.random());
            if (randF <= 0.8f) {
                if (this.jungleTiles.size() > 0)
                    this.growOnJungleTile();
                else
                    this.growOnPlainTile();
            } else {
                if (this.plainTiles.size() > 0)
                    this.growOnPlainTile();
                else
                    this.growOnJungleTile();
            }
        }
    }

    private void growOnJungleTile() {
        if (this.jungleTiles.size() <= 0)   return;
        List<Vector2d> keysAsArray = new ArrayList<>(this.jungleTiles.keySet());
        Random r = new Random();
        Vector2d pos = keysAsArray.get(r.nextInt(keysAsArray.size()));
        this.jungleTiles.remove(pos);
        this.plants.put(pos, new Plant(pos, this.parametrs.getEnergyFromPlant()));
    }

    private void growOnPlainTile() {
        if (this.plainTiles.size() <= 0)   return;
        List<Vector2d> keysAsArray = new ArrayList<>(this.plainTiles.keySet());
        Random r = new Random();
        Vector2d pos = keysAsArray.get(r.nextInt(keysAsArray.size()));
        this.plainTiles.remove(pos);
        this.plants.put(pos, new Plant(pos, this.parametrs.getEnergyFromPlant()));
    }


    public void plantGotEaten(Vector2d pos) {
        this.plants.remove(pos);
    }
    public void removeAnimal(Vector2d pos) {
        this.mapElements.remove(pos);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
//    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
//        Animal a = (Animal) objectAt(oldPosition);
//        Animal a2 = null;
//        Plant p = null;
//        mapElements.remove(oldPosition, a);
//        if(objectAt(newPosition) instanceof Animal){
//            a2 = (Animal) mapElements.get(newPosition);
//        }
//        if(isTherePlant(newPosition)){
//            p = (Plant) mapElements.get(newPosition);
//        }
//        if(a2!=null){
//            if(a.isFedUp() && a2.isFedUp()){
//                Animal a3 = new Animal(this,parametrs, a, a2);
//                mapElements.put(newPosition, a3);
//            }
//        }
//        if(p!=null){
//            mapElements.remove(newPosition,p);
//            a.eat(p);
//        }
//        if(isInScope(newPosition)){
//                mapElements.put(newPosition, a);
//        }
//        else{
//            reactToGoingOut(a);
//        }
//    }
}
