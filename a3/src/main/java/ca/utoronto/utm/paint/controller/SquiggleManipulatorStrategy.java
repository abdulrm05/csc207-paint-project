package ca.utoronto.utm.paint.controller;
import ca.utoronto.utm.paint.model.PaintModel;
import ca.utoronto.utm.paint.model.Point;
import ca.utoronto.utm.paint.model.SquiggleCommand;
import javafx.scene.input.MouseEvent;

class SquiggleManipulatorStrategy extends ShapeManipulatorStrategy {
	SquiggleManipulatorStrategy(PaintModel paintModel) {
		super(paintModel);
	}

	private SquiggleCommand squiggleCommand;
	@Override
	public void mouseDragged(MouseEvent e) {
		this.squiggleCommand.add(new Point((int)e.getX(), (int)e.getY()));
	}

	@Override
	public void mousePressed(MouseEvent e) {
			this.squiggleCommand = new SquiggleCommand();
			this.addCommand(squiggleCommand);
	}
}
