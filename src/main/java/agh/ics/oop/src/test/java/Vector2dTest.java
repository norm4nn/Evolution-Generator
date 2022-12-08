package agh.ics.oop.src.test.java;

import agh.ics.oop.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2dTest {

    @Test
    public void testEquals() {
        assertEquals(true, new Vector2d(1, 2).equals(new Vector2d(1, 2)));
        assertEquals(false, new Vector2d(1, 2).equals(new Vector2d(3, 2)));
        assertEquals(false, new Vector2d(1, 2).equals(new Object()));
    }

    @Test
    public void testHashCode() {
        assertEquals(-31, new Vector2d(0, 1).hashCode());
        assertEquals(0, new Vector2d(0, 0).hashCode());
        assertEquals(4, new Vector2d(2, 2).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("(1,-2)", new Vector2d(1, -2).toString());
        assertEquals("(0,0)", new Vector2d(0, -0).toString());
        assertEquals("(-3,1)", new Vector2d(-3, 1).toString());
    }

    @Test
    public void testPrecedes() {
        assertEquals(true, new Vector2d(0, -1).precedes(new Vector2d(1, 0)));
        assertEquals(false, new Vector2d(0, 1).precedes(new Vector2d(1, 0)));
        assertEquals(true, new Vector2d(0, 0).precedes(new Vector2d(0, 0)));
    }

    @Test
    public void testFollows() {
        assertEquals(true, new Vector2d(1, 0).follows(new Vector2d(0, -1)));
        assertEquals(false, new Vector2d(1, 0).follows(new Vector2d(0, 1)));
        assertEquals(true, new Vector2d(0, 0).follows(new Vector2d(0, 0)));
    }

    @Test
    public void testUpperRight() {
        assertEquals(new Vector2d(2, 2), new Vector2d(-2, 2).upperRight(new Vector2d(2, -2)));
        assertEquals(new Vector2d(0, 1), new Vector2d(0, -1).upperRight(new Vector2d(0, 1)));
        assertEquals(new Vector2d(0, 0), new Vector2d(0, 0).upperRight(new Vector2d(0, 0)));
    }

    @Test
    public void testLowerLeft() {
        assertEquals(new Vector2d(-2, -2), new Vector2d(-2, 2).lowerLeft(new Vector2d(2, -2)));
        assertEquals(new Vector2d(0, -1), new Vector2d(0, 1).lowerLeft(new Vector2d(0, -1)));
        assertEquals(new Vector2d(0, 0), new Vector2d(0, 0).lowerLeft(new Vector2d(0, 0)));
    }

    @Test
    public void testAdd() {
        assertEquals(new Vector2d(0, 1), new Vector2d(1, 0).add(new Vector2d(-1, 1)));
        assertEquals(new Vector2d(0, 0), new Vector2d(1, 1).add(new Vector2d(-1, -1)));
        assertEquals(new Vector2d(2, -2), new Vector2d(2, -0).add(new Vector2d(0, -2)));
    }
    @Test
    public void testSubtract() {
        assertEquals(new Vector2d(2, 0), new Vector2d(0, 1).subtract(new Vector2d(-2, 1)));
        assertEquals(new Vector2d(0, 0), new Vector2d(0, 1).subtract(new Vector2d(-0, 1)));
        assertEquals(new Vector2d(1, 1), new Vector2d(-1, -2).subtract(new Vector2d(-2, -3)));
    }

    @Test
    public void testOpposite() {
        assertEquals(new Vector2d(-0, 0), new Vector2d(-0,0).opposite());
        assertEquals(new Vector2d(2, -3), new Vector2d(-2, 3).opposite());
        assertEquals(new Vector2d(-1, 0), new Vector2d(1, 0).opposite());
    }
}
