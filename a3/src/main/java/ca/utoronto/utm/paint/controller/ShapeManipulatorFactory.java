package ca.utoronto.utm.paint.controller;
import ca.utoronto.utm.paint.model.PaintModel;

public class ShapeManipulatorFactory {
	public static ShapeManipulatorStrategy create(String strategyName, PaintModel paintModel){
		ShapeManipulatorStrategy strategy = switch (strategyName) {
            case "Circle" -> new CircleManipulatorStrategy(paintModel);
            case "Squiggle" -> new SquiggleManipulatorStrategy(paintModel);
            case "Rectangle" -> new RectangleManipulatorStrategy(paintModel);
            case "Polyline" -> new PolylineManipulatorStrategy(paintModel);
            default -> null;
        };
        return strategy;
	}
}
