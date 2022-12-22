package agh.ics.oop;

import java.util.*;

public class SimulationEngine implements IEngine, Runnable, IAnimalChangeObserver {
    private AbstractWorldMap map;
//    private final App app;
    private final Parametrs parametrs;
    private final int delay = 1000;
    private TreeSet<Animal> animals = new TreeSet<Animal>(new Animal.myAnimalComparator());

    public SimulationEngine( Parametrs parametrs) throws IllegalArgumentException {

        this.parametrs = parametrs;
        this.map = new HellMap(parametrs);

            for(int i=0; i < this.parametrs.getStartingAmountOfAnimals(); i++) {
                Animal animal = new Animal(this.map, this.parametrs);
                this.map.place(animal);
                this.animals.add(animal);
            }
            map.growNPlants();
    }

//    @Override
    public void run() {
        while (true) {
            System.out.println(new MapVisualizer(this.map).draw(this.map.getLowerLeft(), this.map.getUpperRight()));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.moveStage();
            this.sortTreeset();
            this.eatStage();
        }
    }

    private void eatStage() {
        for(Animal animal : this.animals) {
            if (this.map.isTherePlant(animal.getPosition()) instanceof Plant plant)
                animal.eat(plant);
        }
    }

    private void deadCheckStage() {
        this.animals.removeIf(animal -> !animal.isAlive());

        MapVisualizer visualizer = new MapVisualizer(map);
        System.out.println(visualizer.draw(map.getLowerLeft(),map.getUpperRight()));
    }

    private void moveStage() {
        for (Animal animal : this.animals) {
            animal.move();
        }
    }

    private void sortTreeset() {
        TreeSet<Animal> tempAnimals = new TreeSet<Animal>(new Animal.myAnimalComparator());
        tempAnimals.addAll(this.animals);
        this.animals = (TreeSet<Animal>) tempAnimals.clone();
    }

    @Override
    public void animalChange(Animal oldAnimal, Animal newAnimal) {
        this.animals.remove(oldAnimal);
        this.animals.add(newAnimal);
    }


}
