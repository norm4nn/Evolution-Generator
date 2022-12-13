package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        try {
//            Application.launch(App.class, args);
            SimulationEngine engine = new SimulationEngine(new Parametrs(10, 10, 30,
                    3, 5, 100, 30,
                    15, 1, 8, 8 ));
            engine.run();
        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }
}