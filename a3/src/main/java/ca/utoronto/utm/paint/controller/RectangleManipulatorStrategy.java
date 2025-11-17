package ca.utoronto.utm.paint.controller;
import ca.utoronto.utm.paint.model.PaintModel;
import ca.utoronto.utm.paint.model.Point;
import ca.utoronto.utm.paint.model.RectangleCommand;
import javafx.scene.input.MouseEvent;

class RectangleManipulatorStrategy extends ShapeManipulatorStrategy {
	RectangleManipulatorStrategy(PaintModel paintModel) {
		super(paintModel);
	}

	private RectangleCommand rectangleCommand;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Point p2=new Point((int)e.getX(), (int)e.getY());
		this.rectangleCommand.setP2(p2);
	}

	@Override
	public void mousePressed(MouseEvent e) {
			Point p1 = new Point((int)e.getX(), (int)e.getY());
			Point p2 = new Point((int)e.getX(), (int)e.getY());

			this.rectangleCommand = new RectangleCommand(p1,p2);
        this.addCommand(rectangleCommand);
	}
}
