package ca.utoronto.utm.paint.controller;

import ca.utoronto.utm.paint.model.PaintModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class PaintPanelController implements EventHandler<MouseEvent> {
    private ShapeManipulatorStrategy strategy;

    public PaintPanelController(PaintModel model) {
        this.setShapeManipulatorStrategy( new ShapeManipulatorStrategy(model));
    }


    public void handle(MouseEvent event) {
        this.strategy.handle(event);
    }
    public void setShapeManipulatorStrategy(ShapeManipulatorStrategy strategy) {
        this.strategy = strategy;
    }
}
