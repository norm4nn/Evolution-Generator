package agh.ics.oop;

public interface IMapElement {

    /**
     @return current position of the element
     on the map as Vector2d class instance.
     */
    Vector2d getPosition();

    /**
     * @param position
     *                  passed position as Vector2d class instance.
     * @return true if object is currently on the passed position
     *         false if object is not.
     */
    boolean isAt(Vector2d position);

    String imgAddress();

    String toString();

}
