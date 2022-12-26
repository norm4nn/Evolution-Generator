package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {

    final private VBox vBox;
    final private App app;

    public GuiElementBox(IMapElement element, App app) throws FileNotFoundException {
        this.app = app;
        Image image = null;
        try {
            image = new Image(new FileInputStream(element.imgAddress()));
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku");
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        this.vBox = new VBox(imageView);

        this.vBox.setAlignment(Pos.CENTER);

        this.vBox.setOnMouseClicked((e) -> {
            if (element instanceof Animal) {
                this.app.setSelectedAnimal((Animal)element);
            }
        });
    }
    public GuiElementBox(IMapElement element) throws FileNotFoundException {
        this.app = null;
        Image image = null;
        try {
            image = new Image(new FileInputStream(element.imgAddress()));
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku");
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        this.vBox = new VBox(imageView);

        this.vBox.setAlignment(Pos.CENTER);
    }
    public VBox getvBox() {
        return this.vBox;
    }
}
