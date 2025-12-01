package ca.utoronto.utm.paint.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;

public class PaintModel extends Observable implements Observer {

	public void addCommand(PaintCommand command){
		this.commands.add(command);
		command.addObserver(this);
		this.setChanged();
		this.notifyObservers();
	}
	
	private final ArrayList<PaintCommand> commands = new ArrayList<PaintCommand>();

	public void accept(PaintCommandVisitor visitor){
        for (PaintCommand command : commands){
            command.accept(visitor);
        }
    }
	
	/**
	 * We Observe our model components, the PaintCommands
	 */
	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers();
	}
}
