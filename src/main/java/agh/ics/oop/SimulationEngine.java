package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class SimulationEngine implements IEngine, Runnable {

    private ArrayList<MoveDirection> moves = new ArrayList<>();
    private AbstractWorldMap map;
    private ArrayList<Animal> animalArrayList;

    private final App app;

    private int delay = 300;

    private void setAnimals(Vector2d[] initialPositions) {
        LinkedList<Animal> animalLinkedList = new LinkedList<>();
        for (Vector2d position : initialPositions) {
            if (map.canMoveTo(position)) {
                animalLinkedList.add(new Animal(this.map, position));
                this.map.place(animalLinkedList.getLast());
            } else
                throw new IllegalArgumentException("Couldn't place animal on position " + position + ".");
        }

        this.animalArrayList = new ArrayList<>(animalLinkedList);
    }

    public SimulationEngine(AbstractWorldMap map, Vector2d[] initialPositions, App app) {
        this.moves = new ArrayList<>();
        this.map = map;
        this.app = app;

        this.setAnimals(initialPositions);
    }

    public SimulationEngine(MoveDirection[] moves, AbstractWorldMap map, Vector2d[] initialPositions, App app) throws IllegalArgumentException {
        Collections.addAll(this.moves, moves);
        this.app = app;
        this.map = map;

        this.setAnimals(initialPositions);
    }

    @Override
    public void run() {
        if (this.app == null) {
            for(int i = 0; i < this.moves.size(); ++i)
//                System.out.println(this.map);
                this.animalArrayList.get(i % this.animalArrayList.size()).move(this.moves.get(i));

            return;
        }

        System.out.println("Thread started.");
        Platform.runLater(this.app::draw);
        for(int i = 0; i < this.moves.size(); ++i) {
            this.animalArrayList.get(i % this.animalArrayList.size()).move(this.moves.get(i));
            Platform.runLater(this.app::draw);
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setDirection (MoveDirection[] direction) {
        this.moves.clear();
        Collections.addAll(this.moves, direction);
    }

}
