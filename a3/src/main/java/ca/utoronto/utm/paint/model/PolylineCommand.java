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

    public ArrayList<Point> getPoints() {return this.points;}

    @Override
    public void execute(GraphicsContext g) {
        ArrayList<Point> point = this.getPoints();
        g.setStroke(this.getColor());

        for (int i = 0; i < point.size() - 1; i++) {
            Point p1 = point.get(i);
            Point p2 = point.get(i + 1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }

        if (previewPoint != null && !points.isEmpty()) {
            Point lastPoint = points.get(points.size() - 1);
            g.strokeLine(lastPoint.x, lastPoint.y, previewPoint.x, previewPoint.y);
        }

    }
}
