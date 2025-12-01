package ca.utoronto.utm.paint.model;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class SquiggleCommand extends PaintCommand {
	private final ArrayList<Point> points=new ArrayList<Point>();
	
	public void add(Point p){ 
		this.points.add(p); 
		this.setChanged();
		this.notifyObservers();
	}
	public ArrayList<Point> getPoints(){ return this.points; }
	
	
	@Override
	public void accept(PaintCommandVisitor visitor) {
		visitor.visit(this);
	}

    @Override
    public String toString() {
        String s = "Squiggle\n";
        s += super.toString();
        s += "\tpoints\n";

        for (Point p : this.getPoints()){
            s += "\t\tpoint:(" + p.x + "," + p.y + ")\n";
        }
        s += "\tend points\n";
        return s + "End Squiggle";
    }
}
