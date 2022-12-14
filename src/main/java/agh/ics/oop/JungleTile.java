package agh.ics.oop;

public class JungleTile implements IMapTile{

    private Vector2d position;
    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return "J";
    }
}
