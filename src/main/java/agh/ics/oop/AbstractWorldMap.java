package agh.ics.oop;
import java.util.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{

    protected HashMap<Vector2d, AbstractWorldMapElement> mapElements = new HashMap<>();
    protected HashMap<Vector2d, Plant> plants = new HashMap<>();
    protected Map<Vector2d, IMapTile> tiles;
    protected Parametrs parametrs;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected HashSet<Vector2d> favouritesPlacesForPlants;
    protected HashSet<Vector2d> notFavouritePlacesForPlants;
    protected int day;

    protected int width;
    protected int height;



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

    public boolean isTherePlant(Vector2d pos){
        return (objectAt(pos) instanceof Plant);
    }

    abstract Map<Vector2d, IMapTile> generateTiles();

    public IMapTile getTileAtPosition(Vector2d pos){
        return tiles.get(pos);
    }

    public void growNPlants(int n){
        for (int i = 0; i < 0.8*n; i++) {
                Vector2d pos = new Vector2d((int) (Math.random()*parametrs.getMapWidth()), (int) (Math.random()*parametrs.getMapHeight()));
                while(!(isOccupied(pos)||getTileAtPosition(pos)instanceof JungleTile)){
                    pos = new Vector2d((int) (Math.random()*parametrs.getMapWidth()), (int) (Math.random()*parametrs.getMapHeight()));
                }
                Plant p = new Plant(pos, parametrs.getEnergyFromPlant());
                mapElements.put(pos,p);
            }
        for (int i = (int) Math.floor(0.8*n); i<n; i++){
                Vector2d pos = new Vector2d((int) (Math.random()*parametrs.getMapWidth()), (int) (Math.random()*parametrs.getMapHeight()));
                while(isOccupied(pos)&& !(getTileAtPosition(pos)instanceof PlainsTile)){
                    pos = new Vector2d((int) (Math.random()*parametrs.getMapWidth()), (int) (Math.random()*parametrs.getMapHeight()));
                }
                Plant p = new Plant(pos, parametrs.getEnergyFromPlant());
                mapElements.put(pos,p);
        }
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
