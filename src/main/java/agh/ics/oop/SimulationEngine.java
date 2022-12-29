package agh.ics.oop;

import com.opencsv.CSVWriter;
import java.io.FileWriter;

import agh.ics.oop.gui.App;
import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Application;
import javafx.application.Platform;

import java.io.IOException;
import java.util.*;

public class SimulationEngine implements IEngine, Runnable, IAnimalChangeObserver {
    private AbstractWorldMap map;
    List<String[]> data = new ArrayList<>();
    private final Parametrs parametrs;
    private final int delay = 2000;
    private TreeSet<Animal> animals = new TreeSet<Animal>(new Animal.myAnimalComparator());
    private final LinkedList<Animal> deadAnimals = new LinkedList<>();
    private final App app;
    private int numberOfAnimals;
    private int numberOfPlants;
    private int numberOfFreeTiles;
    private int[] mostPopularGenotype;
    private int averageEnergy;
    private int averageLifespan;


    public SimulationEngine( Parametrs parametrs, App app) throws IllegalArgumentException{
        this.app = app;
        this.parametrs = parametrs;

        String[] header = {"Liczba zwierząt", "Liczba roślin", "Liczba wolnych pól", "Najpopularniejszy genotyp", "Średni poziom energii", "Średnia długość życia"};
        data.add(header);

        this.map = new HellMap(parametrs);
            for(int i=0; i < this.parametrs.getStartingAmountOfAnimals(); i++) {
                Animal animal = new Animal(this.map, this.parametrs);
                this.map.place(animal);
                this.animals.add(animal);
            }
            this.map.growNPlants(this.parametrs.getStartingAmountOfPlants());
//        System.out.println(new MapVisualizer(this.map).draw(this.map.getLowerLeft(), this.map.getUpperRight()));
    }

//    @Override
    public void run() {
        Platform.runLater(this.app::draw);
        while (this.animals.size() > 0) {
            this.moveStage();
            this.sortTreeset();

            this.deadCheckStage();
            this.sortTreeset();

            this.eatStage();
            this.sortTreeset();

            this.breedStage();
            this.sortTreeset();

            this.mapUpdateStage();
            WriteLineCSV();
//            System.out.println(new MapVisualizer(this.map).draw(this.map.getLowerLeft(), this.map.getUpperRight()));
            Platform.runLater(this.app::draw);
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!this.app.isRunning) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
       try {CreateCSVFile();}
       catch (IOException e){
           System.out.println(e);
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
        this.deadAnimals.addAll(deadAnimals);

    }

    private void mapUpdateStage() {
        this.map.growNPlants(this.parametrs.getNumberOfGrowingPlants());
        this.map.dayPassed();
        int tempSumOfAge = 0;
        int tempSumOfEnergy = 0;
        HashMap<int[], Integer> genotypesMap = new HashMap<>();
        for(Animal animal : this.animals){
            if (genotypesMap.containsKey(animal.getGenotype().getGenotypeArray()))
                genotypesMap.put(animal.getGenotype().getGenotypeArray(), genotypesMap.get(animal.getGenotype().getGenotypeArray()) + 1);
            else
                genotypesMap.put(animal.getGenotype().getGenotypeArray(), 1);
            this.map.mapElements.put(animal.getPosition(), animal);
            tempSumOfAge += animal.getAge();
            tempSumOfEnergy += animal.getEnergy();
        }
        for(Animal animal : this.deadAnimals) {
            tempSumOfAge += animal.getAge();
        }

        this.mostPopularGenotype = genotypesMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
        this.numberOfFreeTiles = this.map.getWidth()*this.map.getHeight() -  this.map.mapElements.size();
        this.numberOfAnimals = this.animals.size();
        this.numberOfPlants = this.map.plants.size();
        this.averageEnergy = tempSumOfEnergy/this.numberOfAnimals;
        this.averageLifespan = tempSumOfAge/(this.numberOfAnimals + this.deadAnimals.size());

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

    public AbstractWorldMap getMap() {
        return this.map;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getNumberOfPlants() {
        return this.numberOfPlants;
    }

    public int getNumberOfFreeTiles() {
        return this.numberOfFreeTiles;
    }

    public int[] getMostPopularGenotype() {
        return this.mostPopularGenotype;
    }

    public int getAverageEnergy() {
        return this.averageEnergy;
    }

    public int getAverageLifespan() {
        return this.averageLifespan;
    }

    public void CreateCSVFile() throws IOException{
        if(parametrs.getSaveStatsToFile()){
            try(CSVWriter writer1 = new CSVWriter(new FileWriter("SimulationData.csv"))){
                writer1.writeAll(data);
            }
        }
    }
    public void WriteLineCSV(){
        String[] newLine = {String.valueOf(this.getNumberOfAnimals()), String.valueOf(getNumberOfPlants()), String.valueOf(getNumberOfFreeTiles()), Arrays.toString(getMostPopularGenotype()), String.valueOf(getAverageEnergy()), String.valueOf(getAverageLifespan())};
        data.add(newLine);
    }
}
