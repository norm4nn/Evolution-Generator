package agh.ics.oop;

public class Parametrs {
    final private int mapWidth;
    final private int mapHeight;
//    final private int mapVariant;
    final private int energyFromPlant;
    final private int numberOfGrowingPlants;
//    final private int growingPlantsVariant;
    final private int startingAmountOfAnimals;
    final private int startingAmountOfEnergy;
    final private int minEnergyToBreed;
    final private int usedEnergyToBreed;
    final private int minNumberOfMutations;
    final private int maxNumberOfMutations;
//    final private int mutationVariant;
    final private int genotypeLength;
//    final private int behaviourVarinat;

    public Parametrs(int mapWidth, int mapHeight, int energyFromPlant, int numberOfGrowingPlants, int startingAmountOfAnimals,
              int startingAmountOfEnergy, int minEnergyToBreed, int usedEnergyToBreed, int minNumberOfMutations,
              int maxNumberOfMutations, int genotypeLength) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.energyFromPlant = energyFromPlant;
        this.numberOfGrowingPlants = numberOfGrowingPlants;
        this.startingAmountOfAnimals = startingAmountOfAnimals;
        this.startingAmountOfEnergy = startingAmountOfEnergy;
        this.minEnergyToBreed = minEnergyToBreed;
        this.usedEnergyToBreed = usedEnergyToBreed;
        this.minNumberOfMutations = minNumberOfMutations;
        this.maxNumberOfMutations = maxNumberOfMutations;
        this.genotypeLength = genotypeLength;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getEnergyFromPlant() {
        return energyFromPlant;
    }

    public int getNumberOfGrowingPlants() {
        return numberOfGrowingPlants;
    }

    public int getStartingAmountOfAnimals() {
        return startingAmountOfAnimals;
    }

    public int getStartingAmountOfEnergy() {
        return startingAmountOfEnergy;
    }

    public int getMinEnergyToBreed() {
        return minEnergyToBreed;
    }

    public int getUsedEnergyToBreed() {
        return usedEnergyToBreed;
    }

    public int getMinNumberOfMutations() {
        return minNumberOfMutations;
    }

    public int getMaxNumberOfMutations() {
        return maxNumberOfMutations;
    }

    public int getGenotypeLength() {
        return genotypeLength;
    }
}
