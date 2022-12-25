package agh.ics.oop;


import java.util.ArrayList;
import java.util.Comparator;

public class Animal extends AbstractWorldMapElement {

    final private ArrayList<IPositionChangeObserver> positionChangeObservers;
    final public int id;
    final private Parametrs parametrs;
    private int age = 0;
    private int energy;
    private int amountOfChildren;
    private int dayOfDeath;
    final private Genotype genotype;
    private MoveDirection direction;
    private boolean breeded;

    private int numberOfEatenPlants;

    public Animal(AbstractWorldMap map, Parametrs parametrs) {
        this.id = parametrs.getIdForAnimal();
        this.map = map;
        this.direction = new MoveDirection();
        this.dayOfDeath = -1;
        this.numberOfEatenPlants = 0;
        this.parametrs = parametrs;
        this.amountOfChildren = 0;
        this.age = 0;
        this.position = new Vector2d((int) (Math.random()*parametrs.getMapWidth()), (int) (Math.random()*parametrs.getMapHeight()));
        this.genotype = new Genotype(parametrs);
        this.energy = parametrs.getStartingAmountOfEnergy();
        this.positionChangeObservers = new ArrayList<>();
        this.addObserver(this.map);
        this.breeded = false;
    }

    public Animal(AbstractWorldMap map, Parametrs parametrs, Animal parent1, Animal parent2) {
        this.id = parametrs.getIdForAnimal();
        this.direction = new MoveDirection();
        this.map = map;
        this.dayOfDeath = -1;
        this.parametrs = parametrs;
        this.position = parent1.getPosition();
        this.amountOfChildren = 0;
        this.numberOfEatenPlants = 0;
        this.age = 0;
        this.energy = this.parametrs.getUsedEnergyToBreed() * 2;
        this.positionChangeObservers = new ArrayList<>();
        this.addObserver(this.map);
        this.breeded = false;

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
        parent1.loseEnergy(this.parametrs.getUsedEnergyToBreed());
        parent2.loseEnergy(this.parametrs.getUsedEnergyToBreed());
        parent1.amountOfChildren += 1;
        parent2.amountOfChildren += 1;
        parent1.breeded = true;
        parent2.breeded = true;
    }

    public boolean isAlive() {
        if (this.dayOfDeath != -1) return false;
        if (this.energy <= 0) {
            this.dayOfDeath = this.map.getDay();
            return false;
        }
        return true;
    }

    public void eat(Plant plant) {
        this.energy += plant.getEnergy();
        this.numberOfEatenPlants += 1;
        if (this.map.getTileAtPosition(plant.getPosition()) instanceof JungleTile)
            this.map.jungleTiles.put(plant.getPosition(), new JungleTile());
        else
            this.map.plainTiles.put(plant.getPosition(), new PlainsTile());

        map.plantGotEaten(plant.getPosition());
    }

    private boolean isFedUp() {
        return this.energy >= this.parametrs.getMinEnergyToBreed();
    }

    public boolean canBreed() {
        return this.isFedUp() && !this.breeded;
    }


    public void loseEnergy(int lostEnergy) {
        this.energy -= lostEnergy;
    }

    public void move() {
        this.breeded = false;
        this.age += 1;
        this.energy -= 1;
        this.direction.add(this.genotype.useGen());
        Vector2d oldPosition = this.position;
        this.position = this.position.add(this.direction.parseToVector2d());
        if (!this.map.canMoveTo(this.position)) {
            this.map.reactToGoingOut(this);
            positionChanged(oldPosition, this.position);
        }
        else {
            positionChanged(oldPosition, this.position);
        }
    }

    public MoveDirection getDirection() {
        return this.direction;
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
        return "src/main/resources/animal.png";
    }

    @Override
    public  String toString() {
        return "A";
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public int getDayOfDeath() {
        return this.dayOfDeath;
    }

    public int getAmountOfChildren() {
        return this.amountOfChildren;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getAge() {
        return this.age;
    }

    public int getId() {
        return this.id;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof final Animal that))
            return false;

        return this.id == that.id && this.position.equals(that.position) && this.energy == that.energy && this.age == that.age
                && this.amountOfChildren == that.amountOfChildren && this.breeded == that.breeded;
    }

    public int hashCode() {
        return this.energy + this.age*10 + this.amountOfChildren*10 + this.position.getY()*100 + this.position.getX()*10;
    }


    static class myAnimalComparator implements Comparator<Animal> {

        @Override
        public int compare(Animal a1, Animal a2) {
            if (a1.getPosition().equals(a2.getPosition())) {
                if (a2.getEnergy() == a1.getEnergy()) {
                    if (a2.getAge() == a1.getAge())
                        if (a2.getAmountOfChildren() == a1.getAmountOfChildren())
                            return a2.getId() - a1.getId();
                        else
                            return a2.getAmountOfChildren() - a1.getAmountOfChildren();
                    return a2.getAge() - a1.getAge();
                }
                return a2.getEnergy() - a1.getEnergy();
            }
            if (a1.getPosition().getX() == a2.getPosition().getX())
                return a1.getPosition().getY() - a2.getPosition().getY();
            return a1.getPosition().getX() - a2.getPosition().getX();
        }


    }
}
