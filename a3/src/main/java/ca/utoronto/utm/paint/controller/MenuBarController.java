package ca.utoronto.utm.paint.controller;

import ca.utoronto.utm.paint.model.PaintModel;
import ca.utoronto.utm.paint.persistence.PaintFileParser;
import ca.utoronto.utm.paint.persistence.PaintFileSaver;
import ca.utoronto.utm.paint.view.View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.PrintWriter;

public class MenuBarController implements EventHandler<ActionEvent> {
    private final View view;
    public MenuBarController(View view){
        this.view=view;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println(((MenuItem) event.getSource()).getText());
        String command = ((MenuItem) event.getSource()).getText();
        switch (command) {
            case "Open" -> {
                FileChooser fc = new FileChooser();
                File file = fc.showOpenDialog(this.view.getStage());

                if (file != null) {
                    System.out.println("Opening: " + file.getName() + "." + "\n");
                    PaintFileParser parser = new PaintFileParser();
                    boolean isParse = parser.parse(file.getAbsolutePath());
                    if(isParse)new View(parser.getPaintModel(), this.view.getStage());
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("File Parse Error");
                        alert.setHeaderText("Error Parsing File");
                        alert.setContentText(file.getName()+" "+parser.getErrorMessage());
                        alert.showAndWait();
                    }
                } else {
                    System.out.println("Open command cancelled by user." + "\n");
                }
            }
            case "Save" -> {
                FileChooser fc = new FileChooser();
                File file = fc.showSaveDialog(this.view.getStage());

                if (file != null) {
                    System.out.println("Saving: " + file.getAbsolutePath() + "." + "\n");
                    PaintFileSaver.save(this.view.getPaintModel(), file);
                } else {
                    System.out.println("Save command cancelled by user." + "\n");
                }
            }
            case "New" -> new View(new PaintModel(), this.view.getStage());
            case "Exit" -> Platform.exit();
        }
    }
}
