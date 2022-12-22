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
        while (this.animals.size() > 0) {

//            System.out.println(new MapVisualizer(this.map).draw(this.map.getLowerLeft(), this.map.getUpperRight()));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.moveStage();
            this.sortTreeset();

            this.deadCheckStage();
            this.sortTreeset();

            this.eatStage();
            this.sortTreeset();

            this.breedStage();
            this.sortTreeset();



            this.mapUpdateStage();
            System.out.println(new MapVisualizer(this.map).draw(this.map.getLowerLeft(), this.map.getUpperRight()));
            System.out.println(this.map.plants.size());
        }
    }

    private void eatStage() {
        for(Animal animal : this.animals) {
            if (this.map.isTherePlant(animal.getPosition()) instanceof Plant plant)
                animal.eat(plant);
        }
    }

    private void breedStage() {
        Object prev = null;
        LinkedList<Animal> babies = new LinkedList<>();
        for(Animal animal : this.animals) {
            if (prev instanceof Animal prevAnimal && prevAnimal.getPosition().equals(animal.getPosition()) && prevAnimal.canBreed() && animal.canBreed()) {
                Animal babyAnimal = new Animal(this.map, this.parametrs, prevAnimal, animal);
                this.map.place(babyAnimal);
                babies.push(babyAnimal);
            }
            prev = animal;
        }
        this.animals.addAll(babies);
    }

    private void deadCheckStage() {
        LinkedList<Animal> deadAnimals = new LinkedList<>();
        for(Animal animal : this.animals) {
//            System.out.println(animal.getEnergy());
            if (!animal.isAlive()) {
                deadAnimals.push(animal);
                this.map.removeAnimal(animal.getPosition());

            }
        }
        this.animals.removeAll(deadAnimals);

    }

    private void mapUpdateStage() {
        this.map.growNPlants();
        this.map.dayPassed();
    }

    private void moveStage() {
        for (Animal animal : this.animals) {
            animal.move();
        }
    }

    private void sortTreeset() {
        TreeSet<Animal> tempAnimals = new TreeSet<>(new Animal.myAnimalComparator());
        tempAnimals.addAll(this.animals);
        this.animals = (TreeSet<Animal>) tempAnimals.clone();
    }

    @Override
    public void animalChange(Animal oldAnimal, Animal newAnimal) {
        this.animals.remove(oldAnimal);
        this.animals.add(newAnimal);
    }




}
