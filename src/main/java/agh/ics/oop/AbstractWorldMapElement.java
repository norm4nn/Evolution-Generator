package agh.ics.oop;

abstract class AbstractWorldMapElement implements IMapElement{
    protected Vector2d position;
    protected AbstractWorldMap map;

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }




}
