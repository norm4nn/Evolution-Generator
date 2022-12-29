package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class App extends Application {
    private SimulationEngine engine;
    private Thread engineThread;
    private AbstractWorldMap map;
    private Parametrs parametrs;
    private final GridPane gridPane = new GridPane();
    private final VBox stats = new VBox();
    private final VBox animalStats = new VBox();
    private final GridPane layout = new GridPane();
    private int mapWidth;
    private int mapHeight;
    private final Button toggleButton = new Button("STOP");
    public boolean isRunning;
    private Animal selectedAnimal;
    private CheckBox showGenotypesCheckBox = new CheckBox("Zaznacz zwierzęta z najpopularniejszym genotypem");
    private VBox optionBox = new VBox();
    private Button submitButton;
//    private ParametersStage parametersStage = new ParametersStage();

    @Override
    public void start(Stage primaryStage)  {
        this.engineThread = new Thread(engine);

        this.isRunning = true;
        this.toggleButton.setOnAction((event) -> {
            if (this.isRunning) {
                try {engine.CreateCSVFile();}
                catch (IOException e){
                    System.out.println(e);
                }
                this.toggleButton.setText("START");
                this.optionBox.getChildren().add(this.showGenotypesCheckBox);
            } else {
                synchronized (engine) {
                    engine.notify();
                }
                this.toggleButton.setText("STOP");
                this.showGenotypesCheckBox.setSelected(false);
                this.optionBox.getChildren().remove(this.showGenotypesCheckBox);
            }
            this.isRunning = !this.isRunning;
        });
        this.showGenotypesCheckBox.setOnAction((e) -> {
            this.drawMap();
        });
        this.mapWidth = this.map.getWidth();
        this.mapHeight = this.map.getHeight();

        this.optionBox.getChildren().add(this.toggleButton);
        this.optionBox.setAlignment(Pos.CENTER);
        this.layout.add(this.gridPane, 1, 0, 2, 2);
        this.layout.add(this.stats, 0, 0, 1, 2);
        this.layout.add(this.animalStats, 0, 2);
        this.layout.add(this.optionBox, 1, 2, 2, 1);


        ColumnConstraints cc = new ColumnConstraints();
        RowConstraints rc = new RowConstraints();
        cc.setPercentWidth(33.3f);
        rc.setPercentHeight(33.3f);
        for (int i = 0; i < 3; ++i) {
            this.layout.getColumnConstraints().add(cc);
            this.layout.getRowConstraints().add(rc);
        }

        Scene scene = new Scene(this.layout, 800, 600);
//        primaryStage = new ParametersStage();
//        this.submitButton = ((ParametersStage) primaryStage).getSubmitButton();

        primaryStage.setScene(scene);
        primaryStage.show();

//        this.parametersStage.show();

        this.engineThread.start();
    }

    public void draw() {
        this.drawMap();
        this.updateStats();
        this.updateAnimalStats();
    }

    public void drawMap()  {
        this.gridPane.getChildren().clear();
        this.gridPane.setGridLinesVisible(false);
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.getRowConstraints().clear();

        int width = this.mapWidth;
        int height = this.mapHeight;

        this.gridPane.setGridLinesVisible(true);

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
        for (int x = 0; x < this.mapWidth; x++)
            for (int y = 0; y < this.mapHeight; y++)
                if (this.map.isOccupied(new Vector2d(x, y)) || this.map.plants.containsKey(new Vector2d(x, y))) {
                    VBox box2Add = null;
                    try {
                        if (this.map.objectAt(new Vector2d(x, y)) instanceof Animal animal) {
                            box2Add = new GuiElementBox((IMapElement) this.map.objectAt(new Vector2d(x, y)), this).getvBox();
                            if (!this.isRunning && this.showGenotypesCheckBox.isSelected() && animal.getGenotype().getGenotypeArray().equals(this.engine.getMostPopularGenotype()))
                                box2Add.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                        else if (this.map.plants.containsKey(new Vector2d(x, y)))
                            box2Add = new GuiElementBox(this.map.plants.get(new Vector2d(x, y))).getvBox();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    this.gridPane.add(box2Add,
                            x  + 1,
                            this.mapHeight - y);
                    GridPane.setHalignment(box2Add, HPos.CENTER);
                }

        Label startLabel = new Label("y/x");
        this.gridPane.add(startLabel, 0, 0);
        GridPane.setHalignment(startLabel, HPos.CENTER);

    }

    private void updateStats() {
        this.stats.getChildren().clear();
        int fontSize = 20;
        LinkedList<Label> statsLabels = new LinkedList<>();

        Label averageLifespanL = new Label("Średnia długość życia: " + this.engine.getAverageLifespan());
        statsLabels.push(averageLifespanL);
        Label averageEnergyL = new Label("Średni poziom energii: " + this.engine.getAverageEnergy());
        statsLabels.push(averageEnergyL);
        Label mostPopularGenotypeL = new Label("Najpopularniejszy genotyp:\n" + Arrays.toString(this.engine.getMostPopularGenotype()));
        statsLabels.push(mostPopularGenotypeL);
        Label numberOfFreeTilesL = new Label("Liczba wolnych pól: " + this.engine.getNumberOfFreeTiles());
        statsLabels.push(numberOfFreeTilesL);
        Label numberOfPlantsL = new Label("Liczba roślin: " + this.engine.getNumberOfPlants());
        statsLabels.push(numberOfPlantsL);
        Label numberOfAnimalsL = new Label("Liczba zwierząt: " + this.engine.getNumberOfAnimals());
        statsLabels.push(numberOfAnimalsL);

        for(Label statLabel : statsLabels)
            statLabel.setFont(Font.font(fontSize));

        this.stats.getChildren().addAll(statsLabels);
    }

    private void updateAnimalStats() {
        this.animalStats.getChildren().clear();
        if (this.selectedAnimal != null) {
            int fontSize = 18;
            LinkedList<Label> animalStatsLabels = new LinkedList<>();
            Label dayOfDeathL;
            if (this.selectedAnimal.getDayOfDeath() == -1)
                dayOfDeathL = new Label("Dzień śmierci: (wciąż żyje)");
            else
                dayOfDeathL = new Label("Dzień śmierci: " + this.selectedAnimal.getDayOfDeath());

            animalStatsLabels.push(dayOfDeathL);
            Label lifespanL = new Label("Wiek: " + this.selectedAnimal.getAge());
            animalStatsLabels.push(lifespanL);
            Label numberOfChildrenL = new Label("Dzieci: " + this.selectedAnimal.getAmountOfChildren());
            animalStatsLabels.push(numberOfChildrenL);
            Label numberOfEatenPlantsL = new Label("Zjedzonych roślin: " + this.selectedAnimal.getNumberOfEatenPlants());
            animalStatsLabels.push(numberOfEatenPlantsL);
            Label energyL = new Label("Energia: " + this.selectedAnimal.getEnergy());
            animalStatsLabels.push(energyL);
            Label currentGenL = new Label("Aktywny gen: " + this.selectedAnimal.getGenotype().getCurrentGen());
            animalStatsLabels.push(currentGenL);
            Label genotypeL = new Label("Genotyp: " + Arrays.toString(this.selectedAnimal.getGenotype().getGenotypeArray()));
            animalStatsLabels.push(genotypeL);
            Button stopFollowingButton = new Button("Przestań obserwować");
            stopFollowingButton.setOnAction((e) -> {
                this.selectedAnimal = null;
                this.animalStats.getChildren().clear();
            });
            for (Label statLabel : animalStatsLabels)
                statLabel.setFont(Font.font(fontSize));

            this.animalStats.getChildren().addAll(animalStatsLabels);
            this.animalStats.getChildren().add(stopFollowingButton);
        }
    }
    @Override
    public void init() throws Exception {
        super.init();
        this.selectedAnimal = null;
//        this.parametrs = new Parametrs(6, 6, 3, 5,
//                2, 1, 20, 30,
//                15, 1, 8, 8, false );
//        this.engine = new SimulationEngine(this.parametrs, this);
//        this.map = this.engine.getMap();
    }

    public void setSelectedAnimal(Animal selectedAnimal) {
        this.selectedAnimal = selectedAnimal;
        this.updateAnimalStats();
    }

    public void setParameters(Parametrs parametrs) {
        this.parametrs = parametrs;
        this.engine = new SimulationEngine(this.parametrs, this);
        this.map = this.engine.getMap();
    }
}
