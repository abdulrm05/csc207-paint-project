package ca.utoronto.utm.paint.view;

import java.util.Observable;
import java.util.Observer;

import ca.utoronto.utm.paint.controller.PaintPanelController;
import ca.utoronto.utm.paint.model.PaintModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class PaintPanel extends StackPane implements Observer {
    private final PaintModel model;
    private final PaintPanelController controller;
    private final Canvas canvas;
	
	public PaintPanel(PaintModel model) {
		this.canvas = new Canvas(500, 500);
		this.getChildren().add(this.canvas);
		// The canvas is transparent, so the background color of the
		// containing pane serves as the background color of the canvas.
		this.setStyle("-fx-background-color: white");
        this.controller = new PaintPanelController(model);
		this.canvas.addEventHandler(MouseEvent.ANY, controller);
        this.model = model;
        this.model.addObserver(this);
        this.repaint();
	}
    public PaintPanelController getController(){ return this.controller; }
	public void repaint() {
		GraphicsContext g = this.canvas.getGraphicsContext2D();
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		this.model.executeAll(g);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}
}

