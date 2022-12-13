package agh.ics.oop;

public interface IPositionChangeObserver {
    /**
     *
     * @param oldPosition - old position of the map element which should be removed from hashmap
     * @param newPosition - new posiion of the map element which will be add to hashmap as new key
     */
    void positionChanged(Vector2d oldPosition, Vector2d newPosition);


}
