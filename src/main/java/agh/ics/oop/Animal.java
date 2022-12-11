package agh.ics.oop;


import java.util.ArrayList;

public class Animal extends AbstractWorldMapElement {

    final private ArrayList<IPositionChangeObserver> positionChangeObservers;

    final private Parametrs parametrs;
    private int age = 0;
    private int energy;
    private int amountOfChildren;
    private int dayOfDeath;
    private Genotype genotype;
    private int orientation = new MoveDirection().random();

    public Animal(AbstractWorldMap map, Parametrs parametrs) {
//        this.map = map;
        this.parametrs = parametrs;
        this.position = new Vector2d((int) (Math.random()*parametrs.getMapWidth()), (int) (Math.random()*parametrs.getMapHeight()));
        this.genotype = new Genotype(parametrs);
        this.energy = parametrs.getStartingAmountOfEnergy();

        this.positionChangeObservers = new ArrayList<>();
//        this.addObserver(this.map);
    }

    public Animal(AbstractWorldMap map, Parametrs parametrs, Animal parent1, Animal parent2) {
        this.parametrs = parametrs;
        this.position = parent1.getPosition();
        this.positionChangeObservers = new ArrayList<>();

        int sumOfEnergy = parent1.getEnergy() + parent2.getEnergy();
        if (Math.random() < 0.5) {
            float dividePoint = (float) (parent1.getEnergy())/sumOfEnergy;
            int[][] parent1Genotypes = parent1.getGenotype().divideGenotype(dividePoint);
            int[][] parent2Genotypes = parent2.getGenotype().divideGenotype(dividePoint);
            this.genotype = new Genotype(parent1Genotypes[0], parent2Genotypes[1], parametrs);
        }
        else {
            float dividePoint = 1 - (float) (parent1.getEnergy()) / sumOfEnergy;
            int[][] parent1Genotypes = parent1.getGenotype().divideGenotype(dividePoint);
            int[][] parent2Genotypes = parent2.getGenotype().divideGenotype(dividePoint);
            this.genotype = new Genotype(parent1Genotypes[1], parent2Genotypes[0], parametrs);
        }
        
    }



    public void move() {
        this.age += 1;
        this.energy -= 1;
        this.orientation += this.genotype.useGen();
        this.orientation %= 8;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.positionChangeObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.positionChangeObservers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : positionChangeObservers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    @Override
    public String imgAddress() {
        return "-";
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public int getDayOfDeath() {
        return dayOfDeath;
    }

    public int getAmountOfChildren() {
        return amountOfChildren;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }
}
