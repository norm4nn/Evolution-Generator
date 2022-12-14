package agh.ics.oop;

public class PlainsTile implements IMapTile{
    private Vector2d position;

    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return " ";
    }
}
