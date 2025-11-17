package ca.utoronto.utm.paint.controller;

import ca.utoronto.utm.paint.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ShapeChooserPanelController implements EventHandler<ActionEvent> {
    private final View view;
    public ShapeChooserPanelController(View view){
        this.view=view;
    }
    @Override
    public void handle(ActionEvent event) {
        String command = ((Button) event.getSource()).getText();
        this.view.getPaintPanel().getController().setShapeManipulatorStrategy(ShapeManipulatorFactory.create(command, view.getPaintModel()));
        System.out.println(command);
    }
}
