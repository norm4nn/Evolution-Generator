package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Platform;

import java.util.*;

public class SimulationEngine implements IEngine, Runnable, IAnimalChangeObserver {
    private AbstractWorldMap map;
//    private final App app;
    private final Parametrs parametrs;
    private final int delay = 300;

    protected TreeSet<Animal> animals = new TreeSet<Animal>(new Comparator<Animal>() {
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
<<<<<<< HEAD

        this.map = new GlobeMap(this.parametrs);
        for(int i=0; i < this.parametrs.getStartingAmountOfAnimals(); ++i) {
=======
        this.map = new HellMap(this.parametrs.getMapWidth(), this.parametrs.getMapHeight(), parametrs);
        for(int i=0; i < this.parametrs.getStartingAmountOfAnimals(); i++) {
>>>>>>> f32ec631965c367e26b071a985a55dda77ceda94
            Animal animal = new Animal(this.map, this.parametrs);
            this.map.place(animal);
            this.animals.add(animal);
        }
        map.growNPlants(parametrs.getNumberOfGrowingPlants());
    }

    @Override
    public void run() {
<<<<<<< HEAD
        for(Animal animal : this.animals) {

        }
    }

    public void deadCheck() {
        for(Animal animal : this.animals) {
            animal.isAlive();
        }
=======
        MapVisualizer visualizer = new MapVisualizer(map);
        System.out.println(visualizer.draw(map.getLowerLeft(),map.getUpperRight()));
>>>>>>> f32ec631965c367e26b071a985a55dda77ceda94
    }

    @Override
    public void animalChange(Animal oldAnimal, Animal newAnimal) {
        this.animals.remove(oldAnimal);
        this.animals.add(newAnimal);
    }


}
