package agh.ics.oop;


public class Plant extends AbstractWorldMapElement {
    final private Vector2d position;
    final private int providesEnergy;


    public Plant(Vector2d position, int energy) {
        this.position = position;
        this.providesEnergy = energy;
    }

    @Override
    public String imgAddress() {
        return "P";
    }

    public int getEnergy() {
        return providesEnergy;
    }

    @Override
    public String toString() {
        return "P";
    }
}
