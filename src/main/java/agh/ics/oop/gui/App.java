package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class App extends Application {
    private Thread engineThread;
    private SimulationEngine engine;
    private AbstractWorldMap map;
    private Parametrs parametrs;
    private final GridPane gridPane = new GridPane();
    private final VBox stats = new VBox();
    private final HBox animalStats = new HBox();
    private final GridPane layout = new GridPane();

    private int mapWidth;
    private int mapHeight;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        this.mapWidth = this.map.getWidth();
        this.mapHeight = this.map.getHeight();
        this.gridPane.prefWidthProperty().bind(this.layout.widthProperty());
        this.layout.add(this.gridPane, 1, 0, 2, 2);
        this.layout.add(this.stats, 0, 0, 1, 2);
        this.layout.add(this.animalStats, 0, 3);

        ColumnConstraints cc = new ColumnConstraints();
        RowConstraints rc = new RowConstraints();
        cc.setPercentWidth(33.3f);
        rc.setPercentHeight(33.3f);
        for(int i=0; i<3;++i) {
            this.layout.getColumnConstraints().add(cc);
            this.layout.getRowConstraints().add(rc);
        }
        this.drawMap();
        this.updateStats();
        this.layout.setGridLinesVisible(false);

        Scene scene = new Scene(this.layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void drawMap()  {
        this.gridPane.getChildren().clear();
        this.gridPane.setGridLinesVisible(false);
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.getRowConstraints().clear();

        int width = this.mapWidth;
        int height = this.mapHeight;


        this.gridPane.setGridLinesVisible(true);
//        System.out.println(height);


        double menuSize = 6d;
        ColumnConstraints menuC = new ColumnConstraints();
        RowConstraints menuR = new RowConstraints();
        menuC.setPercentWidth(menuSize);
        menuR.setPercentHeight(menuSize);
        this.gridPane.getColumnConstraints().add(menuC);
        this.gridPane.getRowConstraints().add(menuR);

        ColumnConstraints cc = new ColumnConstraints();
        RowConstraints rc = new RowConstraints();
        cc.setPercentWidth((100d - menuSize) /(width));
        rc.setPercentHeight((100d - menuSize) /(height));

        for (int x = 0; x < this.mapWidth; x++) {
            Label label2Add = new Label("" + x);
            this.gridPane.add(label2Add, x + 1, 0, 1, 1);
            GridPane.setHalignment(label2Add, HPos.CENTER);
            this.gridPane.getColumnConstraints().add(cc);
        }

        for (int y = 0; y < this.mapHeight; y++) {
            Label label2Add = new Label("" + y);
            this.gridPane.add(label2Add, 0, this.mapHeight - y , 1, 1);
            GridPane.setHalignment(label2Add, HPos.CENTER);
            this.gridPane.getRowConstraints().add(rc);
        }
//        this.gridPane.getRowConstraints().add(rc);
//
//        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++)
//            for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++)
//                if (this.map.isOccupied(new Vector2d(x, y))) {
//                    VBox box2Add = null;
//                    try {
//                        box2Add = new GuiElementBox((IMapElement) this.map.objectAt(new Vector2d(x, y))).getvBox();
//                    } catch (FileNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                    this.gridPane.add(box2Add,
//                            x - lowerLeft.getX() + 1,
//                            upperRight.getY() - y + 1, 1, 1);
//                    GridPane.setHalignment(box2Add, HPos.CENTER);
//                }

        Label startLabel = new Label("y/x");
        this.gridPane.add(startLabel, 0, 0, 1, 1);
        GridPane.setHalignment(startLabel, HPos.CENTER);

    }

    private void updateStats() {
        Label numberOfAnimalsL = new Label("Liczba zwierząt: 4");
        Label numberOfPlantsL = new Label("Liczba roślin: 1");
        numberOfAnimalsL.setFont(Font.font(20));
        numberOfPlantsL.setFont(Font.font(20));
        this.stats.getChildren().addAll(numberOfAnimalsL, numberOfPlantsL);
    }
    @Override
    public void init() throws Exception {
        super.init();
        this.parametrs = new Parametrs(14, 4, 1,
                1, 5, 4, 30,
                15, 1, 8, 8 );
        this.engine = new SimulationEngine(this.parametrs);
        this.map = this.engine.getMap();
    }




}
