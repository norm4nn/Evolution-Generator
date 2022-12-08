package agh.ics.oop;

public class Vector2d {
    private final int x;
    private final int y;
    private final int hash = 31;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    public String toString() {
        return "("+this.x+","+this.y+")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        final int newX = this.x + other.x;
        final int newY = this.y + other.y;

        return new Vector2d(newX, newY);
    }

    public Vector2d subtract(Vector2d other) {
        final int newX = this.x - other.x;
        final int newY = this.y - other.y;

        return new Vector2d(newX, newY);
    }

    public Vector2d upperRight(Vector2d other) {
        final int newX = Math.max(this.x, other.x);
        final int newY = Math.max(this.y, other.y);

        return new Vector2d(newX, newY);
    }

    public Vector2d lowerLeft(Vector2d other) {
        final int newX = Math.min(this.x, other.x);
        final int newY = Math.min(this.y, other.y);

        return new Vector2d(newX, newY);
    }

    public Vector2d opposite() {
        final int newX = -this.x;
        final int newY = -this.y;

        return new Vector2d(newX, newY);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;

        final Vector2d that = (Vector2d) other;

        return this.x == that.x && this.y == that.y;
    }

    public int hashCode() {
        return this.hash * (this.x + this.y) * (this.x - this.y) + this.x * this.y;
    }
}
