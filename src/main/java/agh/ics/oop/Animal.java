package agh.ics.oop;


import java.util.ArrayList;

public class Animal extends AbstractWorldMapElement {

    final private ArrayList<IPositionChangeObserver> positionChangeObservers;

    final private Parametrs parametrs;
    private int age = 0;
    private int energy;
    private int amountOfChildren;
    private int dayOfDeath;
    final private Genotype genotype;
    private MoveDirection direction;
    private boolean breeded;

    public Animal(AbstractWorldMap map, Parametrs parametrs) {
        this.map = map;
        this.direction = new MoveDirection();
        this.dayOfDeath = -1;
        this.parametrs = parametrs;
        this.amountOfChildren = 0;
        this.age = 0;
        this.position = new Vector2d((int) (Math.random()*parametrs.getMapWidth()), (int) (Math.random()*parametrs.getMapHeight()));
        this.genotype = new Genotype(parametrs);
        this.energy = parametrs.getStartingAmountOfEnergy();
        this.positionChangeObservers = new ArrayList<>();
        this.addObserver(this.map);
        addObserver((IPositionChangeObserver) map);
        this.breeded = false;
    }

    public Animal(AbstractWorldMap map, Parametrs parametrs, Animal parent1, Animal parent2) {
        this.direction = new MoveDirection();
        this.map = map;
        this.dayOfDeath = -1;
        this.parametrs = parametrs;
        this.position = parent1.getPosition();
        this.amountOfChildren = 0;
        this.age = 0;
        this.energy = this.parametrs.getUsedEnergyToBreed() * 2;
        this.positionChangeObservers = new ArrayList<>();
        addObserver((IPositionChangeObserver) map);
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

        if (this.map.getTileAtPosition(plant.getPosition()) instanceof JungleTile tile)
            this.map.jungleTiles.put(plant.getPosition(), tile);
        else
            this.map.plainTiles.put(plant.getPosition(), new PlainsTile());

    }

    public boolean isFedUp() {
        return this.energy >= this.parametrs.getMinEnergyToBreed();
    }


    public void loseEnergy(int lostEnergy) {
        this.energy -= lostEnergy;
    }

    public void move() {
        this.age += 1;
        this.energy -= 1;
        this.direction.add(this.genotype.useGen());
        Vector2d oldPosition = this.getPosition();
        this.position.add(this.direction.parseToVector2d());
        positionChanged(oldPosition, this.getPosition());
    }

    public MoveDirection getDirection() {
        this.breeded = false;
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
        return "A";
    }

    @Override
    public  String toString() {
        return "A";
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

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof final Animal that))
            return false;

        return this.position.equals(that.position) && this.energy == that.energy && this.age == that.age
                && this.amountOfChildren == that.amountOfChildren && this.breeded == that.breeded;
    }

    public int hashCode() {
        return this.energy + this.age*10 + this.amountOfChildren*10 + this.position.getY()*100 + this.position.getX()*10;
    }


}
