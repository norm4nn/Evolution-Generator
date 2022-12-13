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
//        this.app = app;
//        this.map = map;
        this.parametrs = parametrs;
    }

    @Override
    public void run() {
        if (this.app == null) {
            for(int i = 0; i < this.moves.size(); ++i)
//                System.out.println(this.map);
                this.animalArrayList.get(i % this.animalArrayList.size()).move();

            return;
        }

        System.out.println("Thread started.");
        for(int i = 0; i < this.moves.size(); ++i) {
            this.animalArrayList.get(i % this.animalArrayList.size()).move();
            Platform.runLater(this.app::draw);
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
