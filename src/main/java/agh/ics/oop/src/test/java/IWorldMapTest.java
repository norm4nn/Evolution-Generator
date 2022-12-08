package agh.ics.oop.src.test.java;

import agh.ics.oop.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IWorldMapTest {
    private AbstractWorldMap mapGrass;
    private AbstractWorldMap mapRect;
    private Vector2d[] positions;


    @BeforeAll
    void setup() {
            this.positions = new Vector2d[]{new Vector2d(0, 0),
                                            new Vector2d(2, 3),
                                            new Vector2d(5, 9),
                                            new Vector2d(1, 1)};
    }

    @BeforeEach
    void init() {
        this.mapGrass = new GrassField(10);
        this.mapRect = new RectangularMap(10, 10);
    }

    @Test
    void testCanMoveTo() {
        for (Vector2d position : this.positions) {
            assertTrue(this.mapGrass.canMoveTo(position));
            assertTrue(this.mapRect.canMoveTo(position));
        }

        for (Vector2d position : this.positions) {
            this.mapGrass.place(new Animal(this.mapGrass, position));
            this.mapRect.place(new Animal(this.mapRect, position));
        }

        for (Vector2d position : this.positions) {
            assertFalse(this.mapGrass.canMoveTo(position));
            assertFalse(this.mapRect.canMoveTo(position));

        }
    }

    @Test
    void testPlace() {
        for (Vector2d position : this.positions) {
            assertTrue(this.mapGrass.place(new Animal(this.mapGrass, position)));
            assertTrue(this.mapRect.place(new Animal(this.mapRect, position)));
        }

        for (Vector2d position : this.positions) {
            assertThrows(IllegalArgumentException.class, () -> {
                this.mapGrass.place(new Animal(this.mapGrass, position));
            });
            assertThrows(IllegalArgumentException.class, () -> {
                this.mapRect.place(new Animal(this.mapRect, position));
            });
        }
    }

    @Test
    void testIsOccupied() {
        for (Vector2d position : this.positions) {
            if (this.mapGrass.objectAt(position) instanceof Grass)
                assertTrue(this.mapGrass.isOccupied(position));
            else
                assertFalse(this.mapGrass.isOccupied(position));
            assertFalse(this.mapRect.isOccupied(position));
        }

        for (Vector2d position : this.positions) {
            this.mapGrass.place(new Animal(this.mapGrass, position));
            this.mapRect.place(new Animal(this.mapRect, position));
        }

        for (Vector2d position : this.positions) {
            assertTrue(this.mapGrass.isOccupied(position));
            assertTrue(this.mapRect.isOccupied(position));
        }
    }

    @Test
    void testObjectAt() {
        for (Vector2d position : this.positions) {
            assertTrue((this.mapGrass.objectAt(position) instanceof Grass)
                             || (this.mapGrass.objectAt(position) == null));
            assertNull(this.mapRect.objectAt(position));
        }

        for (Vector2d position : this.positions) {
            this.mapGrass.place(new Animal(this.mapGrass, position));
            this.mapRect.place(new Animal(this.mapRect, position));
        }

        for (Vector2d position : this.positions) {
            assertTrue(this.mapGrass.objectAt(position) instanceof Animal);
            assertTrue(this.mapRect.objectAt(position) instanceof Animal);
        }

    }

}
