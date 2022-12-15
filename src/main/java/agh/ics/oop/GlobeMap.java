package agh.ics.oop;

import java.util.Map;

import static java.lang.Math.abs;

public class GlobeMap extends AbstractWorldMap{

    Parametrs parametrs;

    public GlobeMap(Parametrs parametrs) {
        this.parametrs = parametrs;
        this.width = parametrs.getMapWidth();
        this.height = parametrs.getMapWidth();
    }


    @Override
    public void growPlants() {}


    @Override
    public void reactToGoingOut(Animal a) {
        Vector2d pos = a.getPosition();
        a.loseEnergy(parametrs.getUsedEnergyToBreed());
        a.position = new Vector2d(abs(pos.getX()-width), pos.getY());
    }
    public Map<Vector2d, IMapTile> generateTiles(){
        return null;
    }
}
