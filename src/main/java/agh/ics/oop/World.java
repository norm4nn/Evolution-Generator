package agh.ics.oop;

import agh.ics.oop.gui.App;
import agh.ics.oop.gui.ParametersStage;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        try {
            Application.launch(ParametersStage.class, args);
//            SimulationEngine engine = new SimulationEngine(new Parametrs(4, 4, 1,
//                    1, 5, 4, 30,
//                    15, 1, 8, 8 ));
//            engine.run();
        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }
}