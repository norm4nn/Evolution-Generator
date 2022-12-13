package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.*;

public class SimulationEngine implements IEngine, Runnable {
    private AbstractWorldMap map;
//    private final App app;
    private final Parametrs parametrs;
    private int delay = 300;

    protected TreeSet<Animal> animals = new TreeSet<>(new Comparator<Animal>() {
        @Override
        public int compare(Animal a1, Animal a2) {
            if (a1.getEnergy() == a2.getEnergy()) {
                if (a1.getAge() == a2.getAge())
                    return a1.getAmountOfChildren() - a2.getAmountOfChildren();
                return a1.getAge() - a2.getAge();
            }
            return a1.getEnergy() - a2.getEnergy();
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

}
