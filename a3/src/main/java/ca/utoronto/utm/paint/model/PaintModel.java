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

	public void executeAll(GraphicsContext g) {
		for(PaintCommand c: this.commands){
			c.execute(g);
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

    /**
     * @return the PaintFileFormat String for this model
     */
    public String getPaintFileString(){
        //TODO FIX THIS!!
        String s = "";
        s+="Paint Save File Version 1.0\n";
        s+="End Paint Save File";
        return s;
    }
}
