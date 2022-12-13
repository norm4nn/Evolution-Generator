package agh.ics.oop;

public interface IAnimalChangeObserver {
    /**
     *
     * @param oldAnimal - old animal of the map element which should be removed from hashmap
     * @param newAnimal - new animal of the map element which will be add to hashmap as new key
     */
    void animalChange(Animal oldAnimal, Animal newAnimal);
}
