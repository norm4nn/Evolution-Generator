package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        try {
//            Application.launch(App.class, args);
            SimulationEngine engine = new SimulationEngine(new Parametrs(4, 4, 30,
                    5, 5, 100, 30,
                    15, 1, 8, 8 ));
            engine.run();
        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }
}