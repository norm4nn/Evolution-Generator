package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.*;

public class SimulationEngine implements IEngine, Runnable, IAnimalChangeObserver {
    private AbstractWorldMap map;
//    private final App app;
    private final Parametrs parametrs;
    private final int delay = 300;

    protected TreeSet<Animal> animals = new TreeSet<>(new Comparator<Animal>() {
        @Override
        public int compare(Animal a1, Animal a2) {
            if (a1.getPosition().equals(a2.getPosition())) {
                if (a1.getEnergy() == a2.getEnergy()) {
                    if (a1.getAge() == a2.getAge())
                        return a1.getAmountOfChildren() - a2.getAmountOfChildren();
                    return a1.getAge() - a2.getAge();
                }
                return a1.getEnergy() - a2.getEnergy();
            }
            if (a1.getPosition().getX() == a2.getPosition().getX())
                return a1.getPosition().getY() - a2.getPosition().getY();
            return a1.getPosition().getX() - a2.getPosition().getX();
            }

    });

    public SimulationEngine( Parametrs parametrs) throws IllegalArgumentException {

        this.parametrs = parametrs;

        this.map = new GlobeMap(this.parametrs.getMapWidth(), this.parametrs.getMapHeight());
        for(int i=0; i < this.parametrs.getStartingAmountOfAnimals(); ++i) {
            Animal animal = new Animal(this.map, this.parametrs);
            this.map.place(animal);
            this.animals.add(animal);
        }
    }

    @Override
    public void run() {
        
    }

    @Override
    public void animalChange(Animal oldAnimal, Animal newAnimal) {
        this.animals.remove(oldAnimal);
        this.animals.add(newAnimal);
    }
}
