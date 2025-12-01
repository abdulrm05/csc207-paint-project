package ca.utoronto.utm.paint.model;
import javafx.scene.canvas.GraphicsContext;

public class CircleCommand extends PaintCommand {
	private Point centre;
	private int radius;
	
	public CircleCommand(Point centre, int radius){
		this.centre = centre;
		this.radius = radius;
	}
	public Point getCentre() { return centre; }
	public void setCentre(Point centre) { 
		this.centre = centre; 
		this.setChanged();
		this.notifyObservers();
	}
	public int getRadius() { return radius; }
	public void setRadius(int radius) { 
		this.radius = radius; 
		this.setChanged();
		this.notifyObservers();
	}

    @Override
    public void accept(PaintCommandVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String s = "Circle\n";
        s += super.toString();
        s += "\tcenter:(" + this.getCentre().x + "," + this.getCentre().y + ")\n";
        s += "\tradius:" + this.radius + "\n";
        s += "End Circle";
        return s;

    }
}
