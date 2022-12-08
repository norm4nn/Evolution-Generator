package agh.ics.oop.src.test.java;

import agh.ics.oop.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RectMapTest {

    private AbstractWorldMap[] answerMaps;

    @BeforeAll
    void setupAnswers() {
        this.answerMaps = new AbstractWorldMap[4];

//        TEST - 0
        this.answerMaps[0] = new RectangularMap(10, 5);
        Animal animal01 = new Animal(this.answerMaps[0], new Vector2d(2, 0));
        animal01.move(MoveDirection.LEFT);
        animal01.move(MoveDirection.LEFT);
        this.answerMaps[0].place(animal01);
        this.answerMaps[0].place(new Animal(this.answerMaps[0], new Vector2d(3, 4)));


//        TEST - 1
        this.answerMaps[1] = new RectangularMap(1, 1);
        Animal animal11 = new Animal(this.answerMaps[1], new Vector2d(0, 0));
        animal11.move(MoveDirection.LEFT);
        animal11.move(MoveDirection.LEFT);
        this.answerMaps[1].place(animal11);

//        TEST - 2
        this.answerMaps[2] = new RectangularMap(3, 2);
        this.answerMaps[2].place(new Animal(this.answerMaps[2], new Vector2d(0, 0)));
        this.answerMaps[2].place(new Animal(this.answerMaps[2], new Vector2d(2, 0)));


//        TEST - 3
        this.answerMaps[3] = new RectangularMap(300, 300);
        for(int i = 0; i < 100; ++i) {
            this.answerMaps[3].place(new Animal(this.answerMaps[3], new Vector2d(i,299)));
        }
    }


    @Test
    void testMap0() {
        MoveDirection[] directions = new OptionsParser().parse("f b r l f f r r f f f f f f f f".split(" "));
        AbstractWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions, null);
        engine.run();
        assertEquals(this.answerMaps[0].toString(), map.toString());
    }

    @Test
    void testMap1() {
        MoveDirection[] directions = new OptionsParser().parse("f b r l f f r r f f f f f f f f l r".split(" "));
        AbstractWorldMap map = new RectangularMap(1, 1);
        Vector2d[] positions = {  new Vector2d(0, 0) };
        IEngine engine = new SimulationEngine(directions, map, positions, null);
        engine.run();
        assertEquals(this.answerMaps[1].toString(), map.toString());
    }

    @Test
    void testMap2() {
        MoveDirection[] directions = new OptionsParser().parse("b b b b l r f f f f r l".split(" "));
        AbstractWorldMap map = new RectangularMap(3, 2);
        Vector2d[] positions = { new Vector2d(1,0), new Vector2d(2, 1)};
        IEngine engine = new SimulationEngine(directions, map, positions, null);
        engine.run();
        assertEquals(this.answerMaps[2].toString(), map.toString());
    }

    @Test
    void testMap3() {
        String args = "l ".repeat(100) +
                "f ".repeat(100 * 200) +
                "r ".repeat(100) +
                "f ".repeat(100 * 300);
        MoveDirection[] directions = new OptionsParser().parse(args.split(" "));
        AbstractWorldMap map = new RectangularMap(300, 300);
        Vector2d[] positions = new Vector2d[100];
        for(int i = 0; i < 100; ++i) {
            positions[i] = new Vector2d(200 + i, 0);
        }
        IEngine engine = new SimulationEngine(directions, map, positions, null);
        engine.run();
        assertEquals(this.answerMaps[3].toString(), map.toString());
    }

}
