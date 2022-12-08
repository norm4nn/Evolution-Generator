package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {

    final private TreeSet<Vector2d> OY = new TreeSet<>(new Comparator<Vector2d>() {
        @Override
        public int compare(Vector2d o1, Vector2d o2) {
            if (o1.getY() != o2.getY())
                return o1.getY() - o2.getY();
            return  o1.getX() - o2.getX();
        }
    });
    final private TreeSet<Vector2d> OX = new TreeSet<>(new Comparator<Vector2d>() {
        @Override
        public int compare(Vector2d o1, Vector2d o2) {
            if (o1.getX() != o2.getX())
                return o1.getX() - o2.getX();
            return  o1.getY() - o2.getY();
        }
    });

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        this.OX.remove(oldPosition);
        this.OY.remove(oldPosition);

        this.addPosition(newPosition);
    }
    public void addPosition(Vector2d element) {
        this.OX.add(element);
        this.OY.add(element);
    }

    public Vector2d getLowerLeft() {
        return new Vector2d(this.OX.first().getX(), this.OY.first().getY());
    }

    public Vector2d getUpperRight() {
        return new Vector2d(this.OX.last().getX(), this.OY.last().getY());
    }
}
