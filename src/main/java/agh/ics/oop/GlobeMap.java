package agh.ics.oop;

import java.util.Map;

import static java.lang.Math.abs;

public class GlobeMap extends AbstractWorldMap{
    int width;
    int height;

<<<<<<< HEAD
    public GlobeMap(Parametrs parametrs) {
        this.parametrs = parametrs;

    }


    @Override
    public void growPlants() {
=======
    public GlobeMap(int width, int height) {
        this.width = width;
        this.height = height;
    }
>>>>>>> f32ec631965c367e26b071a985a55dda77ceda94

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
