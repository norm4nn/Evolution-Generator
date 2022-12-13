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
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class App extends Application {
    private Thread engineThread;
    private SimulationEngine engine;
    private AbstractWorldMap map;

    private final GridPane gridPane = new GridPane();
    private Button startBtn;

    private TextField textField;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        draw();

        Scene scene = new Scene(this.gridPane, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();

        this.startBtn.setOnAction((event) -> {
            this.engineThread = new Thread(engine);
            this.engineThread.start();
        });


    }

    public void draw()  {
        this.gridPane.getChildren().clear();
        this.gridPane.setGridLinesVisible(false);
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.getRowConstraints().clear();

        Vector2d lowerLeft = this.map.getLowerLeft();
        Vector2d upperRight = this.map.getUpperRight();
        int width = upperRight.getX() - lowerLeft.getX() + 1;
        int height = upperRight.getY() - lowerLeft.getY() + 1;


        this.gridPane.setGridLinesVisible(true);

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100d / width);

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100d /(height + 2));


        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            Label label2Add = new Label("" + x);
            this.gridPane.add(label2Add, x - lowerLeft.getX() + 1, 0, 1, 1);
            GridPane.setHalignment(label2Add, HPos.CENTER);
            this.gridPane.getColumnConstraints().add(cc);
        }

        for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++) {
            Label label2Add = new Label("" + y);
            this.gridPane.add(label2Add, 0, upperRight.getY() - y + 1, 1, 1);
            GridPane.setHalignment(label2Add, HPos.CENTER);
            this.gridPane.getRowConstraints().add(rc);
        }
        this.gridPane.getRowConstraints().add(rc);

        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++)
            for (int y = lowerLeft.getY(); y <= upperRight.getY(); y++)
                if (this.map.isOccupied(new Vector2d(x, y))) {
                    VBox box2Add = null;
                    try {
                        box2Add = new GuiElementBox((IMapElement) this.map.objectAt(new Vector2d(x, y))).getvBox();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    this.gridPane.add(box2Add,
                            x - lowerLeft.getX() + 1,
                            upperRight.getY() - y + 1, 1, 1);
                    GridPane.setHalignment(box2Add, HPos.CENTER);
                }

        Label startLabel = new Label("y/x");
        this.gridPane.add(startLabel, 0, 0, 1, 1);
        GridPane.setHalignment(startLabel, HPos.CENTER);

        HBox hBox = new HBox(this.startBtn, this.textField);
        hBox.setAlignment(Pos.CENTER);
        this.gridPane.add(hBox, 0, height + 1, width + 1, 1);
        GridPane.setHalignment(hBox, HPos.CENTER);
        GridPane.setValignment(hBox, VPos.CENTER);

        this.gridPane.getColumnConstraints().add(cc);
        this.gridPane.getRowConstraints().add(rc);

        this.gridPane.setMinWidth(2000);

    }
    @Override
    public void init() throws Exception {
        super.init();
        getParameters().getRaw();

        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};




        this.startBtn = new Button("START");
        this.textField = new TextField();

        }




}
