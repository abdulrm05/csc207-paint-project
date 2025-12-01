package ca.utoronto.utm.paint.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class PolylineCommand extends PaintCommand {
    private final ArrayList<Point> points = new ArrayList<Point>();
    private Point previewPoint;

    public void add(Point p) {
        this.points.add(p);
        this.setChanged();
        this.notifyObservers();
    }

    public void setPreviewPoint(Point p) {
        this.previewPoint = p;
        this.setChanged();
        this.notifyObservers();
    }

    public Point getPreviewPoint() {return this.previewPoint;}

    public ArrayList<Point> getPoints() {return this.points;}

    @Override
    public void accept(PaintCommandVisitor visitor) {
        visitor.visit(this);
    }
}
