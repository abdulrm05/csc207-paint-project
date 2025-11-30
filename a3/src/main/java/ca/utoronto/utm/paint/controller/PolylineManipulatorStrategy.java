package ca.utoronto.utm.paint.controller;

import ca.utoronto.utm.paint.model.PaintModel;
import ca.utoronto.utm.paint.model.Point;
import ca.utoronto.utm.paint.model.PolylineCommand;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Observable;

public class PolylineManipulatorStrategy extends ShapeManipulatorStrategy{

    private PolylineCommand polylineCommand;
    private Point lastPoint;

    PolylineManipulatorStrategy(PaintModel model) {super(model);}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            Point clickedPoint = new Point((int) e.getX(), (int) e.getY());

            if (polylineCommand == null) {
                polylineCommand = new PolylineCommand();
                this.addCommand(polylineCommand);
            }
            polylineCommand.add(clickedPoint);
            lastPoint = clickedPoint;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (polylineCommand != null && lastPoint != null) {
            polylineCommand.setPreviewPoint(new Point ((int) e.getX(), (int) e.getY()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            if (polylineCommand != null && lastPoint != null) {
                Point finalPoint = new Point((int) e.getX(), (int) e.getY());
                polylineCommand.add(finalPoint);
                polylineCommand.setPreviewPoint(null);
            }
            polylineCommand = null;
            lastPoint = null;
        }
    }

}
