package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HellMap extends AbstractWorldMap{


    public HellMap(Parametrs parametrs) {
        this.width = parametrs.getMapWidth();
        this.height = parametrs.getMapHeight();
        this.parametrs = parametrs;
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width,height);
        ArrayList<HashMap<Vector2d, IMapTile>> temp = generateTiles();
        this.tiles = temp.get(0);
        this.jungleTiles = temp.get(1);
        this.plainTiles = temp.get(2);
    }

    public void reactToGoingOut(Animal a){
        Vector2d pos = new Vector2d((int) (Math.random()*parametrs.getMapWidth()), (int) (Math.random()*parametrs.getMapHeight()));
        while(isOccupied(pos)){
            pos = new Vector2d((int) (Math.random()*parametrs.getMapWidth()), (int) (Math.random()*parametrs.getMapHeight()));
        }
        a.position = pos;
//        this.positionChanged(a.getPosition(), pos);

    }

    public ArrayList<HashMap<Vector2d, IMapTile>> generateTiles(){
        int jungleRows = (int) Math.ceil(0.2 * height);
        int midJungleRows = (int) Math.ceil(0.5 * height);
//        System.out.println(Integer.toString(jungleRows) + ", " + Integer.toString(midJungleRows));
        HashMap<Vector2d, IMapTile> newTiles = new HashMap<>();
        HashMap<Vector2d, IMapTile> jungleTiles = new HashMap<>();
        HashMap<Vector2d, IMapTile> plainsTiles = new HashMap<>();
        for (int i = midJungleRows - (int) Math.ceil(jungleRows/2); i <= midJungleRows - (int) Math.ceil(jungleRows/2) + jungleRows; i++) {
            for (int j = 0; j <= width; j++) {
                newTiles.put(new Vector2d(j, i), new JungleTile());
                jungleTiles.put(new Vector2d(j, i), new JungleTile());

            }
        }
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                if(newTiles.containsKey(new Vector2d(i,j))){
                    continue;
                }
                else{
                    newTiles.put(new Vector2d(i,j), new PlainsTile());
                    plainsTiles.put(new Vector2d(i,j), new PlainsTile());
                }
            }
        }
        ArrayList<HashMap<Vector2d, IMapTile>> result = new ArrayList<>();
        result.add(newTiles);
        result.add(jungleTiles);
        result.add(plainsTiles);

        return result;
    }
}
