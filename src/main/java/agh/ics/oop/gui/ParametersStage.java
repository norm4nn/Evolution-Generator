package agh.ics.oop.gui;

import agh.ics.oop.Parametrs;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ParametersStage extends Application {
    private VBox contentBox = new VBox();

    private TextField mapHeight = new TextField();
    private TextField mapWidth = new TextField();
    private TextField startingNumberOfPlants = new TextField();
    private TextField energyGivenByPlants = new TextField();
    private TextField numberOfGrowingPlants = new TextField();
    private TextField startingNumberOfAnimals = new TextField();
    private TextField startingEnergy = new TextField();
    private TextField energyRequierdToBeFedUp= new TextField();
    private TextField energyUsedToBreed = new TextField();
    private TextField minNumberOfMutations = new TextField();
    private TextField maxNumberOfMutations = new TextField();
    private TextField genotypeLength = new TextField();
    private CheckBox saveToFile = new CheckBox("Zapisuj statystyki co ture do pliku .csv");
    private final Button submitButton = new Button("Submit");
    public ParametersStage() {
        this.contentBox.setAlignment(Pos.CENTER);
        HBox parameterBox;

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Wysokość mapy: "), this.mapHeight);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Szerokość mapy: "), this.mapWidth);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Początkowa ilość roślin: "), this.startingNumberOfPlants);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Energia otrzymywana ze zjedzonych roślin: "), this.energyGivenByPlants);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Ilość roślin wyrastających co turę: "), this.numberOfGrowingPlants);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Początkowa ilość zwierząt: "), this.startingNumberOfAnimals);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Początkowa ilość energii: "), this.startingEnergy);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Ilość energii potrzebna do bycia najedzonym: "), this.energyRequierdToBeFedUp);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Energia zużywana przez rodzica by stworzyć potomka: "), this.energyUsedToBreed);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Minimalna liczba mutacji: "), this.minNumberOfMutations);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 0, 0));
        parameterBox.getChildren().addAll(new Label("Maksymalna liczba mutacji: "), this.maxNumberOfMutations);
        this.contentBox.getChildren().add(parameterBox);

        parameterBox = new HBox();
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.setPadding(new Insets(10, 0, 10, 0));
        parameterBox.getChildren().addAll(new Label("Długość genotypu: "), this.genotypeLength);
        this.contentBox.getChildren().add(parameterBox);

        this.contentBox.getChildren().add(this.saveToFile);
        this.contentBox.getChildren().add(this.submitButton);
        this.submitButton.setOnAction((e) -> {
            App newApp = new App();
            newApp.setParameters(this.getParametrs());
            Platform.runLater(new Runnable() {
                public void run() {
                    newApp.start(new Stage());
                }
            });
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(this.contentBox));
        primaryStage.show();

    }

    public Parametrs getParametrs() {
        return new Parametrs(Integer.parseInt(this.mapWidth.getText()),
                            Integer.parseInt(this.mapHeight.getText()),
                            Integer.parseInt(this.startingNumberOfPlants.getText()),
                            Integer.parseInt(this.energyGivenByPlants.getText()),
                            Integer.parseInt(this.numberOfGrowingPlants.getText()),
                            Integer.parseInt(this.startingNumberOfAnimals.getText()),
                            Integer.parseInt(this.startingEnergy.getText()),
                            Integer.parseInt(this.energyRequierdToBeFedUp.getText()),
                            Integer.parseInt(this.energyUsedToBreed.getText()),
                            Integer.parseInt(this.minNumberOfMutations.getText()),
                            Integer.parseInt(this.maxNumberOfMutations.getText()),
                            Integer.parseInt(this.genotypeLength.getText()),
                            this.saveToFile.isSelected()
                );
    }
}
